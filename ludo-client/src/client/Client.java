package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import client.Observer;
import controller.FacadeMovimento;
import view.Board;

public class Client implements Runnable, client.Subject {
	private Socket newClient = null;
	private ClientThread newClientThread = null;
	private Thread newThread = null;
	private Scanner keyboard = null;
	private PrintStream saida = null;
	private static String NovaJogada = null;
	
	//Creates an ArrayList to hold all observers
	private static ArrayList<client.Observer> observers = new ArrayList<client.Observer>();
	
	private static Client clientfirstInstance = null;

	//Singleton da Class -- Client
	public static Client getInstance(){
		if(clientfirstInstance == null){

			clientfirstInstance = new Client();
		}

		return clientfirstInstance;
	}

	private Client() {}
	
	private void setNovaJogada(String newString){
		
		NovaJogada = newString;
		notifyObserver();
	}
	
	public void initializeClient(String newServerName, int NewPort, String newNickname){
		try {
			newClient = new Socket(newServerName, NewPort);
			System.out.println("O cliente se conectou ao servidor!");
			startClient(newNickname);
		} catch (UnknownHostException e) {
			System.out.println("Host Unknown: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Unexpected exception: " + e.getMessage());
		}
	}

	private void startClient(String newNickname) throws IOException {
		keyboard = new Scanner(System.in);
		saida = new PrintStream(newClient.getOutputStream());
		if (newThread == null) {
			newClientThread = new ClientThread(this, newClient);
			newThread = new Thread(this);
			System.out.println(newNickname);
			sendMsg("Nickname: " + newNickname);
			newThread.start();
		}
	}

	public void closeConnection() throws IOException {
		if (newThread != null) {
			newThread.interrupt();
			newThread = null;
		}
		if (keyboard != null)
			keyboard.close();
		if (saida != null)
			saida.close();
		if (newClient != null)
			newClient.close();
		System.out.println("O cliente terminou de executar!");
	}

	public void handle(String newMsg) {
		
		if (newMsg.equals("###")) {
			System.out.println("O usuario saiu");
		} else {
			if(newMsg.equals("TIMED OUT")){
				System.out.println("Acaboouuu");
				FacadeMovimento.getInstance().popUpTimedOut();
			}
			else{
				String[] parts = newMsg.split(" ");
				if(parts[0].equals("BEGIN:")){
					FacadeMovimento.getInstance().gameStarted();
					System.out.println(parts[1]);
					FacadeMovimento.getInstance().setPlayerTurn(parts[1]);
				}
				else
					this.setNovaJogada(newMsg);
			}
		}
	}
	
	public void sendMsg(String PackPinosToClient){
		saida.println(PackPinosToClient);
	}
	
	@Override
	public void run() {
		while (newThread != null) {

			String msg = keyboard.nextLine();

			while (msg.compareTo("###") != 0) {
				saida.println(msg);
				msg = keyboard.nextLine();
			}
			try {
				this.closeConnection();
			} catch (IOException e) {
				System.out.println("Error closing connection" + e);
			}
		}

	}

	/****************** Start: client.Observer ******************/
	@Override
	public void register(client.Observer newObserver) {
		// Adiciona um novo Observer a lista de Observers
		System.out.println("Client: Observador Registrado!");

		observers.add(newObserver);
		
	}

	@Override
	public void unregister(client.Observer deletedObserver) {
		//Get the index of the observer that is going to be deleted.
		int ObserverIndex = observers.indexOf(deletedObserver);

		//Print out message
		System.out.println("Observer " + (ObserverIndex+1) + "deleted");

		//Removes observer from the array list
		observers.remove(ObserverIndex);
		
	}

	@Override
	public void notifyObserver() {
		//Cycle through all observers and notifies them of changes
		for(int i = 0; i < observers.size(); i++){
			Observer observer = (Observer) observers.get(i);
			observer.updateClient(NovaJogada);
		}
		
	}
	/****************** END: client.Observer ******************/


}