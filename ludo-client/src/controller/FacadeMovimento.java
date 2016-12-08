package controller;

import client.Client;
import model.ConjuntoDePinos;
import model.MovesAndRules;
import model.Score;
import view.Board;
import view.Dice;
import view.DrawingBoard;

public class FacadeMovimento{
	private int CoordinateX;
	private int CoordinateY;
	Dice dice;

	private static FacadeMovimento fmfirstInstance = null;

	//Singleton da Class -- FacadeMovimento
	public static FacadeMovimento getInstance(){
		if(fmfirstInstance == null){

			fmfirstInstance = new FacadeMovimento();
		}

		return fmfirstInstance;
	}

	private FacadeMovimento(){
		setCoordinateX(-1);
		setCoordinateY(-1);
		dice = Dice.getInstance();
	}

	public void RollDice(){
		dice.generatingRandomNumberDice();
	}

	public int getCoordinateY() {
		return CoordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		CoordinateY = coordinateY;
	}

	public int getCoordinateX() {
		return CoordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		CoordinateX = coordinateX;
	}
	
	public String getPackPinosToClient(){
		return Board.getInstance().packGameToClient();
	}
	
	public  void popUpTimedOut(){
		DrawingBoard.getInstancce().popUp();
	}
	public void gameStarted(){
		Board.getInstance().setGameStarted(true);
	}
	public void setPlayerTurn(String currentPlayer){
		Board.getInstance().setDaVez(currentPlayer);
	}
	
	public String getPlayerTurn(){
		return Board.getInstance().getDaVez();
	}
	
	public String getNickBoard(){
		return Board.getInstance().getNickName();
	}
	
	public void setNickBoard(String newNick){
		Board.getInstance().setNickName(newNick);
	}
	
	public void initializeClient(String newServerName, int NewPort, String newNickname ){
		Client.getInstance().initializeClient(newServerName, NewPort, newNickname);
	}
	
	public void FacadeRollDiceStatus(boolean b_status){
		Board.getInstance().RollDiceStatus(b_status);
	}
	
	public void BoardSetRound(int newRound){
		Board.getInstance().round = newRound;
	}
	
	public void boardSendMsg(String Jogada){
		Client.getInstance().sendMsg(Jogada);
	}
	public void _repaint(){
		DrawingBoard.getInstancce().repaint();
		DrawingBoard.getInstancce().revalidate();
		
	}
	public boolean setClickedCoordinates(int cX, int cY){
		setCoordinateX(cX);
		setCoordinateY(cY); 
		MovesAndRules.getInstancce().InicializaMovesAndRules(cX, cY, dice.getRandNum());

		if(MovesAndRules.getInstancce().JustDoIt() == true){
			//Update os valores de Score
			//Client.getInstance().sendMsg(getPackPinosToClient());
			Score.getInstancce().updateScore();
			return true;
		}

		else{
			return false;
		}

	}

	public static void FacadeRegistraObserver(model.Subject PinoEstruturado){
		DrawingBoard.getInstancce().addTabuleiro(PinoEstruturado);
	}
	
	public static void FacadeRegistraObserverClient(client.Subject Client){
		ConjuntoDePinos.getInstancce().addPinoEstruturadotoClient(Client);
	}
}
