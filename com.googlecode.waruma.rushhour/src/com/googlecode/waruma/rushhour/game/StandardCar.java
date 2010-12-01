package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class StandardCar extends AbstractMoveable{
  
	public StandardCar(Boolean[][] collisionMap, Point point,
			Enum<Orientation> orientation) {
		super(collisionMap, point, orientation);
		// TODO Auto-generated constructor stub
	}

	public void move(int distance) throws IllegalMoveException{
		
	}





}