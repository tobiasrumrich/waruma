package com.googlecode.waruma.rushhour.game;

import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;

/**
 * Decorater-Klasse für die SteeringCar-Eigenschaft. Das SteeringLockObjekt hat
 * zusätzlich eine moved-Variable, die auf "true" gesetzt wird, wenn das Objekt
 * einmal bewegt wurde. Beim nächsten Versuch das Objekt zu bewegen, wird eine
 * IllegalMoveException geworfen.
 * 
 * @author Fabian Malinowski
 */

public class SteeringLock extends AbstractAdditionalFunctionality implements
		IGameBoardObject, Serializable {

	private static final long serialVersionUID = 1959133511270794370L;
	private boolean moved = false;

	/**
	 * Erstellt ein neuen SteeringLock Decorator, der das übergebene Objekt
	 * erweitert.
	 * 
	 * @param moveable
	 */
	public SteeringLock(AbstractMoveable moveable) {
		super(moveable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality
	 * #checkMove(int)
	 */
	@Override
	public void checkMove(int distance) throws IllegalMoveException {
		super.checkMove(distance);
		if (moved) {
			throw new IllegalMoveException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality
	 * #equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SteeringLock other = (SteeringLock) obj;
		if (moved != other.moved) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality
	 * #hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 96223;
		int result = super.hashCode();
		result = prime * result + (moved ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality
	 * #move(int)
	 */
	@Override
	public void move(int distance) throws IllegalMoveException {

		if (!moved) {
			super.move(distance);
			moved = true;
		} else {
			throw new IllegalMoveException();
		}
	}

	/**
	 * Setzt den lock zurück, sodass ein zuvor gelocktes Auto wieder entlockt
	 * ist.
	 */
	public void unlock() {
		moved = false;
	}

}