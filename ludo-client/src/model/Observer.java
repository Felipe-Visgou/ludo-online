package model;

import java.awt.Color;

//The Observers update method is called when the Subject changes

public interface Observer {
	
	public void update(int nCasa, Color Cor, int nPino ); //Pensar quais variaveis devemos passar pro Update..
}
