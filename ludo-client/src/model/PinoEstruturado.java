package model;

import java.awt.Color;
import java.util.ArrayList;

public class PinoEstruturado implements Subject {	

	// Creates an ArrayList to hold all observers
	private static ArrayList<model.Observer> observers = new ArrayList<model.Observer>();

	//Criar as variaveis que a gente vai usar para fazer o update
	int casa;
	int numero;
	Color cor;

	public PinoEstruturado(){
		this.casa = 0;
		this.numero = 0;
		this.cor = null;
	}

	public Color getColor(){
		return this.cor;
	}

	public int getCasa(){
		return this.casa;
	}

	public int getNumero(){
		return this.numero;
	}

	public void casaSet(int nCasa){
		this.casa = nCasa;
	}

	public void setCasa(int mCasa){
		this.casa = mCasa;
	//	notifyObserver();
	}

	public void setColor(Color color){
		this.cor = color;
	}

	public void setNumero(int nPino){
		this.numero = nPino;
	}

	/****************** Start: model.Subject ******************/
	@Override
	public void register(model.Observer newObserver) {
		// Adiciona um novo Observer a lista de Observers

		System.out.println("Pino: Observador Registrado!");

		observers.add(newObserver);

	}

	@Override
	public void unregister(model.Observer deleteObserver) {
		//Get the index of the observer that is going to be deleted.
		int ObserverIndex = observers.indexOf(deleteObserver);

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
			observer.update(this.casa, this.cor, this.numero);
		}
	}

	/****************** END: model.Subject ******************/
}
