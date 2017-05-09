package com.swe.user;

import com.swe.game.Subject;

public abstract class Observer {
	  
	   protected Subject subject;
	   public abstract void update();
}
