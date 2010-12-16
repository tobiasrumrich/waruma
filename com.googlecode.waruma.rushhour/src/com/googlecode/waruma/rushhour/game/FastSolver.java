package com.googlecode.waruma.rushhour.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.ISolver;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Der FastSolver bestimmt für ein beliebiges Spielbrett, sofern vorhanden, die
 * beste Lösung. Er ist in der Lage die Lösung für das komplexeste bekannte
 * RushHour Spielbrett (24.000 Brettpositionen, 93 Züge bis zur Lüsung) in
 * deutlich unter einer Sekunde zu berechnen. Um dieses Ergebnis zu erreichen
 * wurde bei der Implementierung sehr auf Effizienz und weniger auf
 * Erweiterbarkeit und Verwenung bereits vorhandener Funktionalitüten geachtet.
 * 
 * @author Florian
 */
public class FastSolver implements ISolver {
	private Set<Integer> visitedStates;
	private Queue<FastSolverState> stateQueue;
	private Map<Integer, IGameBoardObject> gameBoardObjectMap;
	private byte solutionX, solutionY;

	/**
	 * Erstellt ein neue Instanz des Fastsolvers für das übergebene Spielbrett
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
	 * Bestimmt über Breitensuche die zum Gewinnen nötigen Züge
	 * 
	 * @return Liste der Züge zum Erreichen der Zielposition
	 */
	public List<IMove> solveGameBoard() {
		while (!stateQueue.isEmpty()) {
			FastSolverState currentState = stateQueue.remove();
			// Ziel erreicht?
			if (currentState.player != null
					&& currentState.player.x == solutionX
					&& currentState.player.y == solutionY) {
				return reconstructMoveList(currentState);
			}
			// Z�ge ermitteln
			computeAllPossibleMoves(currentState);
		}
		return null;
	}

	/**
	 * Bestimmt alle in einem gegebenen SolverState durchführbaren Züge und
	 * leitet diese an addStateToQueue weiter
	 * 
	 * @param state
	 *            SolverState der geprüft wird
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
	 * Erstellt den neuen SolverState auf der Basis des übergeben Status und
	 * Zuges und fügt diesen, sofern er neu ist in die Queue der zu
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
	 * Rekonstuiert von dem übergebenen Status aus eine Liste der bisher
	 * getätigten Züge in Ausführungsreihenfolge
	 * 
	 * @param currentState
	 *            Solver-Status
	 * @return Liste der Züge
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
	 * Erstellt aus dem übergebenen GameBoardObject ein SolverCar mit den
	 * gleichen Eigenschaften
	 * 
	 * @param gameBoardObject
	 *            Spielbrettobjekt
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
			if (gameBoardObject.getOrientation() == Orientation.EAST) {
				this.solutionX--;
			} 
			if (gameBoardObject.getOrientation() == Orientation.SOUTH) {
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
