package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.util.List;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.CollisionDetector;
import com.googlecode.waruma.rushhour.game.FastSolver;
import com.googlecode.waruma.rushhour.game.PlayerCar;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

public class TestFastSolver extends TestCase {

	private class MockNonMoveable implements IGameBoardObject {
		private Point position;
		private Boolean[][] collisionMap;
		private Orientation orientation;

		public MockNonMoveable(Boolean[][] collisionMap, Point position,
				Orientation orientation) {
			this.position = position;
			this.collisionMap = collisionMap;
			this.orientation = orientation;
		}

		@Override
		public Boolean[][] getCollisionMap() {
			return collisionMap;
		}

		@Override
		public Orientation getOrientation() {
			return orientation;
		}

		@Override
		public Point getPosition() {
			return position;
		}

		@Override
		public void setOrientation(Orientation orientation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setPosition(Point position) {
			this.position = position;
		}

	}

	private Boolean[][] collisionMapCar;
	private ICollisionDetector collisionDetector;
	private Boolean[][] collisionMapTruck;
	private GameBoard gameBoard;

	@Override
	protected void setUp() throws Exception {
		collisionDetector = new CollisionDetector(6, 6);
		collisionMapCar = new Boolean[][] { { true }, { true } };
		collisionMapTruck = new Boolean[][] { { true }, { true }, { true } };

		gameBoard = new GameBoard(collisionDetector);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHardGame() {
		PlayerCar playerCar =
				new PlayerCar(collisionMapCar, new Point(2, 2),
						Orientation.EAST, collisionDetector);
		playerCar.setDestination(new Point(5, 2));

		StandardCar car1 =
				new StandardCar(collisionMapCar, new Point(3, 0),
						Orientation.NORTH);
		StandardCar car2 =
				new StandardCar(collisionMapCar, new Point(0, 1),
						Orientation.NORTH);
		StandardCar car3 =
				new StandardCar(collisionMapCar, new Point(1, 1),
						Orientation.WEST);
		StandardCar car4 =
				new StandardCar(collisionMapCar, new Point(0, 3),
						Orientation.EAST);
		StandardCar car5 =
				new StandardCar(collisionMapCar, new Point(2, 3),
						Orientation.SOUTH);
		StandardCar car6 =
				new StandardCar(collisionMapCar, new Point(1, 4),
						Orientation.NORTH);
		StandardCar car7 =
				new StandardCar(collisionMapCar, new Point(4, 4),
						Orientation.WEST);
		StandardCar car8 =
				new StandardCar(collisionMapCar, new Point(2, 5),
						Orientation.WEST);
		StandardCar car9 =
				new StandardCar(collisionMapCar, new Point(4, 5),
						Orientation.EAST);
		StandardCar car10 =
				new StandardCar(collisionMapTruck, new Point(0, 0),
						Orientation.WEST);
		StandardCar car11 =
				new StandardCar(collisionMapTruck, new Point(4, 0),
						Orientation.NORTH);
		StandardCar car12 =
				new StandardCar(collisionMapTruck, new Point(5, 0),
						Orientation.NORTH);

		try {
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

		} catch (IllegalBoardPositionException e) {
			fail("Fehler im Test oder GameBoard");
		}

		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moveList = solver.solveGameBoard();

		for (IMove move : moveList) {
			try {
				gameBoard.move(move);
			} catch (IllegalMoveException e) {
				fail("Solver erzeugt ung�ltige Z�ge");
			}
		}

		assertTrue(playerCar.reachedDestination());
	}

	public void testMediumGame() {
		PlayerCar playerCar =
				new PlayerCar(collisionMapCar, new Point(0, 2),
						Orientation.EAST, collisionDetector);
		playerCar.setDestination(new Point(5, 2));
		StandardCar car1 =
				new StandardCar(collisionMapCar, new Point(0, 0),
						Orientation.WEST);
		StandardCar car2 =
				new StandardCar(collisionMapCar, new Point(3, 0),
						Orientation.NORTH);
		StandardCar car3 =
				new StandardCar(collisionMapCar, new Point(4, 0),
						Orientation.WEST);
		StandardCar car4 =
				new StandardCar(collisionMapCar, new Point(0, 3),
						Orientation.SOUTH);
		StandardCar car5 =
				new StandardCar(collisionMapCar, new Point(1, 3),
						Orientation.EAST);
		StandardCar car6 =
				new StandardCar(collisionMapCar, new Point(3, 3),
						Orientation.WEST);
		StandardCar car7 =
				new StandardCar(collisionMapCar, new Point(0, 5),
						Orientation.EAST);
		StandardCar car8 =
				new StandardCar(collisionMapCar, new Point(3, 4),
						Orientation.NORTH);
		StandardCar truck1 =
				new StandardCar(collisionMapTruck, new Point(2, 0),
						Orientation.SOUTH);
		StandardCar truck2 =
				new StandardCar(collisionMapTruck, new Point(5, 3),
						Orientation.SOUTH);

		try {
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

		} catch (IllegalBoardPositionException e) {
			fail("Fehler im Test oder GameBoard");
		}

		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moveList = solver.solveGameBoard();

		for (IMove move : moveList) {
			try {
				gameBoard.move(move);
			} catch (IllegalMoveException e) {
				fail("Solver erzeugt ung�ltige Z�ge");
			}
		}

		assertTrue(playerCar.reachedDestination());
	}

	public void testMediumGame2() {
		PlayerCar playerCar =
				new PlayerCar(collisionMapCar, new Point(3, 2),
						Orientation.EAST, collisionDetector);
		playerCar.setDestination(new Point(5, 2));
		StandardCar car1 =
				new StandardCar(collisionMapCar, new Point(4, 0),
						Orientation.NORTH);
		StandardCar car2 =
				new StandardCar(collisionMapCar, new Point(1, 3),
						Orientation.WEST);
		StandardCar car3 =
				new StandardCar(collisionMapCar, new Point(4, 3),
						Orientation.SOUTH);
		StandardCar car4 =
				new StandardCar(collisionMapCar, new Point(1, 4),
						Orientation.NORTH);
		StandardCar car5 =
				new StandardCar(collisionMapCar, new Point(4, 5),
						Orientation.EAST);
		StandardCar car10 =
				new StandardCar(collisionMapTruck, new Point(2, 0),
						Orientation.SOUTH);
		StandardCar car11 =
				new StandardCar(collisionMapTruck, new Point(5, 1),
						Orientation.NORTH);
		StandardCar car12 =
				new StandardCar(collisionMapTruck, new Point(3, 3),
						Orientation.NORTH);

		try {
			gameBoard.addGameBoardObject(playerCar);
			gameBoard.addGameBoardObject(car1);
			gameBoard.addGameBoardObject(car2);
			gameBoard.addGameBoardObject(car3);
			gameBoard.addGameBoardObject(car4);
			gameBoard.addGameBoardObject(car5);
			gameBoard.addGameBoardObject(car10);
			gameBoard.addGameBoardObject(car11);
			gameBoard.addGameBoardObject(car12);

		} catch (IllegalBoardPositionException e) {
			fail("Fehler im Test oder GameBoard");
		}

		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moveList = solver.solveGameBoard();

		for (IMove move : moveList) {
			try {
				gameBoard.move(move);
			} catch (IllegalMoveException e) {
				fail("Solver erzeugt ung�ltige Z�ge");
			}
		}

		assertTrue(playerCar.reachedDestination());
	}

	public void testNonMoveableObject() {
		PlayerCar playerCar =
				new PlayerCar(collisionMapCar, new Point(3, 0),
						Orientation.SOUTH, collisionDetector);
		playerCar.setDestination(new Point(3, 5));
		MockNonMoveable nonMoveable =
				new MockNonMoveable(collisionMapCar, new Point(2, 5),
						Orientation.EAST);
		try {
			gameBoard.addGameBoardObject(playerCar);
			gameBoard.addGameBoardObject(nonMoveable);
		} catch (IllegalBoardPositionException e) {
			fail("Fehler im Test oder GameBoard");
		}

		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moveList = solver.solveGameBoard();

		assertEquals(moveList, null);
	}

	public void testVeryEasyGame() {
		PlayerCar playerCar =
				new PlayerCar(collisionMapCar, new Point(0, 2),
						Orientation.EAST, collisionDetector);
		playerCar.setDestination(new Point(5, 2));
		StandardCar truck1 =
				new StandardCar(collisionMapTruck, new Point(2, 1),
						Orientation.NORTH);
		StandardCar truck2 =
				new StandardCar(collisionMapTruck, new Point(4, 1),
						Orientation.SOUTH);
		StandardCar car1 =
				new StandardCar(collisionMapCar, new Point(2, 0),
						Orientation.WEST);
		SteeringLock car2 =
				new SteeringLock(new StandardCar(collisionMapCar, new Point(2,
						4), Orientation.EAST));

		try {
			gameBoard.addGameBoardObject(playerCar);
			gameBoard.addGameBoardObject(truck1);
			gameBoard.addGameBoardObject(truck2);
			gameBoard.addGameBoardObject(car1);
			gameBoard.addGameBoardObject(car2);
		} catch (IllegalBoardPositionException e) {
			fail("Fehler im Test oder GameBoard");
		}

		FastSolver solver = new FastSolver(gameBoard);
		List<IMove> moveList = solver.solveGameBoard();

		for (IMove move : moveList) {
			try {
				gameBoard.move(move);
			} catch (IllegalMoveException e) {
				fail("Solver erzeugt ung�ltige Z�ge");
			}
		}

		assertTrue(playerCar.reachedDestination());
	}

}
