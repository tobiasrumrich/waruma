package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

public interface IGameBoardObject {

	public Boolean[][] getCollisionMap();

	public Orientation getOrientation();
	
	public void setOrientation(Orientation orientation);

	public Point getPosition();

	public void setPosition(Point position);

}