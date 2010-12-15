package com.googlecode.waruma.rushhour.game;

import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.AdditionalFunctionality;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;

/*
 * @author fabian
 * 
 * Decorater-Klasse für die SteeringCar-Eigenschaft. Das SteeringLockObjekt hat zusätzlich eine moved-Variable, die auf "true" gesetzt wird, wenn das Objekt einmal bewegt wurde.
 * Beim nächsten Versuch das Objekt zu bewegen, wird eine IllegalMoveException geworfen.
 * 
 */

public class SteeringLock extends AdditionalFunctionality implements
		IGameBoardObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1959133511270794370L;
	private boolean moved = false;

	public SteeringLock(AbstractMoveable moveable) {
		super(moveable);
	}

	@Override
	public void move(int distance) throws IllegalMoveException {

		if (!moved) {
			super.move(distance);
			moved = true;
		} else {
			throw new IllegalMoveException();
		}
	}
	
	public void unlock(){
		moved = false;
	}
	
	@Override
	public void checkMove(int distance) throws IllegalMoveException {
		super.checkMove(distance);
		if(moved){
			throw new IllegalMoveException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 96223;
		int result = super.hashCode();
		result = prime * result + (moved ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SteeringLock other = (SteeringLock) obj;
		if (moved != other.moved)
			return false;
		return true;
	}

}