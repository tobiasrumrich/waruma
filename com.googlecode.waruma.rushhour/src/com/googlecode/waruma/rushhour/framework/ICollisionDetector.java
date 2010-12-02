package com.googlecode.waruma.rushhour.framework;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;

public interface ICollisionDetector {
	
  public void checkCollision(IMove move);
  public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException;

}