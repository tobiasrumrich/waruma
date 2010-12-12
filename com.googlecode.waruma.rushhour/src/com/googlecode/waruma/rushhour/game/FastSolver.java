package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.ISolver;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Der FastSolver bestimmt f�r ein beliebiges Spielbrett, sofern vorhanden, die
 * beste L�sung. Er ist in der Lage die L�sung f�r das komplexeste bekannte
 * RushHour Spielbrett (24.000 Brettpositionen, 93 Z�ge bis zur L�sung) in
 * deutlich unter einer Sekunde zu berechnen. Um dieses Ergebnis zu erreichen
 * wurde bei der Implementierung sehr auf Effizienz und weniger auf
 * Erweiterbarkeit und Verwenung bereits vorhandener Funktionalit�ten geachtet.
 * 
 * @author Florian
 */
public class FastSolver implements ISolver {
	private Set<Integer> visitedStates;
	private Queue<FastSolverState> stateQueue;
	private Map<Integer, IGameBoardObject> gameBoardObjectMap;
	private byte solutionX, solutionY;

	/**
	 * Erstellt ein neue Instanz des Fastsolvers f�r das �bergebene Spielbrett
	 * 
	 * @param gameBoard
	 *            Beliebiges RushHour-Spielbrett
	 */
	public FastSolver(GameBoard gameBoard) {
		gameBoardObjectMap = new HashMap<Integer, IGameBoardObject>();
		stateQueue = new LinkedList<FastSolverState>();
		visitedStates = new HashSet<Integer>();

		FastSolverState initialState = new FastSolverState(6, 6);
		for (IGameBoardObject gameBoardObject : gameBoard.getGameBoardObjects()) {
			initialState
					.addSolverCar(createSolverCarFromGameBoardObject(gameBoardObject));
		}
		stateQueue.add(initialState);
	}

	/**
	 * Bestimmt �ber Breitensuche die zum Gewinnen n�tigen Z�ge
	 * 
	 * @return Liste der Z�ge zum Erreichen der Zielposition
	 */
	public List<IMove> solveGameBoard() {
		while (!stateQueue.isEmpty()) {
			FastSolverState currentState = stateQueue.remove();			
			// Ziel erreicht?
			if (currentState.player.x == solutionX
					&& currentState.player.y == solutionY) {
				return reconstructMoveList(currentState);
			}
			// Z�ge ermitteln
			computeAllPossibleMoves(currentState);
		}
		System.out.println("Keine L�sung gefunden");
		return null;
	}
	
	/**
	 * Bestimmt alle in einem gegebenen SolverState durchf�hrbaren Z�ge und
	 * leitet diese an addStateToQueue weiter
	 * 
	 * @param state
	 *            SolverState der gepr�ft wird
	 */
	private void computeAllPossibleMoves(FastSolverState state) {
		byte listIndex = 0;
		for (FastSolverCar car : state.cars) {
			// In positiver Zugrichtung
			byte distance = 0;
			boolean valid = true;
			while (valid) {
				distance++;
				valid = state.validTile(car, distance);
				if (valid) {
					createNewStateAndPropose(state, listIndex, distance);
				}
			}
			// In negativer Zugrichtung
			distance = 0;
			valid = true;
			while (valid) {
				distance--;
				valid = state.validTile(car, distance);
				if (valid) {
					createNewStateAndPropose(state, listIndex, distance);
				}
			}
			listIndex++;
		}
	}

	/**
	 * Erstellt den neuen SolverState auf der Basis des �bergeben Status und
	 * Zuges und f�gt diesen, sofern er neu ist in die Queue der zu
	 * untersuchenden Stati ein.
	 * 
	 * @param state
	 *            Momentaner Status
	 * @param carListIndex
	 *            Index des zu bewegenden Autos in der Autoliste des momentanen
	 *            Status
	 * @param distance
	 *            Distanz der Bewegung
	 */
	private void createNewStateAndPropose(FastSolverState state,
			byte carListIndex, byte distance) {
		FastSolverState newState = new FastSolverState(state);
		newState.moveCar(carListIndex, distance);

		if (visitedStates.add(newState.hashCode())) {
			stateQueue.add(newState);
		}
	}

	/**
	 * Rekonstuiert von dem �bergebenen Status aus eine Liste der bisher
	 * get�tigten Z�ge in Ausf�hrungsreihenfolge
	 * 
	 * @param currentState
	 *            Solver-Status
	 * @return Liste der Z�ge
	 */
	private List<IMove> reconstructMoveList(FastSolverState currentState) {
		LinkedList<IMove> moveList = new LinkedList<IMove>();

		while (currentState.previousState != null) {
			IGameBoardObject movedObject = gameBoardObjectMap
					.get(currentState.movedCar);
			int distance = currentState.movedDistance;

			if (movedObject.getOrientation() == Orientation.WEST
					|| movedObject.getOrientation() == Orientation.NORTH) {
				distance *= -1;
			}

			IMove move = new Move((IMoveable) movedObject, distance);
			moveList.addFirst(move);
			currentState = currentState.previousState;
		}

		return moveList;
	}

	/**
	 * Erstellt aus dem �bergebenen GameBoardObject ein SolverCar mit den gleichen Eigenschaften
	 * @param gameBoardObject Spielbrettobjekt
	 * @return SolverCar
	 */
	private FastSolverCar createSolverCarFromGameBoardObject(
			IGameBoardObject gameBoardObject) {
		byte x, y, length;
		boolean isPlayer = false, isLockCar = false, locked = false, orientation = false;
		// Position extrahieren
		x = (byte) gameBoardObject.getPosition().x;
		y = (byte) gameBoardObject.getPosition().y;
		length = (byte) gameBoardObject.getCollisionMap().length;
		// Richtung setzen
		if (gameBoardObject.getOrientation() == Orientation.EAST
				|| gameBoardObject.getOrientation() == Orientation.WEST) {
			orientation = true;
		}
		// Spielerdaten extrahieren
		if (gameBoardObject instanceof IPlayer) {
			IPlayer player = (IPlayer) gameBoardObject;
			this.solutionX = (byte) player.getDestination().x;
			this.solutionY = (byte) player.getDestination().y;
			if (orientation) {
				this.solutionX--;
			} else {
				this.solutionY--;
			}

			isPlayer = true;
		}
		// Lenkradschloss
		if (gameBoardObject instanceof SteeringLock) {
			isLockCar = true;
		}
		// Nicht beweglich
		if (!(gameBoardObject instanceof IMoveable)) {
			locked = true;
		}

		// Objekt instantiieren
		FastSolverCar car = new FastSolverCar(x, y, length, orientation,
				isPlayer, isLockCar, locked);
		// Zuordnung speichern
		gameBoardObjectMap.put(car.id, gameBoardObject);

		return car;
	}
}
