package com.googlecode.waruma.rushhour.framework;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Das GameBoard orchestriet das Ausführen von Zügen und sorgt dafür, dass das
 * Spiel bei destruktiven Operationen in einem kosistenten Zustand bleibt.
 * 
 * @author Florian
 */
public class GameBoard implements Serializable {
	private static final long serialVersionUID = -7059872709872532815L;
	private ICollisionDetector collisionDetector;
	// Manuelle Implementierung eines Hash-Sets, da sich in der Java HashSet implementierung der 
	private Map<Integer, IGameBoardObject> gameBoardObjects;
	private Stack<IMove> moveHistory;

	public GameBoard(ICollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
		this.gameBoardObjects = new HashMap<Integer, IGameBoardObject>();
		this.moveHistory = new Stack<IMove>();
	}

	public ICollisionDetector getCollisionDetector() {
		return this.collisionDetector;
	}

	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		collisionDetector.addGameBoardObject(gameBoardObject);
		gameBoardObjects.put(gameBoardObject.hashCode(), gameBoardObject);

	}

	public Collection<IGameBoardObject> getGameBoardObjects() {
		return gameBoardObjects.values();
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
		IMoveable moveable = move.getMoveable();
		int distance = move.getDistance();

		if (!gameBoardObjects.containsKey(moveable.hashCode())) {
			throw new IllegalMoveException();
		}

		// throws IllegalMoveException
		collisionDetector.checkMove(move);
		moveable.checkMove(distance);

		gameBoardObjects.remove(moveable.hashCode());
		collisionDetector.doMove(move);
		moveable.move(distance);
		moveHistory.push(move);
		gameBoardObjects.put(moveable.hashCode(), (IGameBoardObject) moveable);

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