package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class StandardCar extends AbstractGameBoardObject implements IMoveable {
  
  public StandardCar(Boolean[][] collisionMap, Point point,
			Enum<Orientation> orientation) {
		super(collisionMap, point, orientation);
		// TODO Auto-generated constructor stub
	}

	public void move(int distance) {
		
	}





}