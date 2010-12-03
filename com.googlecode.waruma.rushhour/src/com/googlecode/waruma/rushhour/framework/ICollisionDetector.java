package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface ICollisionDetector {
	
  public void move(IMove move) throws IllegalMoveException;
  public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException;

}