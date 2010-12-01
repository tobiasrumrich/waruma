package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public abstract class AbstractAdditionalFunctionality extends AbstractMoveable{
	public AbstractMoveable abstractMoveable;
	
	public AbstractAdditionalFunctionality(AbstractMoveable abstractMoveable) {
		super(abstractMoveable.getCollisionMap(), abstractMoveable.getPosition(), abstractMoveable.getOrientation());
	}

	

	public abstract void move(int distance) throws IllegalMoveException;



}