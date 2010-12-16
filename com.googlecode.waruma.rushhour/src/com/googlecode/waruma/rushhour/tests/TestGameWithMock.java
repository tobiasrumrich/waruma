package com.googlecode.waruma.rushhour.tests;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.GameState;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.ISolver;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.FastSolver;
import com.googlecode.waruma.rushhour.game.PlayerCar;
import com.googlecode.waruma.rushhour.game.RushHourBoardCreationController;
import com.googlecode.waruma.rushhour.game.RushHourGameplayControler;

/**
 * Testet den RushHourGameplayController
 * 
 * @author Rumrich
 * 
 */
public class TestGameWithMock extends TestCase {

	List<IGameBoardObject> carList = new ArrayList<IGameBoardObject>();
	RushHourBoardCreationController boardCreationController;
	GameBoard gameBoard;
	IGameBoardObject car1, car2, car3, car4, car5, car6, car7, truck1, truck2,
			playerCar;
	private RushHourGameplayControler gamePlayController;
	private GameState gameState;
	private RushHourWindowMock rushHourWindowMock;
	private ArrayList<IGameBoardObject> listOfCars;

	private class RushHourWindowMock implements IGameWonObserver {
		public Boolean calledUpdateGameWon = false;

		@Override
		public void updateGameWon() {
			calledUpdateGameWon = true;
		}

	}

	protected void setUp() throws Exception {
		super.setUp();
		boardCreationController = new RushHourBoardCreationController(6, 6);

		car1 = boardCreationController.createCar(new Point(0, 0),
				Orientation.SOUTH, false);
		car2 = boardCreationController.createCar(new Point(1, 1),
				Orientation.EAST, false);
		car3 = boardCreationController.createCar(new Point(3, 1),
				Orientation.EAST, false);

		playerCar = boardCreationController.createPlayerCar(new Point(0, 2),
				new Point(5, 2), Orientation.EAST);

		car4 = boardCreationController.createCar(new Point(0, 3),
				Orientation.EAST, false);
		car5 = boardCreationController.createCar(new Point(2, 2),
				Orientation.SOUTH, false);
		car6 = boardCreationController.createCar(new Point(3, 2),
				Orientation.SOUTH, false);
		car7 = boardCreationController.createCar(new Point(1, 4),
				Orientation.SOUTH, false);

		truck1 = boardCreationController.createTruck(new Point(2, 5),
				Orientation.EAST, false);
		truck2 = boardCreationController.createTruck(new Point(4, 2),
				Orientation.SOUTH, false);

		listOfCars = new ArrayList<IGameBoardObject>();
		listOfCars.add(car1);
		listOfCars.add(car2);
		listOfCars.add(car3);
		listOfCars.add(car4);
		listOfCars.add(car5);
		listOfCars.add(car6);
		listOfCars.add(car7);
		listOfCars.add(truck1);
		listOfCars.add(truck2);
		
		Object currentState = boardCreationController.getCurrentState();

		if (currentState instanceof GameBoard) {
			gameBoard = (GameBoard) currentState;
		} else {
			fail("Cast failed - Could not initialize GameBoard.");
		}

		gamePlayController = new RushHourGameplayControler(gameBoard);
		gameState = new GameState();
		gameState.addPlayer((IPlayer) playerCar);

		rushHourWindowMock = new RushHourWindowMock();
		gamePlayController.registerGameWon(rushHourWindowMock);
		((PlayerCar) playerCar).registerReachedDestination(gameState);

	}

	public void testSolver() {
		ISolver solver = new FastSolver(gameBoard);
		List<IMove> solverMoves = solver.solveGameBoard();

		for (IMove iMove : solverMoves) {
			try {
				gamePlayController.moveCar(
						(IGameBoardObject) iMove.getMoveable(),
						iMove.getDistance());
			} catch (IllegalMoveException e) {
				fail("solver delivered illegal moves!");
			}
		}

		assertTrue(rushHourWindowMock.calledUpdateGameWon);
	}

	public void testGetCars() {
		Collection<IGameBoardObject> cars = gamePlayController.getCars();
		for (IGameBoardObject iCar : cars) {
			if (!listOfCars.contains(iCar)) {
				fail();
			}
		}
	}

	public void testGameStore() {
		GameBoard currentState = (GameBoard) gamePlayController
				.getCurrentState();
		try {
			gamePlayController.saveGame("temp_junit_serialized_game.tmp");
		} catch (IOException e) {
			fail();
		}

		RushHourBoardCreationController anotherBoardCreationController = new RushHourBoardCreationController(
				6, 6);
		GameBoard emptyBoard = (GameBoard) boardCreationController
				.getCurrentState();
		RushHourGameplayControler anotherGamePlayControler = new RushHourGameplayControler(
				emptyBoard);

		try {
			anotherGamePlayControler.loadGame("temp_junit_serialized_game.tmp");
		} catch (IOException e) {
			fail();
		}
		assertEquals(currentState,
				(GameBoard) anotherGamePlayControler.getCurrentState());

	}

}
