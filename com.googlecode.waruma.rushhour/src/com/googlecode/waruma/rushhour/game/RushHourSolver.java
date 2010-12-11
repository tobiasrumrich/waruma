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
	private class Timer {
		private long elapsed = 0;
		private long start;
		
		private void start(){
			start = System.currentTimeMillis();
		}
		
		private void stop(){
			elapsed += System.currentTimeMillis() - start;
		}
		
	}
	
	private static final long serialVersionUID = -3355079380109207520L;
	private Cloner objectCloner = new Cloner();
	private Set<RushHourSolverState> visitedBoardPositions;
	private Queue<RushHourSolverState> stateQueue;
	private long startTime;
	private long endTime;

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
		Timer timer = new Timer();
		
		int checkedPositions = 0;
		while (!stateQueue.isEmpty()) {
			checkedPositions++;
			RushHourSolverState currentState = stateQueue.poll();
			// Gewonnen?
			if (currentState.isSolutionState()) {
				System.out.println(checkedPositions + " Steps - " + timer.elapsed + " ms");
				return reconstructMoveList(currentState);
			}
			List<IMove> possibleMoves = currentState.GetValidMoves();
			for (IMove move : possibleMoves) {
				
				RushHourSolverState newState = new RushHourSolverState(
						currentState, 
						objectCloner.deepClone(currentState.getCollisionMap()),
						objectCloner.deepClone(currentState.getMoveableGameBoardObjects()),
						objectCloner.deepClone(currentState.getPlayers()));
				timer.start();
				if(newState.doMove(move)) {
					timer.stop();
					if(visitedBoardPositions.add(newState)){
						stateQueue.add(newState);
					}
				}
				
			}

		}
		// Breitensuche ohne Ergebnis
		
		return null;
	}

	private List<IMove> reconstructMoveList(RushHourSolverState currentState) {
		// TODO Auto-generated method stub
		return null;
	}

	// DEBUG initialization
	public static void main(String[] args) throws IllegalBoardPositionException {
		ICollisionDetector collisionDetector = new RushHourCollisionDetector(6,6);
		Boolean[][] collisionMapCar = {{true},{true}};
		Boolean[][] collisionMapTruck = {{true},{true},{true}};

		GameBoard gameBoard = new GameBoard(collisionDetector);
		
		// Schwerstes bekanntes Spielbrett
//		// 14898 Züge - 187,853 Sekunden
//		
//		PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(2, 2), Orientation.EAST, collisionDetector);
//		playerCar.setDestination(new Point(5,2));
//		
//		StandardCar car1 = new StandardCar(collisionMapCar, new Point(3, 0), Orientation.NORTH);
//		StandardCar car2 = new StandardCar(collisionMapCar, new Point(0, 1), Orientation.NORTH);
//		StandardCar car3 = new StandardCar(collisionMapCar, new Point(1, 1), Orientation.WEST);
//		StandardCar car4 = new StandardCar(collisionMapCar, new Point(0, 3), Orientation.EAST);
//		StandardCar car5 = new StandardCar(collisionMapCar, new Point(2, 3), Orientation.SOUTH);
//		StandardCar car6 = new StandardCar(collisionMapCar, new Point(1, 4), Orientation.NORTH);
//		StandardCar car7 = new StandardCar(collisionMapCar, new Point(4, 4), Orientation.WEST);
//		StandardCar car8 = new StandardCar(collisionMapCar, new Point(2, 5), Orientation.WEST);
//		StandardCar car9 = new StandardCar(collisionMapCar, new Point(4, 5), Orientation.EAST);
//		StandardCar car10 = new StandardCar(collisionMapTruck, new Point(0, 0), Orientation.WEST);
//		StandardCar car11 = new StandardCar(collisionMapTruck, new Point(4, 0), Orientation.NORTH);
//		StandardCar car12 = new StandardCar(collisionMapTruck, new Point(5, 0), Orientation.NORTH);
//		
//		gameBoard.addGameBoardObject(playerCar);
//		gameBoard.addGameBoardObject(car1);
//		gameBoard.addGameBoardObject(car2);
//		gameBoard.addGameBoardObject(car3);
//		gameBoard.addGameBoardObject(car4);
//		gameBoard.addGameBoardObject(car5);
//		gameBoard.addGameBoardObject(car6);
//		gameBoard.addGameBoardObject(car7);
//		gameBoard.addGameBoardObject(car8);
//		gameBoard.addGameBoardObject(car9);
//		gameBoard.addGameBoardObject(car10);
//		gameBoard.addGameBoardObject(car11);
//		gameBoard.addGameBoardObject(car12);
		
		
		
//		Mittelschweres Spielbrett (574 Züge - 4 Sekunden)	
//		Überarbeitete Zugermittlung : 609 Züge - 3,3 Sekunden
		
		PlayerCar playerCar = new PlayerCar(collisionMapCar, new Point(0, 2),
				Orientation.EAST, collisionDetector);
		playerCar.setDestination(new Point(5,2));
		StandardCar car1 = new StandardCar(collisionMapCar, new Point(0, 0), Orientation.EAST);
		StandardCar car2 = new StandardCar(collisionMapCar, new Point(3, 0), Orientation.NORTH);
		StandardCar car3 = new StandardCar(collisionMapCar, new Point(4, 0), Orientation.WEST);
		StandardCar car4 = new StandardCar(collisionMapCar, new Point(0, 3), Orientation.SOUTH);
		StandardCar car5 = new StandardCar(collisionMapCar, new Point(1, 3), Orientation.EAST);
		StandardCar car6 = new StandardCar(collisionMapCar, new Point(3, 3), Orientation.WEST);
		StandardCar car7 = new StandardCar(collisionMapCar, new Point(0, 5), Orientation.EAST);
		StandardCar car8 = new StandardCar(collisionMapCar, new Point(3, 4), Orientation.SOUTH);
		StandardCar truck1 = new StandardCar(collisionMapTruck, new Point(2, 0), Orientation.NORTH);
		StandardCar truck2 = new StandardCar(collisionMapTruck, new Point(5, 3), Orientation.SOUTH);
		
		gameBoard.addGameBoardObject(playerCar);
		gameBoard.addGameBoardObject(car1);
		gameBoard.addGameBoardObject(car2);
		gameBoard.addGameBoardObject(car3);
		gameBoard.addGameBoardObject(car4);
		gameBoard.addGameBoardObject(car5);
		gameBoard.addGameBoardObject(car6);
		gameBoard.addGameBoardObject(car7);
		gameBoard.addGameBoardObject(car8);
		gameBoard.addGameBoardObject(truck1);
		gameBoard.addGameBoardObject(truck2);
		
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

		RushHourSolver solver = new RushHourSolver(gameBoard);
		solver.solveGameBoard();
	}
}