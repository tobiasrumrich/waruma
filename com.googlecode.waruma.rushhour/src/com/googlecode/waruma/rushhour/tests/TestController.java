package com.googlecode.waruma.rushhour.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.RushHourBoardCreationController;
import com.googlecode.waruma.rushhour.game.RushHourGameplayControler;
import com.googlecode.waruma.rushhour.game.SteeringLock;

/**
 * Testet den RushHourGameplayController
 * 
 * @author Rumrich
 * 
 */
public class TestController extends TestCase {

	private class RushHourWindowMock implements IGameWonObserver {
		public Boolean calledUpdateGameWon = false;

		@Override
		public void updateGameWon() {
			calledUpdateGameWon = true;
		}

	}

	private List<IGameBoardObject> carList = new ArrayList<IGameBoardObject>();
	private RushHourBoardCreationController boardCreationController;
	private GameBoard gameBoard;
	private IGameBoardObject car1, car2, car3, car4, car5, car6, car7, truck1,
			truck2, playerCar;
	private RushHourGameplayControler gamePlayController;
	private RushHourWindowMock rushHourWindowMock;

	private ArrayList<IGameBoardObject> listOfCars;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		boardCreationController = new RushHourBoardCreationController();

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
		listOfCars.add(playerCar);

		Object currentState = boardCreationController.getCurrentState();

		if (currentState instanceof GameBoard) {
			gameBoard = (GameBoard) currentState;
		} else {
			fail("Cast failed - Could not initialize GameBoard.");
		}

		gamePlayController = new RushHourGameplayControler(gameBoard);

		rushHourWindowMock = new RushHourWindowMock();
		gamePlayController.registerGameWon(rushHourWindowMock);
	}

	public void testChangeDestination() {
		boardCreationController.changeDestination(new Point(4, 2));
	}

	public void testElapsedGameTime() {
		assertEquals("00:00:00", gamePlayController.elapsedGameTime());
		try {
			gamePlayController.moveCar(car3, 1);
		} catch (IllegalMoveException e) {
			fail("IllegalMoveException");
			e.printStackTrace();
		}
		assertTrue(gamePlayController.elapsedGameTime() != "00:00:00");
	}

	public void testGameBoardObjects() {
		Collection<IGameBoardObject> boardObjects = boardCreationController
				.getGameBoardObjects();
		assertEquals(10, boardObjects.size());

		boardCreationController.removeObjectFromBoard(car4);

		boardObjects = boardCreationController.getGameBoardObjects();
		assertEquals(9, boardObjects.size());
	}

	public void testGameStore() {
		GameBoard currentState = (GameBoard) gamePlayController
				.getCurrentState();
		try {
			gamePlayController.saveGame("temp_junit_serialized_game.tmp");
		} catch (IOException e) {
			e.printStackTrace();
			fail();

		}

		new RushHourBoardCreationController(6, 6);
		GameBoard emptyBoard = (GameBoard) boardCreationController
				.getCurrentState();
		RushHourGameplayControler anotherGamePlayControler = new RushHourGameplayControler(
				emptyBoard);

		try {
			anotherGamePlayControler.loadGame("temp_junit_serialized_game.tmp");
		} catch (IOException e) {
			fail();
		}
		assertEquals(currentState, anotherGamePlayControler.getCurrentState());

	}

	public void testGetCars() {
		gamePlayController.getCars();
		for (IGameBoardObject boardObject : listOfCars) {
			if (!gamePlayController.getCars().contains(boardObject)) {
				fail();
			}
		}
	}

	public void testGetMoveCountAndLatestMove() {
		assertEquals(Integer.valueOf(0), gamePlayController.getMoveCount());
		try {
			gamePlayController.moveCar(car3, 1);
		} catch (IllegalMoveException e) {
			fail("IllegalMoveException");
			e.printStackTrace();
		}
		assertEquals(Integer.valueOf(1), gamePlayController.getMoveCount());
		try {
			gamePlayController.moveCar(car3, -1);
		} catch (IllegalMoveException e) {
			fail("IllegalMoveException");
			e.printStackTrace();
		}
		assertEquals(Integer.valueOf(2), gamePlayController.getMoveCount());
		try {
			gamePlayController.moveCar(truck1, 1);
		} catch (IllegalMoveException e) {
			fail("IllegalMoveException");
			e.printStackTrace();
		}
		assertEquals(Integer.valueOf(3), gamePlayController.getMoveCount());
		gamePlayController.undoLatestMove();
		assertEquals(Integer.valueOf(2), gamePlayController.getMoveCount());
	}

	public void testGetMoveRange() {
		Rectangle moveRange = gamePlayController.getMoveRange(car3);
		assertEquals(3, moveRange.x);
		assertEquals(1, moveRange.y);
		assertEquals(2, moveRange.width);
	}

	public void testHasMoveInHistory() {
		assertFalse(gamePlayController.hasMoveInHistory());
		try {
			gamePlayController.moveCar(car3, 1);
		} catch (IllegalMoveException e) {
			fail("IllegalMoveException");
			e.printStackTrace();
		}
		assertTrue(gamePlayController.hasMoveInHistory());
	}

	public void testRepositionGameBoadObject() {
		try {
			boardCreationController.changeCarPosition(car1, new Point(0, 4));
		} catch (IllegalBoardPositionException e) {
			fail();
		}

		boolean thrown = false;
		try {
			boardCreationController.changeCarPosition(null, new Point(1, 2));
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testRotateGameBoadObject() {
		try {
			boardCreationController.changeRotation(car1, Orientation.EAST);
		} catch (IllegalBoardPositionException e) {
			fail();
		}
		boolean thrownEx = false;

		try {
			boardCreationController.changeRotation(car6, Orientation.WEST);
		} catch (IllegalBoardPositionException e) {
			thrownEx = true;
		}

		assertTrue(thrownEx);
	}

	public void testSaveAndRestore() {
		try {
			boardCreationController.saveGameBoard("bctest.ser");
		} catch (IOException e) {
			fail();
		}

		RushHourBoardCreationController newController = new RushHourBoardCreationController();

		try {
			newController.loadGameBoard("bctest.ser");
		} catch (IOException e) {
			fail();
		}

		assertEquals(10, newController.getGameBoardObjects().size());

		Object state = boardCreationController.getCurrentState();

		newController.loadState(state);

		boolean thrown = false;
		try {
			newController.loadState(null);
		} catch (IllegalArgumentException e) {
			thrown = true;
		}

		assertTrue(thrown);

		thrown = false;
		try {
			boardCreationController.loadGameBoard(null);
		} catch (IOException e) {
			thrown = true;
		}

		assertTrue(thrown);

	}

	public void testSolver() {
		List<IMove> solverMoves = gamePlayController.solveGame();

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

	public void testUnlockAllCars() throws IllegalBoardPositionException {
		SteeringLock steeringLockCar = (SteeringLock) boardCreationController
				.createCar(new Point(5, 0), Orientation.SOUTH, true);
		try {
			steeringLockCar.move(2);
		} catch (IllegalMoveException e) {
			fail();
		}

		boolean thrownEx = false;

		try {
			steeringLockCar.move(1);
		} catch (IllegalMoveException e) {
			thrownEx = true;
		}

		assertTrue(thrownEx);

		boardCreationController.unlockAllCars();

		try {
			steeringLockCar.move(1);
		} catch (IllegalMoveException e) {
			fail();
		}
	}

	public void testValidTile() {
		assertTrue(boardCreationController.validTile(new Point(2, 4)));
		assertFalse(boardCreationController.validTile(new Point(3, 2)));
	}

}
