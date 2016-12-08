package model;

import model.Observer;

public interface Subject {

	public void register(Observer o); //add an observer
	public void unregister(Observer o); // delete an observer
	public void notifyObserver(); //notify all observer 

}
