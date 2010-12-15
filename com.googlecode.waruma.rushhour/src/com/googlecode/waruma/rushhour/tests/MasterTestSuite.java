package com.googlecode.waruma.rushhour.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.*;
import com.googlecode.waruma.rushhour.game.*;


public class MasterTestSuite extends TestCase {

	List<IGameBoardObject> carList = new ArrayList<IGameBoardObject>();
	RushHourBoardCreationController boardCreationController;
	GameBoard gameBoard;
	IGameBoardObject car1, car2, car3, car4, car5, car6, car7, truck1, truck2, playerCar;
	private RushHourGameplayControler gamePlayController;
	private GameState gameState;
	
	protected void setUp() throws Exception {
		super.setUp();
		boardCreationController = new RushHourBoardCreationController(6, 6);
		
		
		
		
		car1 = boardCreationController.createCar(new Point(0,0), Orientation.SOUTH,false);
		car2 = boardCreationController.createCar(new Point(1,1), Orientation.EAST,false);
		car3 = boardCreationController.createCar(new Point(3,1), Orientation.EAST,false);
		
		playerCar = boardCreationController.createPlayerCar(new Point(0,2), new Point(5,2), Orientation.EAST);
		
		car4 = boardCreationController.createCar(new Point(0,3), Orientation.EAST,false);
		car5 = boardCreationController.createCar(new Point(2,2), Orientation.SOUTH,false);
		car6 = boardCreationController.createCar(new Point(3,2), Orientation.SOUTH,false);
		car7 = boardCreationController.createCar(new Point(1,4), Orientation.SOUTH,false);
		
		truck1 = boardCreationController.createTruck(new Point(2,5), Orientation.EAST, false);
		truck2 = boardCreationController.createTruck(new Point(4,2), Orientation.SOUTH, false);
		

		Object currentState = boardCreationController.getCurrentState();
		
		if (currentState instanceof GameBoard) {
			gameBoard = (GameBoard) currentState;
		}
		else {
			fail("Cast failed - Could not initialize GameBoard.");
		}
		
		gamePlayController = new RushHourGameplayControler(gameBoard);
		gamePlayController
		gameState = new GameState();
		gameState.addPlayer((IPlayer) playerCar);
		
		gameState.registerGameWon((IGameWonObserver) gamePlayController);
		
		//gameState.registerGameWon((IGameWonObserver) gamePlayController);
		
		//PlayerCar beim GameState registrieren
		((PlayerCar) playerCar).registerReachedDestination(gameState);
		
	}
	
	public void testSolver() {
		ISolver solver = new FastSolver(gameBoard);
		List<IMove> solverMoves = solver.solveGameBoard();
		
		for (IMove iMove : solverMoves) {
			try {
				//iMove.getMoveable().move(iMove.getDistance());
				//gameBoard.move(iMove);
				gamePlayController.moveCar((IGameBoardObject) iMove.getMoveable(),iMove.getDistance());
			} catch (IllegalMoveException e) {
				fail("solver delivered illegal moves!!");
				e.printStackTrace();
			}
		}
	}	


}
