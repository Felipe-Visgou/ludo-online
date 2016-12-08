package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import client.Client;
import controller.FacadeMovimento;
import model.ConjuntoDePinos;
import model.Path;
import model.PinoEstruturado;
import model.Score;

@SuppressWarnings("serial")
public class Board extends JFrame {
	//Variaveis de Classe
	JButton RollDice;
	MouseHandler mHandler;
	ActionHandler aHandler;
	JLabel lImageIconDado;
	private boolean gameStarted;
	public static int round;
	private String nickname = null;
	private String daVez = null;

	private static Board boardfirstInstance = null;

	// Singleton da Class -- ConjuntoDePinos
	public static Board getInstance() {
		if (boardfirstInstance == null) {

			boardfirstInstance = new Board();
		}
		return boardfirstInstance;
	}

	// Construtor Default é usado quando iniciamos um jogo novo
	private Board() {gameStarted = false;}

	public String getNickName(){
		return this.nickname;
	}
	
	public void setNickName(String newNickName){
		this.nickname = newNickName;
	}
	
	public void setDaVez(String newDaVez){
		this.daVez = newDaVez;
	}
	
	public String getDaVez(){
		return this.daVez;
	}
	
	public void setGameStarted(boolean newGameStarted){
		this.gameStarted = newGameStarted;
	}
	
	public boolean getGameStarted(){
		return this.gameStarted;
	}
	
	public void initializeBoard(String UserNickName) {
		//Set user nickname
		this.setNickName(UserNickName);
		
		// Instanciando o Objeto que vai ser Observado
		PinoEstruturado pPino = new PinoEstruturado();

		// Criando Objeto do tipo DrawingBoard-- Singleton
		DrawingBoard.getInstancce();

		// Criando Objeto do tipo FacadeMovimento -- Singleton
		FacadeMovimento.getInstance();

		//Registra o Observer para PinoEstruturado
		FacadeMovimento.FacadeRegistraObserver(pPino);

		// Criando objeto do tipo ConjuntoDePinos -- Singleton
		ConjuntoDePinos.getInstancce().initializeConjuntoDePinos();

		// Criando objeto do tipo Dice -- Singleton
		Dice.getInstance();

		// Criando objeto do tipo Client -- Singleton
		FacadeMovimento.getInstance().initializeClient("127.0.0.1", 5501, getNickName());

		//Registra Observador para Cliente
		FacadeMovimento.FacadeRegistraObserverClient(Client.getInstance());

		// Criando objeto do tipo Path -- Singleton
		Path.getInstance();

		// Criando Objeto do Tipo Score -- Singleton
		Score.getInstancce();

		mHandler = new MouseHandler();
		aHandler = new ActionHandler();
		round = 0;

		// Definindo configuracoes defaults para o JFrame
		this.setSize(768, 640);
		this.setTitle("LUDO & FRIENDS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Centralizando a Janela com Tela do PC
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		// Criando Box para dividir o Frame em partes: Tabuleiro e Dado.
		Box thebox = Box.createVerticalBox();
		Box thebox2 = Box.createVerticalBox();
		Box thebox3 = Box.createVerticalBox();

		// Criando um Label para adicionar a img e chamando a funcao que cria as
		// imagens do dado
		lImageIconDado = new JLabel();

		// Atribuindo a um container a referencia da img
		Container cDice = lImageIconDado;

		// Adicionando a image ao box
		thebox.add(cDice);

		// Criando o botao(RollDice)
		RollDice = new JButton("Roll Dice");

		// Adicionando ActionListner ao botao RollDice
		RollDice.addActionListener(aHandler);

		// Adicionando o botao RollDice ao box
		thebox.add(RollDice);
		
		JLabel DisplayNick = new JLabel("Nick: " + this.getNickName());
		JLabel DisplayDaVez = new JLabel("É a vez do Jogador: " + this.getDaVez());
		
		thebox2.add(DisplayNick, BorderLayout.NORTH);
		thebox2.add(DisplayDaVez, BorderLayout.NORTH);
		
		thebox3.add(thebox, BorderLayout.NORTH);
		thebox3.add(thebox2, BorderLayout.SOUTH);

		// Adicionando o box do dado para o panel
		this.add(thebox3, BorderLayout.EAST);

		// Fazendo com que a area desenhada tome conta da parte central do frame
		this.add(DrawingBoard.getInstancce(), BorderLayout.CENTER);

		// Setting a posicao de centralizacao
		this.setLocation(xPos, yPos);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Mostrando a frame
		this.setVisible(true);
	}

	private class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == RollDice) {
				// Cria uma referencia para guardar a ImageIcon do Dado que
				// será
				// retorna pela MakingImageDice
				ImageIcon DadoImageIcon = null;

				if (round == 4) {
					round = 0;
				}

				switch (round) {
				case 0:
					// Set background color of Button RollDice as red --
					// indicating that is Red Round
					RollDice.setBackground(Color.red);
					break;

				case 1:
					// Set background color of Button RollDice as blue --
					// indicating that is blue Round
					RollDice.setBackground(Color.blue);
					break;

				case 2:
					// Set background color of Button RollDice as yellow --
					// indicating that is yellow Round
					RollDice.setBackground(Color.yellow);
					break;

				case 3:
					// Set background color of Button RollDice as green --
					// indicating that is green Round
					RollDice.setBackground(Color.green);
					break;
				}

				RollDice.setOpaque(true);

				RollDice.setEnabled(false);

				// Manda o dado gerar um número aleatório de novo
				FacadeMovimento.getInstance().RollDice();

				// Chama o MouseListener para tratar o evento click
				DrawingBoard.getInstancce().addMouseListener(mHandler);

				// Montando a Imagem com o numero aletatorio gerado passado
				// com
				// parametro
				DadoImageIcon = Dice.getInstance().MakingImageDice(Dice.getInstance().getRandNum());

				// Adicionando a imagem ao label
				lImageIconDado.setIcon(DadoImageIcon);

			}
		}
	}

	private class MouseHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int cX = e.getX();
			int cY = e.getY();

			if (FacadeMovimento.getInstance().setClickedCoordinates(cX, cY) == true) {
				if (round == 4) {
					round = 0;
				}

				switch (round) {
				case 0:
					RollDice.setBackground(Color.red);
					break;

				case 1:
					RollDice.setBackground(Color.blue);
					break;

				case 2:
					RollDice.setBackground(Color.yellow);
					break;

				case 3:
					RollDice.setBackground(Color.green);
					break;
				}

				RollDice.setOpaque(true);

				RollDice.setEnabled(true);
				if (Dice.getInstance().getRandNum() == 20 || Dice.getInstance().getRandNum() == 10) {
					RollDice.setEnabled(false);
				}
				FacadeMovimento.getInstance().boardSendMsg(packGameToClient());
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// RollDice.setText(String.format("Pressed at %d %d", e.getX(),
			// e.getY()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// RollDice.setText(String.format("U RELEASED THE BUTTON", e.getX(),
			// e.getY()));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// RollDice.setText(String.format("SELECIONE THE FUCKING PINO",
			// e.getX(), e.getY()));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// RollDice.setText(String.format("BOA SORTE!", e.getX(),
			// e.getY()));
		}

	}
	
	public String packGameToClient() {

		// Create the string that contains the Round info
		String PinsInfo = null;
		PinsInfo = getNickName();
		PinsInfo += "@" + Board.getInstance().round;
		// Create the string that contains the ConjuntodePinos info
		PinsInfo += "%";
		PinsInfo += "Red:" + " ";
		for (int i = 1; i < 5; i++) {
			// Gets the number of pin and transform to a string
			PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getRed(i).getNumero()) + " ";

			if (i < 4) {
				// Gets the house of pin and transform to a string
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getRed(i).getCasa()) + " ";
			} else {
				// Gets the house of pin and transform to a string
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getRed(i).getCasa());
			}
		}
		PinsInfo += "#";
		PinsInfo += "Blue:" + " ";
		for (int i = 1; i < 5; i++) {
			// Gets the numeber of pin and transform to a string
			PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getBlue(i).getNumero()) + " ";

			if (i < 4) {
				// Gets the house of pin and transform to a string
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getBlue(i).getCasa()) + " ";
			}

			else {
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getBlue(i).getCasa());
			}
		}
		PinsInfo += "#";
		PinsInfo += "Yellow:" + " ";
		for (int i = 1; i < 5; i++) {
			// Gets the number of pin and transform to a string
			PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getYellow(i).getNumero()) + " ";

			if (i < 4) {
				// Gets the house of pin and transform to a string
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getYellow(i).getCasa()) + " ";
			} else {
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getYellow(i).getCasa());
			}
		}
		PinsInfo += "#";
		PinsInfo += "Green:" + " ";
		for (int i = 1; i < 5; i++) {
			// Gets the number of pin and transform to a string
			PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getGreen(i).getNumero()) + " ";

			if (i < 4) {// Gets the house of pin and transform to a string
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getGreen(i).getCasa()) + " ";
			}

			else {
				PinsInfo += Integer.toString(ConjuntoDePinos.getInstancce().getGreen(i).getCasa());
			}
		}
		//System.out.println(PinsInfo);
		return PinsInfo;

	}
	
	public void RollDiceStatus(boolean b_status){
		this.RollDice.setEnabled(b_status);
	}

}
