package com.googlecode.waruma.rushhour.framework;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

public class GameBoard {

	private ICollisionDetector collisionDetector;
	private Set<IGameBoardObject> gameBoardObjects;
	private Stack<IMove> moveHistory;

	public GameBoard(ICollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
		this.gameBoardObjects = new HashSet<IGameBoardObject>();
		this.moveHistory = new Stack<IMove>();
	}

	public Set<IGameBoardObject> getGameBoardObjects() {
		return gameBoardObjects;
	}

	public Stack<IMove> getMoveHistory() {
		return moveHistory;
	}

	public void move(IMove move) throws IllegalMoveException {
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
	}

}