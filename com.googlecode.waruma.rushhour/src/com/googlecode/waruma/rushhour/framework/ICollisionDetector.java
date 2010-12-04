package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface ICollisionDetector {
	
  public void checkMove(IMove move) throws IllegalMoveException;
  public void doMove(IMove move) throws IllegalMoveException;
  public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException;
  public boolean hitPoint(IGameBoardObject gameBoardObject, Point point);
}