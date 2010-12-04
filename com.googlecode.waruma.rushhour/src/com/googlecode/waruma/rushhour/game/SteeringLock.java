package com.googlecode.waruma.rushhour.game;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.AdditionalFunctionality;

public class SteeringLock extends AdditionalFunctionality {

	private boolean moved = false;

	public SteeringLock(AbstractMoveable moveable) {
		super(moveable);
	}
		
	public void move(int distance) throws IllegalMoveException {
		
		if (moved==false) {
			super.move(distance);
			moved=true;
		} else{
			throw new IllegalMoveException();
		}	
	}
	
	
	

}