package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * 
 * @author dep18237
 * 
 */
public class GameBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7059872709872532815L;
	private ICollisionDetector collisionDetector;
	private Set<IGameBoardObject> gameBoardObjects;
	private Stack<IMove> moveHistory;

	public GameBoard(ICollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
		this.gameBoardObjects = new HashSet<IGameBoardObject>();
		this.moveHistory = new Stack<IMove>();
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		Point position = gameBoardObject.getPosition();

		// throws IllegalBoardPositionException
		collisionDetector.addGameBoardObject(gameBoardObject);

		gameBoardObjects.add(gameBoardObject);

	}

	public Set<IGameBoardObject> getGameBoardObjects() {
		return gameBoardObjects;
	}

	public Stack<IMove> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * Diese Methode führt einen Spielzug anhand des übergebenen IMove Objektes
	 * aus.
	 * 
	 * @param move
	 * @throws IllegalMoveException
	 */
	public void move(IMove move) throws IllegalMoveException {
		if (!gameBoardObjects.contains(move.getMoveable())
				|| !(move.getMoveable() instanceof IMoveable)) {
			throw new IllegalMoveException();
		}

		// throws IllegalMoveException
		collisionDetector.checkMove(move);
		collisionDetector.doMove(move);

		// Der Collision Detector hat keine Exception geworfen, also machen wir
		// weiter
		for (IGameBoardObject gameBoardObject : gameBoardObjects) {
			if (gameBoardObject.equals(move.getMoveable())) {
				if (gameBoardObject instanceof IMoveable) {
					((IMoveable) gameBoardObject).move(move.getDistance());
				} else {
					throw new IllegalMoveException();
				}
			}
		}

		moveHistory.push(move);
	}

	@Override
	public int hashCode() {
		final int prime = 99991;
		int result = 1;
		result = prime
				* result
				+ ((collisionDetector == null) ? 0 : collisionDetector
						.hashCode());
		result = prime
				* result
				+ ((gameBoardObjects == null) ? 0 : gameBoardObjects.hashCode());
		result = prime * result
				+ ((moveHistory == null) ? 0 : moveHistory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameBoard other = (GameBoard) obj;
		if (collisionDetector == null) {
			if (other.collisionDetector != null)
				return false;
		} else if (!collisionDetector.equals(other.collisionDetector))
			return false;
		if (gameBoardObjects == null) {
			if (other.gameBoardObjects != null)
				return false;
		} else if (!gameBoardObjects.equals(other.gameBoardObjects))
			return false;
		if (moveHistory == null) {
			if (other.moveHistory != null)
				return false;
		} else if (!moveHistory.equals(other.moveHistory))
			return false;
		return true;
	}

	
	
}