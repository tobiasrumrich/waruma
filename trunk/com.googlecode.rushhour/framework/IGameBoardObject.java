package com.googlecode.waruma.rushhour.framework;
import java.awt.Point;

public interface IGameBoardObject {

    
  public Boolean[][] getCollisionMap();

  public Enum<Orientation> getOrientation();

  public Point getPosition();

  public void setPosition(Point position);

}