package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {
	private Socket newSocketThread = null;
	private Server newServerThread = null;
	private int idThread = -1;
	private Scanner in = null;
	private PrintStream saida = null;
	private String user = null;

	public ServerThread(Server server, Socket newSocket) {
		newServerThread = server;
		newSocketThread = newSocket;
		idThread = newSocketThread.getPort();
	}
	public void run() {
		System.out.println("Server Thread " + idThread + " running");
		while (true) {
			while (in.hasNextLine()) {
				String receviedMsg = in.nextLine();
				newServerThread.handle(this.idThread, receviedMsg);
			}
		}
	}
	
	public int getIdThread(){
		return this.idThread;
	}
	
	public String getUserThread(){
		return this.user;
	}
	
	public void setUserThread(String newUser){
		System.out.println("Vou setar User " + newUser);
		this.user = newUser;
	}
	public void send(String newMsg) {
		System.out.println("Essa eh a mensagem enviada= " + newMsg);
		saida.println(newMsg);
	}

	public void openStream() throws IOException {
		in = new Scanner(newSocketThread.getInputStream());
		saida = new PrintStream(newSocketThread.getOutputStream());
	}

	public void closeConnection() throws IOException {
		if (in != null)
			in.close();;
		if (newSocketThread != null)
			newSocketThread.close();
		if(newServerThread != null)
			newServerThread.closeConnection();
	}

}