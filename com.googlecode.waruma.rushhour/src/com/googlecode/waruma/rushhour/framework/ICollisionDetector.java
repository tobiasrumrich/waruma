package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public interface ICollisionDetector {
	public void checkMove(IMove move) throws IllegalMoveException;

	public void doMove(IMove move) throws IllegalMoveException;

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException;

	public void moveGameBoardObjectToPosition(IGameBoardObject gameBoardObject,
			Point position) throws IllegalBoardPositionException;

	public boolean hitPoint(IGameBoardObject gameBoardObject, Point point);

	public boolean validTile(Point point);
}