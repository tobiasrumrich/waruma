package com.googlecode.waruma.rushhour.framework;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Das GameBoard orchestriet das Ausf�hren von Z�gen und sorgt daf�r, dass das
 * Spiel bei destruktiven Operationen in einem konsistenten Zustand bleibt.
 * 
 * @author Florian
 */
public class GameBoard implements Serializable {
	private static final long serialVersionUID = -7059872709872532815L;
	private ICollisionDetector collisionDetector;
	// Manuelle Implementierung eines Hash-Sets, da sich in der Java HashSet
	// implementierung der Hash nicht bei �nderungen am Objekt mit�ndert
	private Map<Integer, IGameBoardObject> gameBoardObjects;
	private Stack<IMove> moveHistory;

	/**
	 * Erstellt eine neues GameBoard unter Verwendung des �bergebenen
	 * CollisionDetectors
	 * 
	 * @param collisionDetector
	 */
	public GameBoard(ICollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
		this.gameBoardObjects = new HashMap<Integer, IGameBoardObject>();
		this.moveHistory = new Stack<IMove>();
	}

	/**
	 * F�gt dem Spielbrett ein neues GameBoardObject hinzu
	 * @param gameBoardObject
	 * @throws IllegalBoardPositionException
	 */
	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		collisionDetector.addGameBoardObject(gameBoardObject);
		gameBoardObjects.put(gameBoardObject.hashCode(), gameBoardObject);

	}

	/**
	 * Gibt die Liste der auf dem Spielbrett vorhandenen Autos zur�ck
	 * @return Collection der Autos
	 */
	public Collection<IGameBoardObject> getGameBoardObjects() {
		return gameBoardObjects.values();
	}

	/**
	 * Gibt den Stack der bisher ausgef�hrten Z�ge aus
	 * @return MoveHistory Stack
	 */
	public Stack<IMove> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * Diese Methode f�hrt einen Spielzug anhand des �bergebenen IMove Objektes
	 * aus, sofern die Pr�fungen positiv waren.
	 * 
	 * @param move
	 *            Auszuf�hrender Zug
	 * @throws IllegalMoveException
	 *             Bei ung�ltigem Zug
	 */
	public void move(IMove move) throws IllegalMoveException {
		IMoveable moveable = move.getMoveable();
		int distance = move.getDistance();
		// Objekt nicht auf dem Spielbrett
		if (!gameBoardObjects.containsKey(moveable.hashCode())) {
			throw new IllegalMoveException();
		}

		// Zug pr�fen
		collisionDetector.checkMove(move);
		moveable.checkMove(distance);

		// CollisionDetector aktualisieren
		collisionDetector.doMove(move);

		// Moveable aktualisieren
		gameBoardObjects.remove(moveable.hashCode());
		moveable.move(distance);
		gameBoardObjects.put(moveable.hashCode(), (IGameBoardObject) moveable);

		// Move History aktualisieren
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