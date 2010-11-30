package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;



public abstract class AbstractAdditionalFunctionality extends
		AbstractGameBoardObject implements IMoveable {

	public AbstractAdditionalFunctionality(Boolean[][] collisionMap,
			Point position, Enum<Orientation> orientation) {
		super(collisionMap, position, orientation);
		// TODO Auto-generated constructor stub
	}

	public IMoveable myIMoveable;

	public abstract void move(int distance);



}