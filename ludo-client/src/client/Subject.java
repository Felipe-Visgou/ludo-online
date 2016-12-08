package client;

import client.Observer;

public interface Subject {
	
	public void register(Observer newObserver); //add an observer
	public void unregister(Observer deletedObserver); // delete an observer
	public void notifyObserver(); //notify all observer 

}
