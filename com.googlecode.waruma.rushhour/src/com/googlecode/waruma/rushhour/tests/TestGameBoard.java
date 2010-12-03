package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.util.Set;
import java.util.Stack;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;

public class TestGameBoard extends TestCase {

	private Boolean[][] collisionMapCar;
	private Boolean[][] collisionMapTrucks;
	private MockMoveable moveableCar;
	private MockMoveable moveableTruck;
	private GameBoard gameBoard;
	private MockCollisionDetector mockCollisionDetector;
	private MockMoveable moveableCar2;

	private class MockCollisionDetector implements ICollisionDetector {
		boolean calledCheckCollision = false;
		boolean calledAddGameBoardObject = false;
		IMove move;
		IGameBoardObject gameBoardObject;

		@Override
		public void move(IMove move) {
			this.move = move;
			calledCheckCollision = true;

		}

		@Override
		public void addGameBoardObject(IGameBoardObject gameBoardObject) {
			this.gameBoardObject = gameBoardObject;
			calledAddGameBoardObject = true;

		}
	}

	private class MockMoveable extends AbstractGameBoardObject implements
			IMoveable {

		private boolean calledMove = false;
		private int distance = 0;

		public MockMoveable(Boolean[][] collisionMap, Point position,
				Enum<Orientation> orientation) {
			super(collisionMap, position, orientation);
		}

		@Override
		public void move(int distance) throws IllegalMoveException {
			calledMove = true;
			this.distance = distance;
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		collisionMapCar = new Boolean[][] {{true, true}};
		collisionMapTrucks = new Boolean[][] {{true, true, true}};
		
		moveableCar = new MockMoveable(collisionMapCar, new Point(0,0), Orientation.EAST);
		moveableCar2 = new MockMoveable(collisionMapCar, new Point(1000,500), Orientation.EAST);
		moveableTruck = new MockMoveable(collisionMapTrucks, new Point(-500,-1000), Orientation.EAST);

		mockCollisionDetector = new MockCollisionDetector();
		
		gameBoard = new GameBoard(mockCollisionDetector);
	}
	
	public void testGameBoardInitialization() {
		assertTrue(gameBoard.getGameBoardObjects() instanceof Set<?>);
		assertTrue(gameBoard.getMoveHistory() instanceof Stack<?>);
	}

	public void testAddGameBoardObjectCollisionDelegation() throws IllegalBoardPositionException {
		// Auto1
		gameBoard.addGameBoardObject(moveableCar);
		assertTrue(mockCollisionDetector.calledAddGameBoardObject);
		assertEquals(moveableCar, mockCollisionDetector.gameBoardObject);
		
		// Auto2
		mockCollisionDetector.calledAddGameBoardObject = false;
		mockCollisionDetector.gameBoardObject = null;
		gameBoard.addGameBoardObject(moveableCar2);
		assertTrue(mockCollisionDetector.calledAddGameBoardObject);
		assertEquals(moveableCar2, mockCollisionDetector.gameBoardObject);
		
		// Auto3
		mockCollisionDetector.calledAddGameBoardObject = false;
		mockCollisionDetector.gameBoardObject = null;
		gameBoard.addGameBoardObject(moveableTruck);
		assertTrue(mockCollisionDetector.calledAddGameBoardObject);
		assertEquals(moveableTruck, mockCollisionDetector.gameBoardObject);
	}
	
	public void testAddGameBoardObjectCollectionTest() throws IllegalBoardPositionException {
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.addGameBoardObject(moveableCar2);
		gameBoard.addGameBoardObject(moveableTruck);
	
		Set<IGameBoardObject> gameBoardObjects = gameBoard.getGameBoardObjects();
		assertEquals(3, gameBoardObjects.size());
		
		assertTrue(gameBoardObjects.contains(moveableCar));
		assertTrue(gameBoardObjects.contains(moveableCar2));
		assertTrue(gameBoardObjects.contains(moveableTruck));
	}
	
	public void testMove() throws IllegalMoveException, IllegalBoardPositionException {
		IMove move = new Move(moveableCar, 1);
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.move(move);
		assertTrue(mockCollisionDetector.calledCheckCollision);
		assertEquals(mockCollisionDetector.move, move);	
	}

	public void testMoveHistory() throws IllegalMoveException {
		gameBoard.move(new Move(moveableCar, 2));
		gameBoard.move(new Move(moveableCar2, -3));
		IMove move = new Move(moveableTruck, 1);
		gameBoard.move(move);
		gameBoard.move(new Move(moveableCar, 1));
		gameBoard.move(new Move(moveableCar2, 2));
		
		Stack<IMove> history = gameBoard.getMoveHistory();		
		assertEquals(5, history.size());

		history.pop();
		history.pop();
		
		assertEquals(move, history.pop());
	}
}
