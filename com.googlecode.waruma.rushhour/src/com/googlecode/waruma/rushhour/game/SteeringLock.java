package com.googlecode.waruma.rushhour.game;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.AdditionalFunctionality;



/*
* @author fabian
* 
* Decorater-Klasse für die SteeringCar-Eigenschaft. Das SteeringLockObjekt hat zusätzlich eine moved-Variable, die auf "true" gesetzt wird, wenn das Objekt einmal bewegt wurde.
* Beim nächsten Versuch das Objekt zu bewegen, wird eine IllegalMoveException geworfen.
* 
*/

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