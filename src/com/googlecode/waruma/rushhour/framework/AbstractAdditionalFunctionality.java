package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;


public abstract class AbstractAdditionalFunctionality extends
		AbstractGameBoardObject implements IMoveable {

	public IMoveable myIMoveable;

	public abstract void move(Integer distance);



}