package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.util.List;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface ICollisionDetector {
  public Boolean[][] getCollisionMap();	
  public void checkMove(IMove move) throws IllegalMoveException;
  public void doMove(IMove move) throws IllegalMoveException;
  public void doMoveWithoutCheck(IMove move);
  public void addGameBoardObject(IGameBoardObject gameBoardObject) throws IllegalBoardPositionException;
  public boolean hitPoint(IGameBoardObject gameBoardObject, Point point);
  public List<IMove> getValidMoves(IGameBoardObject gameBoardObject);

}