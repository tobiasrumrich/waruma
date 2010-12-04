package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;
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
public class GameBoard {

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

		// throws IllegalMoveException
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
		collisionDetector.move(move);

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

}