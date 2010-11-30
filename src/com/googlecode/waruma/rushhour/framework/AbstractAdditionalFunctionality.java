package com.googlecode.waruma.rushhour.framework;



public abstract class AbstractAdditionalFunctionality extends
		AbstractGameBoardObject implements IMoveable {

	public IMoveable myIMoveable;

	public abstract void move(Integer distance);



}