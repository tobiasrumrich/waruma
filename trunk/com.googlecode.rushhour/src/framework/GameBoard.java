package com.googlecode.waruma.rushhour.framework;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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

	public void setCollisionDetector(ICollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
	}

	public void move(IMove move) {
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject) {
	}

}