package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class SteeringLock extends AbstractAdditionalFunctionality {

	public SteeringLock(Boolean[][] collisionMap, Point position,
			Enum<Orientation> orientation) {
		super(collisionMap, position, orientation);
		// TODO Auto-generated constructor stub
	}

	public AbstractAdditionalFunctionality myAbstractAdditionalFunctionality;

	public void move(int distance) {
	}

}