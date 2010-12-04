package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian
 * a
 * Diese Klasse stellt ein Standardauto dar. Sie hält die obere, linke Koordinate, die Kollisionsmap und die Orientierung des Auto auf dem Spielbrett. 
 * Dazu ermöglicht sie das Bewegen eines Autos (Änderung der Position in point) durch Aufrufen move()-Funktion.
 * 
 */

public class StandardCar extends AbstractMoveable{
  
	private Point currentPosition;

	public StandardCar(Boolean[][] collisionMap, Point point,
			Orientation orientation) {
		super(collisionMap, point, orientation);
		// TODO Auto-generated constructor stub
	}

	public void move(int distance) throws IllegalMoveException {
		
		currentPosition = getPosition();
	
			switch(getOrientation()) {
			case NORTH:
				currentPosition.setLocation(currentPosition.x, currentPosition.y - distance);
			    break; 

			case EAST:
				currentPosition.setLocation(currentPosition.x + distance, currentPosition.y);
				break;
			    
			case SOUTH:
				currentPosition.setLocation(currentPosition.x, currentPosition.y + distance);
				break;
		    
			case WEST:
				currentPosition.setLocation(currentPosition.x - distance, currentPosition.y);
				break;
		}
	}

}