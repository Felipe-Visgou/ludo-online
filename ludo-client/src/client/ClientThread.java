package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {
	private Socket newSocketThread = null;
	private Client newClientThread = null;
	private Scanner in = null;

	public ClientThread(Client client, Socket socket) {
		newClientThread = client;
		newSocketThread = socket;
		try {
			openStream();
		} catch (IOException e) {
			System.out.println("Error gettin input" + e);
		}
		start();
	}

	public void openStream() throws IOException {
		in = new Scanner(newSocketThread.getInputStream());

	}

	public void closeStream() {
		if (in != null) {
			in.close();
		}
		this.interrupt();
	}

	public void run() {
		while (true) {
			while (in.hasNextLine()) {
				newClientThread.handle(in.nextLine());
			}
		}
	}

}