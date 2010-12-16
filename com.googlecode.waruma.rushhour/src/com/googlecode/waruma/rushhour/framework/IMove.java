package com.googlecode.waruma.rushhour.framework;

public interface IMove {

	public int getDistance();

	public IMoveable getMoveable();
	
	public void revertDirection();

}