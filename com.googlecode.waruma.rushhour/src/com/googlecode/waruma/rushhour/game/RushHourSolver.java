package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.ISolver;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.rits.cloning.Cloner;

public class RushHourSolver implements ISolver, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3355079380109207520L;
	private Cloner objectCloner = new Cloner();
	private Set<RushHourSolverState> visitedBoardPositions;
	private Queue<RushHourSolverState> stateQueue;

	public RushHourSolver(GameBoard gameBoard) {
		// Initialisieren der Collections
		visitedBoardPositions = new HashSet<RushHourSolverState>();
		stateQueue = new LinkedList<RushHourSolverState>();
		// GameBoard klonen und daraus den initialen SolverState erstellen
		GameBoard clonedGameBoard = objectCloner.deepClone(gameBoard);
		RushHourSolverState initialSolverState = new RushHourSolverState(
				clonedGameBoard);
		// Solverstate der StateQueue und den besuchten Positionen hinzufügen
		visitedBoardPositions.add(initialSolverState);
		stateQueue.add(initialSolverState);
	}
	
	public static void debugState(RushHourSolverState state){
		System.out.println(state.hashCode());
		System.out.println(state.isSolutionState());
		printCollisionMap(state.getCollisionMap());
	}

	public static void printCollisionMap(Boolean[][] collisionMap){
		for (int i = 0; i < collisionMap.length; i++) {
			for (int j = 0; j < collisionMap[i].length; j++) {
				System.out.print(collisionMap[j][i] == true ? " " : "X");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	@Override
	public List<IMove> solveGameBoard() {
		while (!stateQueue.isEmpty()) {
			RushHourSolverState currentState = stateQueue.poll();
			debugState(currentState);
			// Gewonnen?
			if (currentState.isSolutionState()) {
				return reconstructMoveList(currentState);
			}
			List<IMove> possibleMoves = currentState.GetValidMoves();
			for (IMove move : possibleMoves) {
				RushHourSolverState newState = new RushHourSolverState(
						currentState, 
						objectCloner.deepClone(currentState.getCollisionMap()),
						objectCloner.deepClone(currentState.getMoveableGameBoardObjects()),
						objectCloner.deepClone(currentState.getPlayers()));

				if(newState.doMove(move)) {
					if(!visitedBoardPositions.contains(newState)){
						stateQueue.add(newState);
						visitedBoardPositions.add(newState);
					}
				}
			}

		}
		// Breitensuche ohne Ergebnis
		return null;
	}

	// BFS(start_node, goal_node) {
	// for(all nodes i) visited[i] = false; // anfangs sind keine Knoten besucht
	// queue.push(start_node); // mit Start-Knoten beginnen
	// visited[start_node] = true;
	// while(! queue.empty() ) { // solange queue nicht leer ist
	// node = queue.pop(); // erstes Element von der queue nehmen
	// if(node == goal_node) {
	// return true; // testen, ob Ziel-Knoten gefunden
	// }
	// foreach(neighbors of node) { // alle Nachfolge-Knoten, ...
	// if(visited[neighbor] == false) { // ... die noch nicht besucht wurden ...
	// queue.push(neighbor); // ... zur queue hinzufügen...
	// visited[neighbor] = true; // ... und als bereits gesehen markieren
	// }
	// }
	// }
	// return false; // Knoten kann nicht erreicht werden
	// }

	private List<IMove> reconstructMoveList(RushHourSolverState currentState) {
		// TODO Auto-generated method stub
		return null;
	}

	// DEBUG initialization
	public static void main(String[] args) throws IllegalBoardPositionException {
		ICollisionDetector collisionDetector = new RushHourCollisionDetector(6,6);
		Boolean[][] collisionMapCar = new Boolean[2][0];
		Boolean[][] collisionMapTruck = new Boolean[3][0];

		GameBoard gameBoard = new GameBoard(collisionDetector);

		PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(0, 2),
				Orientation.EAST, collisionDetector);
		StandardCar truck1 = new StandardCar(collisionMapTruck,
				new Point(2, 1), Orientation.NORTH);
		StandardCar truck2 = new StandardCar(collisionMapTruck,
				new Point(4, 1), Orientation.SOUTH);
		StandardCar car1 = new StandardCar(collisionMapCar, new Point(2, 0),
				Orientation.WEST);
		StandardCar car2 = new StandardCar(collisionMapCar, new Point(2, 4),
				Orientation.EAST);

		gameBoard.addGameBoardObject(playerCar);
		gameBoard.addGameBoardObject(truck1);
		gameBoard.addGameBoardObject(truck2);
		gameBoard.addGameBoardObject(car1);
		gameBoard.addGameBoardObject(car2);

		RushHourSolver solver = new RushHourSolver(gameBoard);
		solver.solveGameBoard();
	}
}