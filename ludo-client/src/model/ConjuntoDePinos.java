package model;

import java.awt.Color;
import controller.FacadeMovimento;
import view.Board;

public class ConjuntoDePinos implements client.Observer {
	//Static used as a counter
	private static int ObserverIDTracker = 0;
	//Used to track the observer
	private int ObserverID;
	
	PinoEstruturado[] GreenPino = new PinoEstruturado[4];
	PinoEstruturado[] RedPino = new PinoEstruturado[4];
	PinoEstruturado[] BluePino = new PinoEstruturado[4];
	PinoEstruturado[] YellowPino = new PinoEstruturado[4];

	int verde6 = 0;
	int vermelho6 = 0;
	int azul6 = 0;
	int amarelo6 = 0;


	private static ConjuntoDePinos cpfirstInstance = null;

	//Singleton da Class -- ConjuntoDePinos
	public static ConjuntoDePinos getInstancce(){
		if(cpfirstInstance == null){

			cpfirstInstance = new ConjuntoDePinos();
		}

		return cpfirstInstance;
	}

	private ConjuntoDePinos(){}

	public PinoEstruturado getGreen(int numero){
		return this.GreenPino[numero-1];
	}

	public PinoEstruturado getRed(int numero){
		return this.RedPino[numero-1];
	}

	public PinoEstruturado getBlue(int numero){
		return this.BluePino[numero-1];
	}

	public PinoEstruturado getYellow(int numero){
		return this.YellowPino[numero-1];
	}
	
	public void initializeConjuntoDePinos(){

		for(int i = 1; i < 5; i++){
			this.RedPino[i-1] = new PinoEstruturado();
			this.GreenPino[i-1] = new PinoEstruturado();
			this.BluePino[i-1] = new PinoEstruturado();
			this.YellowPino[i-1] = new PinoEstruturado();
		}

		for(int i = 1; i < 5; i++){
			if( i == 1 ){

				this.RedPino[i-1].setNumero(i);
				this.RedPino[i-1].setColor(Color.red);
				this.RedPino[i-1].setCasa(1);
				this.BluePino[i-1].setNumero(i);
				this.BluePino[i-1].setColor(Color.blue);
				this.BluePino[i-1].setCasa(1);
				this.YellowPino[i-1].setNumero(i);
				this.YellowPino[i-1].setColor(Color.yellow);
				this.YellowPino[i-1].setCasa(1);
				this.GreenPino[i-1].setNumero(i);
				this.GreenPino[i-1].setColor(Color.green);
				this.GreenPino[i-1].setCasa(1);

			}

			else{
				this.RedPino[i-1].setNumero(i);
				this.RedPino[i-1].setColor(Color.red);
				this.RedPino[i-1].setCasa(0);
				this.BluePino[i-1].setNumero(i);
				this.BluePino[i-1].setColor(Color.blue);
				this.BluePino[i-1].setCasa(0);
				this.YellowPino[i-1].setNumero(i);
				this.YellowPino[i-1].setColor(Color.yellow);
				this.YellowPino[i-1].setCasa(0);
				this.GreenPino[i-1].setNumero(i);
				this.GreenPino[i-1].setColor(Color.green);
				this.GreenPino[i-1].setCasa(0);
			}
		}
		for(int i = 0; i < 4;i++){
			this.RedPino[i].notifyObserver();
			this.BluePino[i].notifyObserver();
			this.YellowPino[i].notifyObserver();
			this.GreenPino[i].notifyObserver();
		}
	}
	
	/****************** Start: client.Observer ******************/
	@Override
	public void updateClient(String NovaJogada) {
		
		//Break lines into pieces
		System.out.println(NovaJogada);
		String[] parts = NovaJogada.split("%");
		String[] partpart = parts[0].split("@");
		String jogadorSeguinte = partpart[0];
		
		//seta o round atual
		String newRound = partpart[1];
		int Round = Integer.parseInt(newRound);
		
		FacadeMovimento.getInstance().BoardSetRound(Round);
		
		System.out.println("Jogador Seguinte: " + jogadorSeguinte);
		
		if(!FacadeMovimento.getInstance().getNickBoard().equals(jogadorSeguinte)){
			System.out.println("Dado: False " + FacadeMovimento.getInstance().getNickBoard());
			FacadeMovimento.getInstance().FacadeRollDiceStatus(false);
		}else{
			FacadeMovimento.getInstance().FacadeRollDiceStatus(true);
			System.out.println("Dado: True " +FacadeMovimento.getInstance().getNickBoard());
		}
		
		String estadoAtual = parts[1];
		String[] quebraNovaJogada = estadoAtual.split("#");
		FacadeMovimento.getInstance().setPlayerTurn(jogadorSeguinte);
		
		//Red: 1 1 0 1 0 1 0#Blue: 1 0 1 0 0 1 0 1#...
		
		String [] RedPinos = quebraNovaJogada[0].split(" ");
		
		this.RedPino[0].setNumero(Integer.parseInt(RedPinos[1]));
		this.RedPino[0].setColor(Color.red);
		this.RedPino[0].setCasa(Integer.parseInt(RedPinos[2]));
		this.RedPino[1].setNumero(Integer.parseInt(RedPinos[3]));
		this.RedPino[1].setColor(Color.red);
		this.RedPino[1].setCasa(Integer.parseInt(RedPinos[4]));
		this.RedPino[2].setNumero(Integer.parseInt(RedPinos[5]));
		this.RedPino[2].setColor(Color.red);
		this.RedPino[2].setCasa(Integer.parseInt(RedPinos[6]));
		this.RedPino[3].setNumero(Integer.parseInt(RedPinos[7]));
		this.RedPino[3].setColor(Color.red);
		this.RedPino[3].setCasa(Integer.parseInt(RedPinos[8]));
		
		String [] BluePinos = quebraNovaJogada[1].split(" ");
		
		this.BluePino[0].setNumero(Integer.parseInt(BluePinos[1]));
		this.BluePino[0].setColor(Color.blue);
		this.BluePino[0].setCasa(Integer.parseInt(BluePinos[2]));
		this.BluePino[1].setNumero(Integer.parseInt(BluePinos[3]));
		this.BluePino[1].setColor(Color.blue);
		this.BluePino[1].setCasa(Integer.parseInt(BluePinos[4]));
		this.BluePino[2].setNumero(Integer.parseInt(BluePinos[5]));
		this.BluePino[2].setColor(Color.blue);
		this.BluePino[2].setCasa(Integer.parseInt(BluePinos[6]));
		this.BluePino[3].setNumero(Integer.parseInt(BluePinos[7]));
		this.BluePino[3].setColor(Color.blue);
		this.BluePino[3].setCasa(Integer.parseInt(BluePinos[8]));
		
		String [] YellowPinos = quebraNovaJogada[2].split(" ");
		
		this.YellowPino[0].setNumero(Integer.parseInt(YellowPinos[1]));
		this.YellowPino[0].setColor(Color.yellow);
		this.YellowPino[0].setCasa(Integer.parseInt(YellowPinos[2]));
		this.YellowPino[1].setNumero(Integer.parseInt(YellowPinos[3]));
		this.YellowPino[1].setColor(Color.yellow);
		this.YellowPino[1].setCasa(Integer.parseInt(YellowPinos[4]));
		this.YellowPino[2].setNumero(Integer.parseInt(YellowPinos[5]));
		this.YellowPino[2].setColor(Color.yellow);
		this.YellowPino[2].setCasa(Integer.parseInt(YellowPinos[6]));
		this.YellowPino[3].setNumero(Integer.parseInt(YellowPinos[7]));
		this.YellowPino[3].setColor(Color.yellow);
		this.YellowPino[3].setCasa(Integer.parseInt(YellowPinos[8]));
		
		String [] GreenPinos = quebraNovaJogada[3].split(" ");
		
		this.GreenPino[0].setNumero(Integer.parseInt(GreenPinos[1]));
		this.GreenPino[0].setColor(Color.green);
		this.GreenPino[0].setCasa(Integer.parseInt(GreenPinos[2]));
		this.GreenPino[1].setNumero(Integer.parseInt(GreenPinos[3]));
		this.GreenPino[1].setColor(Color.green);
		this.GreenPino[1].setCasa(Integer.parseInt(GreenPinos[4]));
		this.GreenPino[2].setNumero(Integer.parseInt(GreenPinos[5]));
		this.GreenPino[2].setColor(Color.green);
		this.GreenPino[2].setCasa(Integer.parseInt(GreenPinos[6]));
		this.GreenPino[3].setNumero(Integer.parseInt(GreenPinos[7]));
		this.GreenPino[3].setColor(Color.green);
		this.GreenPino[3].setCasa(Integer.parseInt(GreenPinos[8]));
		for(int i = 0; i < 4;i++){
			this.RedPino[0].setColor(Color.red);
			this.RedPino[i].notifyObserver();
			this.RedPino[0].setColor(Color.blue);
			this.BluePino[i].notifyObserver();
			this.RedPino[0].setColor(Color.yellow);
			this.YellowPino[i].notifyObserver();
			this.RedPino[0].setColor(Color.green);
			this.GreenPino[i].notifyObserver();
		}
		//Repaint the client
		FacadeMovimento.getInstance()._repaint();
	}
	
	public void addPinoEstruturadotoClient(client.Subject Client){
		
		//Incrementando o contado statico
		this.ObserverID = ++ObserverIDTracker;

		//Mensagem que notifica o usuario de um novo observador
		System.out.println("Client: New Observer" + this.ObserverID);
		
		Client.register(this);
		
	}
	
	/****************** END: model.Observer ******************/

}
