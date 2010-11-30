package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;


public abstract class AbstractGameBoardObject implements IGameBoardObject, IMoveable {

  private Boolean[][] collisionMap;

  private Point position;

  private Enum orientation;
  

  @Override
  public Boolean[][] getCollisionMap() {
  	// TODO Auto-generated method stub
  	return null;
  }

  @Override
  public Enum<Orientation> getOrientation() {
  	// TODO Auto-generated method stub
  	return null;
  }

  @Override
  public Point getPosition() {
  	// TODO Auto-generated method stub
  	return null;
  }

  @Override
  public Point setPositon() {
  	// TODO Auto-generated method stub
  	return null;
  }

}