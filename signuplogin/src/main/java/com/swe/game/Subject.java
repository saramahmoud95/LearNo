package com.swe.game;

import java.util.ArrayList;

import com.swe.user.Observer;

public class Subject {

	
	 private ArrayList<Observer> observers = new ArrayList<Observer>();
	 private String comment ;
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	    notifyAllObservers();

	}
	
	 public void attach(Observer observer){
	      observers.add(observer);		
	}

	public void notifyAllObservers(){
	      for (Observer observer : observers) {
	         observer.update();
	      }
	   } 

	   
	   
}
