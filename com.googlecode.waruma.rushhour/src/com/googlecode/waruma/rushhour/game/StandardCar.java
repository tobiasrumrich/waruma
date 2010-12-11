package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian
 * 
 *         Diese Klasse stellt ein Standardauto dar. Sie hält die obere, linke
 *         Koordinate, die Kollisionsmap und die Orientierung des Auto auf dem
 *         Spielbrett. Dazu ermöglicht sie das Bewegen eines Autos (Änderung der
 *         Position in point) durch Aufrufen move()-Funktion.
 * 
 */

public class StandardCar extends AbstractMoveable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4355928368405991476L;

	public StandardCar(Boolean[][] collisionMap, Point point,
			Orientation orientation) {
		super(collisionMap, point, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(int distance) throws IllegalMoveException {

		if (distance != 0) {
			Point currentPosition = getPosition();

			switch (getOrientation()) {
			case NORTH:
				currentPosition.setLocation(currentPosition.x,
						currentPosition.y - distance);
				break;

			case EAST:
				currentPosition.setLocation(currentPosition.x + distance,
						currentPosition.y);
				break;

			case SOUTH:
				currentPosition.setLocation(currentPosition.x,
						currentPosition.y + distance);
				break;

			case WEST:
				currentPosition.setLocation(currentPosition.x - distance,
						currentPosition.y);
				break;
			}
			
			super.setPosition(currentPosition);
			
		} else {
			throw new IllegalMoveException();
		}
	}
	
	@Override
	public void checkMove(int distance) throws IllegalMoveException {
		if(distance == 0){
			throw new IllegalMoveException();
		}
	}	

	@Override
	public int hashCode() {
		final int prime = 42209;
		int result = super.hashCode();
		result = prime * result;
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
		return true;
	}
}