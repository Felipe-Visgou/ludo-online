package model;

public class Score {
	public int ScoreRed;
	public int ScoreBlue;
	public int ScoreYellow;
	public int ScoreGreen;

	private static Score SfirstInstance = null;

	//Singleton da Class -- Score
	public static Score getInstancce(){
		if(SfirstInstance == null){

			SfirstInstance = new Score();
		}

		return SfirstInstance;
	}

	private Score(){
		this.ScoreRed = -1;
		this.ScoreBlue = -1;
		this.ScoreYellow = -1;
		this.ScoreGreen = -1;
	}

	public void updateScore(){
		this.ScoreRed = 0;
		this.ScoreBlue = 0;
		this.ScoreYellow = 0;
		this.ScoreGreen = 0;

		for(int i = 1 ; i < 5; i++){
			this.ScoreRed += 59  - ConjuntoDePinos.getInstancce().getRed(i).getCasa();
			this.ScoreBlue += 59  - ConjuntoDePinos.getInstancce().getBlue(i).getCasa();
			this.ScoreYellow += 59  - ConjuntoDePinos.getInstancce().getYellow(i).getCasa();
			this.ScoreGreen += 59  - ConjuntoDePinos.getInstancce().getGreen(i).getCasa();
		}
	}

	public int getScoreRed(){
		return this.ScoreRed;
	}

	public int getScoreBlue(){
		return this.ScoreBlue;
	}

	public int getScoreYellow(){
		return this.ScoreYellow;
	}

	public int getScoreGreen(){
		return this.ScoreGreen;
	}
}
