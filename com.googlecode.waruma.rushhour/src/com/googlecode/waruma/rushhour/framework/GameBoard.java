package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Das GameBoard orchestriet das Ausführen von Zügen und sorgt dafür, dass das
 * Spiel bei destruktiven Operationen in einem konsistenten Zustand bleibt.
 * 
 * @author Florian
 */
public class GameBoard implements Serializable {
	private static final long serialVersionUID = -7059872709872532815L;
	private ICollisionDetector collisionDetector;
	
	public ICollisionDetector getCollisionDetector() {
		return collisionDetector;
	}

	// Manuelle Implementierung eines Hash-Sets, da sich in der Java HashSet
	// implementierung der Hash nicht bei Änderungen am Objekt mitändert
	private Map<Integer, IGameBoardObject> gameBoardObjects;
	private Stack<IMove> moveHistory;

	/**
	 * Erstellt eine neues GameBoard unter Verwendung des übergebenen
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
	 * Fügt dem Spielbrett ein neues GameBoardObject hinzu
	 * 
	 * @param gameBoardObject
	 * @throws IllegalBoardPositionException
	 */
	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException {
		collisionDetector.addGameBoardObject(gameBoardObject);
		gameBoardObjects.put(gameBoardObject.hashCode(), gameBoardObject);

	}

	/**
	 * Entfernt ein GamBoardObject vom Spielfeld
	 * 
	 * @param gameBoardObject
	 */
	public void removeGameBoardObject(IGameBoardObject gameBoardObject) {
		if (gameBoardObjects.containsKey(gameBoardObject.hashCode())) {
			try {
				collisionDetector.removeGameBoardObject(gameBoardObject);
			} catch (IllegalArgumentException e) {
			}
			gameBoardObjects.remove(gameBoardObject.hashCode());
		}

	}

	/**
	 * Bewegt ein GameBoardObject an die angegebene Position auf dem Spielbrett
	 * 
	 * @param gameBoardObject
	 * @param position
	 * @throws IllegalBoardPositionException
	 */
	public void repositionGameBoardObject(IGameBoardObject gameBoardObject,
			Point position) throws IllegalBoardPositionException {
		// Objekt nicht beim Gameboard bekannt
		if (!gameBoardObjects.containsKey(gameBoardObject.hashCode())) {
			throw new IllegalBoardPositionException();
		}

		// Aus der Fahrzeugliste entfernen
		collisionDetector.moveGameBoardObjectToPosition(gameBoardObject,
				position);
		
		gameBoardObjects.remove(gameBoardObject.hashCode());
		// CollisionMap aktualisieren
		gameBoardObject.setPosition(position);
		// Wieder mit neuem Hash hinzufügen
		gameBoardObjects.put(gameBoardObject.hashCode(), gameBoardObject);

	}
	
	/**
	 * Rotiert das übergebene Objekt in die übergebene Orientierung
	 * @param gameBoardObject
	 * @param orientation
	 * @throws IllegalBoardPositionException 
	 */
	public void rotateGameBoardObject(IGameBoardObject gameBoardObject,
			Orientation orientation) throws IllegalBoardPositionException {
		if(!gameBoardObjects.containsKey(gameBoardObject.hashCode())){
			throw new IllegalBoardPositionException();
		}
		
		collisionDetector.rotateGameBoardObject(gameBoardObject, orientation);
		
		gameBoardObjects.remove(gameBoardObject.hashCode());
		// CollisionMap aktualisieren
		gameBoardObject.setOrientation(orientation);
		// Wieder mit neuem Hash hinzufügen
		gameBoardObjects.put(gameBoardObject.hashCode(), gameBoardObject);
	}
	
	public void rebuildGameBoardObjects(){
		Collection<IGameBoardObject> boardObjects = gameBoardObjects.values();
		HashMap<Integer, IGameBoardObject> newBoardObjects = new HashMap<Integer, IGameBoardObject>();
		for (IGameBoardObject boardObject : boardObjects) {
			newBoardObjects.put(boardObject.hashCode(), boardObject);
		}
		gameBoardObjects.clear();
		gameBoardObjects = newBoardObjects;
	}

	/**
	 * Gibt die Liste der auf dem Spielbrett vorhandenen Autos zurück
	 * 
	 * @return Collection der Autos
	 */
	public Collection<IGameBoardObject> getGameBoardObjects() {
		return gameBoardObjects.values();
	}

	/**
	 * Gibt den Stack der bisher ausgeführten Züge aus
	 * 
	 * @return MoveHistory Stack
	 */
	public Stack<IMove> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * Diese Methode führt einen Spielzug anhand des übergebenen IMove Objektes
	 * aus, sofern die Prüfungen positiv waren.
	 * 
	 * @param move
	 *            Auszuführender Zug
	 * @throws IllegalMoveException
	 *             Bei ungültigem Zug
	 */
	public void move(IMove move) throws IllegalMoveException {
		IMoveable moveable = move.getMoveable();
		int distance = move.getDistance();
		// Objekt nicht auf dem Spielbrett
		if (!gameBoardObjects.containsKey(moveable.hashCode())) {
			throw new IllegalMoveException();
		}

		// Zug prüfen
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