package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Timer;

public class Server implements Runnable, ActionListener {
	private ArrayList<ServerThread> newClients = new ArrayList<ServerThread>();
	private ServerSocket newServer = null;
	private Thread newThread = null;
	private int nConnections;
	private int MAXConnections = 4;
	private Timer timer;
	private final int DELAY = 180000;
	private int entryOrder = 0;

	public Server(int newPort) {
		try {
			newServer = new ServerSocket(newPort);
			System.out.println("Porta " + newPort + " aberta!");
			start();
		} catch (IOException e) {
			System.out.println("Erro Port " + newPort + " : " + e.getMessage());
		}
		nConnections = 0;
		timer = new Timer(DELAY, this);
	}

	public void start() {
		if (newThread == null) {
			newThread = new Thread(this);
			newThread.start();
		}
	}

	public void stop() {
		if (newThread != null) {
			newThread.interrupt();
			newThread = null;
		}
	}

	@Override
	public void run() {
		if (nConnections < MAXConnections) {
			while (newThread != null) {
				try {
					System.out.println("Waiting for a client..");
					addNewThread(newServer.accept());
				} catch (IOException e) {
					System.out.println("Acceptance Error: " + e.getMessage());
					stop();
				}
			}
		}
	}

	private void addNewThread(Socket newSocket) {
		System.out.println("Nova conex�o com o cliente " + newSocket.getInetAddress().getHostAddress());
		newClients.add(new ServerThread(this, newSocket));
		nConnections++;
		if (nConnections == 1) {
			timer.start();
			System.out.println("Timer Started");
		}

		try {
			newClients.get(newClients.size() - 1).openStream();
			newClients.get(newClients.size() - 1).start();

		} catch (IOException e) {
			System.out.println("Error opening Thread: " + e);
		}
		
		if (nConnections == MAXConnections) {
			timer.stop();
			//handleBegin(); // Estava dando HandleBegin antes de Receber o Nick
		}
	}

	public synchronized void handle(int idClient, String input) {
		if (input.equals("###")) {
			newClients.get(idClient).send("###");
			remove(idClient);
		} else {
			String[] parts = input.split(" ");
			if(parts[0].equals("Nickname:")){ /* Se eu estou recebendo um nickname */
				System.out.println("Meu nick eh "+ parts[1]);
				newClients.get(entryOrder).setUserThread(parts[1]);
				entryOrder++;
				if(nConnections == MAXConnections)
					handleBegin(); //Essa Seria a Posicao Ideal para o HandleBegin, mas eu nao sei se afeta o MAXCONNECTIONS
			}
			else{				
				for(ServerThread st:newClients) {
						System.out.println("idClient= "+ idClient + " " + "stClient= " + st.getIdThread());
						st.send(nextPlayerServer(input));
				}
			}
		}
	}

	public synchronized void remove(int idClient) {
		System.out.println("Removing Client Thread " + idClient);
		try {
			newClients.get(idClient).closeConnection();
			newClients.remove(idClient);
		} catch (IOException e) {
			System.out.println("Error closing Client Connection" + e);
			newClients.get(idClient).interrupt();
		}

	}

	public void closeConnection() throws IOException {
		newServer.close();
		System.out.println("O servidor terminou de executar!");
	}

	private void handleTimeOut() {

		if (nConnections < MAXConnections) {
			for (ServerThread s : newClients) {
				timer.stop();
				s.send("TIMED OUT");
				// this.newThread = null;
			}
			System.exit(0);
		}
	}

	private void handleBegin() {
	//	System.out.println("Tamanho da lista de clientes : " + newClients.size());
		
		for(ServerThread st:newClients){
			System.out.println(st.getUserThread());
			st.send("BEGIN: " + newClients.get(0).getUserThread());
		}
	}

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.println("Error: Insert only the PORT!");
		} else {
			new Server(Integer.parseInt(args[0]));
		}
	}

	private String nextPlayerServer(String Msg) {
		// Exemplo da msg recebida.
		// QUEM ENVIOU#RED x x x x x x x x #BLUE x x x x x x x x #YELLOW x x x x
		// x x x x#GREEN x x x x x x x x
		String[] quebraMsg = Msg.split("%");
		String[] quebraquebra = quebraMsg[0].split("@");
		String jogadorAtual = quebraquebra[0];
		String estadoAtual = quebraMsg[1];
		String jogadorSeguinte = null;
		String round_recebido = quebraquebra[1];
		Integer temp = getPlayerRound(jogadorAtual);
		if(round_recebido.equals(temp.toString()))
			return jogadorAtual+"@"+round_recebido+"%"+estadoAtual;
		for(int i = 0; i < newClients.size(); i++){
			if(newClients.get(i).getUserThread().equals(jogadorAtual)){
				if(i == newClients.size()-1)
					jogadorSeguinte = newClients.get(0).getUserThread();
				else{
					jogadorSeguinte = newClients.get(++i).getUserThread();
				}
				break;
			}
			System.out.println("Nao achei o nick do cara ");
		}
		int next_round = getPlayerRound(jogadorSeguinte);
		if(next_round == -1)
			System.out.println("Deu ruim ao passar o round");
		//System.out.println("Essa eh a mesnagem q o nextPlayer esta retornando : "+jogadorSeguinte+next_round+"%"+estadoAtual);
		return jogadorSeguinte+"@"+next_round+"%"+estadoAtual;
	}
	private int getPlayerRound(String nick){
		for(int i = 0; i < newClients.size(); i++){
			if(nick.equals(newClients.get(i).getUserThread()))
				return i;
		}
		return -1;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {

		handleTimeOut();
	}
}