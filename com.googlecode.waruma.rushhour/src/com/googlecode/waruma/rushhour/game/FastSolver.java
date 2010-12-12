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
 * Der FastSolver bestimmt für ein beliebiges Spielbrett, sofern vorhanden, die
 * beste Lösung. Er ist in der Lage die Lösung für das komplexeste bekannte
 * RushHour Spielbrett (24.000 Brettpositionen, 93 Züge bis zur Lösung) in
 * deutlich unter einer Sekunde zu berechnen. Um dieses Ergebnis zu erreichen
 * wurde bei der Implementierung sehr auf Effizienz und weniger auf
 * Erweiterbarkeit und Verwenung bereits vorhandener Funktionalitäten geachtet.
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
			if (currentState.player.x == solutionX
					&& currentState.player.y == solutionY) {
				return reconstructMoveList(currentState);
			}
			// Züge ermitteln
			computeAllPossibleMoves(currentState);
		}
		System.out.println("Keine Lösung gefunden");
		return null;
	}

//	public static void debugState(FastSolverState state){
//		// Print collisionmap
//		byte[][] collisionMap = state.collisionMap;
//		System.out.println(" 012345");
//		for (int i = 0; i < collisionMap.length; i++) {
//			System.out.print(i);
//			for (int j = 0; j < collisionMap[i].length; j++) {
//				if(collisionMap[j][i] == 1)
//					System.out.print("-");
//				if(collisionMap[j][i] == 2)
//					System.out.print("I");
//				if(collisionMap[j][i] == 0)
//					System.out.print(" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		// Auto debuggen
//		FastSolverCar moved = null;
//		for (FastSolverCar car : state.cars) {
//			if(car.id == state.movedCar)
//				moved = car;
//		}
//		System.out.println("L:" + moved.length + " (" + moved.x + "," + moved.y + ") " + state.movedDistance);
//		System.out.println();
//		
//	}
	
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
	 * Erstellt aus dem übergebenen GameBoardObject ein SolverCar mit den gleichen Eigenschaften
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

	// DEBUG initialization
	public static void main(String[] args) throws IllegalBoardPositionException {
		ICollisionDetector collisionDetector = new RushHourCollisionDetector(6,
				6);
		Boolean[][] collisionMapCar = { { true }, { true } };
		Boolean[][] collisionMapTruck = { { true }, { true }, { true } };

		GameBoard gameBoard = new GameBoard(collisionDetector);

		// Schwerstes bekanntes Spielbrett
		// // 14898 Züge - 187,853 Sekunden
		//
		 PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(2, 2),
		 Orientation.EAST, collisionDetector);
		 playerCar.setDestination(new Point(5, 2));
		
		 StandardCar car1 = new StandardCar(collisionMapCar, new Point(3, 0),
		 Orientation.NORTH);
		 StandardCar car2 = new StandardCar(collisionMapCar, new Point(0, 1),
		 Orientation.NORTH);
		 StandardCar car3 = new StandardCar(collisionMapCar, new Point(1, 1),
		 Orientation.WEST);
		 StandardCar car4 = new StandardCar(collisionMapCar, new Point(0, 3),
		 Orientation.EAST);
		 StandardCar car5 = new StandardCar(collisionMapCar, new Point(2, 3),
		 Orientation.SOUTH);
		 StandardCar car6 = new StandardCar(collisionMapCar, new Point(1, 4),
		 Orientation.NORTH);
		 StandardCar car7 = new StandardCar(collisionMapCar, new Point(4, 4),
		 Orientation.WEST);
		 StandardCar car8 = new StandardCar(collisionMapCar, new Point(2, 5),
		 Orientation.WEST);
		 StandardCar car9 = new StandardCar(collisionMapCar, new Point(4, 5),
		 Orientation.EAST);
		 StandardCar car10 = new StandardCar(collisionMapTruck, new Point(0,
		 0),
		 Orientation.WEST);
		 StandardCar car11 = new StandardCar(collisionMapTruck, new Point(4,
		 0),
		 Orientation.NORTH);
		 StandardCar car12 = new StandardCar(collisionMapTruck, new Point(5,
		 0),
		 Orientation.NORTH);
		
		 gameBoard.addGameBoardObject(playerCar);
		 gameBoard.addGameBoardObject(car1);
		 gameBoard.addGameBoardObject(car2);
		 gameBoard.addGameBoardObject(car3);
		 gameBoard.addGameBoardObject(car4);
		 gameBoard.addGameBoardObject(car5);
		 gameBoard.addGameBoardObject(car6);
		 gameBoard.addGameBoardObject(car7);
		 gameBoard.addGameBoardObject(car8);
		 gameBoard.addGameBoardObject(car9);
		 gameBoard.addGameBoardObject(car10);
		 gameBoard.addGameBoardObject(car11);
		 gameBoard.addGameBoardObject(car12);

		// Mittelschweres Spielbrett (574 Züge - 4 Sekunden)
		// Überarbeitete Zugermittlung : 609 Züge - 3,3 Sekunden
		//
//		 PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(0, 2),
//		 Orientation.EAST, collisionDetector);
//		 playerCar.setDestination(new Point(5, 2));
//		 StandardCar car1 = new StandardCar(collisionMapCar, new Point(0, 0),
//		 Orientation.EAST);
//		 StandardCar car2 = new StandardCar(collisionMapCar, new Point(3, 0),
//		 Orientation.NORTH);
//		 StandardCar car3 = new StandardCar(collisionMapCar, new Point(4, 0),
//		 Orientation.WEST);
//		 StandardCar car4 = new StandardCar(collisionMapCar, new Point(0, 3),
//		 Orientation.SOUTH);
//		 StandardCar car5 = new StandardCar(collisionMapCar, new Point(1, 3),
//		 Orientation.EAST);
//		 StandardCar car6 = new StandardCar(collisionMapCar, new Point(3, 3),
//		 Orientation.WEST);
//		 StandardCar car7 = new StandardCar(collisionMapCar, new Point(0, 5),
//		 Orientation.EAST);
//		 StandardCar car8 = new StandardCar(collisionMapCar, new Point(3, 4),
//		 Orientation.SOUTH);
//		 StandardCar truck1 = new StandardCar(collisionMapTruck,
//		 new Point(2, 0), Orientation.NORTH);
//		 StandardCar truck2 = new StandardCar(collisionMapTruck,
//		 new Point(5, 3), Orientation.SOUTH);
//		
//		 gameBoard.addGameBoardObject(playerCar);
//		 gameBoard.addGameBoardObject(car1);
//		 gameBoard.addGameBoardObject(car2);
//		 gameBoard.addGameBoardObject(car3);
//		 gameBoard.addGameBoardObject(car4);
//		 gameBoard.addGameBoardObject(car5);
//		 gameBoard.addGameBoardObject(car6);
//		 gameBoard.addGameBoardObject(car7);
//		 gameBoard.addGameBoardObject(car8);
//		 gameBoard.addGameBoardObject(truck1);
//		 gameBoard.addGameBoardObject(truck2);
//
//		PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(0, 2),
//				Orientation.EAST, collisionDetector);
//		playerCar.setDestination(new Point(5, 2));
//		StandardCar truck1 = new StandardCar(collisionMapTruck,
//				new Point(2, 1), Orientation.NORTH);
//		StandardCar truck2 = new StandardCar(collisionMapTruck,
//				new Point(4, 1), Orientation.SOUTH);
//		StandardCar car1 = new StandardCar(collisionMapCar, new Point(2, 0),
//				Orientation.WEST);
//		StandardCar car2 = new StandardCar(collisionMapCar, new Point(2, 4),
//				Orientation.EAST);
//
//		gameBoard.addGameBoardObject(playerCar);
//		gameBoard.addGameBoardObject(truck1);
//		gameBoard.addGameBoardObject(truck2);
//		gameBoard.addGameBoardObject(car1);
//		gameBoard.addGameBoardObject(car2);
//
		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moves = solver.solveGameBoard();
//		for (IMove move : moves) {
//			System.out.println(move);
//		}
	}
}
