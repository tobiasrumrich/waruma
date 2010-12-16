package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;
import java.util.Collection;
import java.util.Stack;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Rectangle;

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

	private class MockCollisionDetector implements ICollisionDetector {
		boolean calledDoMove = false;
		boolean calledAddGameBoardObject = false;
		boolean calledCheckMove = false;

		IMove move;
		IGameBoardObject gameBoardObject;

		@Override
		public void addGameBoardObject(IGameBoardObject gameBoardObject) {
			this.gameBoardObject = gameBoardObject;
			calledAddGameBoardObject = true;

		}

		@Override
		public void checkMove(IMove move) throws IllegalMoveException {
			calledCheckMove = true;
			if (move.getDistance() == -42) {
				throw new IllegalMoveException();
			}
		}

		@Override
		public void doMove(IMove move) throws IllegalMoveException {
			this.move = move;
			calledDoMove = true;

		}

		@Override
		public Rectangle getMoveRange(IGameBoardObject gameBoardObject) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hitPoint(IGameBoardObject gameBoardObject, Point point) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void moveGameBoardObjectToPosition(
				IGameBoardObject gameBoardObject, Point position)
				throws IllegalBoardPositionException {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeGameBoardObject(IGameBoardObject gameBoardObject) {
			// TODO Auto-generated method stub

		}

		@Override
		public void rotateGameBoardObject(IGameBoardObject gameBoardObject,
				Orientation orientation) throws IllegalBoardPositionException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean validTile(Point point) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class MockMoveable extends AbstractGameBoardObject implements
			IMoveable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7058927463289079845L;

		public MockMoveable(Boolean[][] collisionMap, Point position,
				Orientation orientation) {
			super(collisionMap, position, orientation);
		}

		@Override
		public void checkMove(int distance) throws IllegalMoveException {
			// TODO Auto-generated method stub

		}

		@Override
		public void move(int distance) throws IllegalMoveException {
		}
	}

	private Boolean[][] collisionMapCar;
	private Boolean[][] collisionMapTrucks;
	private MockMoveable moveableCar;
	private MockMoveable moveableTruck;
	private GameBoard gameBoard;
	private MockCollisionDetector mockCollisionDetector;
	private MockMoveable moveableCar2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		collisionMapCar = new Boolean[][] { { true, true } };
		collisionMapTrucks = new Boolean[][] { { true, true, true } };

		moveableCar =
				new MockMoveable(collisionMapCar, new Point(0, 0),
						Orientation.EAST);
		moveableCar2 =
				new MockMoveable(collisionMapCar, new Point(1000, 500),
						Orientation.EAST);
		moveableTruck =
				new MockMoveable(collisionMapTrucks, new Point(-500, -1000),
						Orientation.EAST);

		mockCollisionDetector = new MockCollisionDetector();

		gameBoard = new GameBoard(mockCollisionDetector);
	}

	public void testAddGameBoardObjectCollectionTest()
			throws IllegalBoardPositionException {
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.addGameBoardObject(moveableCar2);
		gameBoard.addGameBoardObject(moveableTruck);

		Collection<IGameBoardObject> gameBoardObjects =
				gameBoard.getGameBoardObjects();
		assertEquals(3, gameBoardObjects.size());

		assertTrue(gameBoardObjects.contains(moveableCar));
		assertTrue(gameBoardObjects.contains(moveableCar2));
		assertTrue(gameBoardObjects.contains(moveableTruck));
	}

	public void testAddGameBoardObjectCollisionDelegation()
			throws IllegalBoardPositionException {
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

	public void testMoveHistory() throws IllegalMoveException,
			IllegalBoardPositionException {
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.addGameBoardObject(moveableCar2);
		gameBoard.addGameBoardObject(moveableTruck);

		gameBoard.move(new Move(moveableCar, 2));
		gameBoard.move(new Move(moveableCar2, -3));
		IMove move = new Move(moveableTruck, 1);
		gameBoard.move(move);
		gameBoard.move(new Move(moveableCar, 1));
		gameBoard.move(new Move(moveableCar2, 2));
		try {
			gameBoard.move(new Move(moveableCar, -42));
			fail();
		} catch (IllegalMoveException e) {
		}

		Stack<IMove> history = gameBoard.getMoveHistory();
		assertEquals(5, history.size());

		history.pop();
		history.pop();

		assertEquals(move, history.pop());
	}

	public void testMoveNonExistingObject() {
		boolean thrownException = false;

		try {
			gameBoard.move(new Move(moveableCar, 2));
		} catch (IllegalMoveException e) {
			thrownException = true;
		}

		assertTrue(thrownException);
	}

	public void testMoveRange() throws IllegalBoardPositionException {
		gameBoard.addGameBoardObject(moveableCar);
		Rectangle moveRange = gameBoard.getMoveRange(moveableCar);
		assertNull(moveRange);
	}

	public void testMoveWithIllegalMove() throws IllegalMoveException,
			IllegalBoardPositionException {
		IMove move = new Move(moveableCar, -42);
		gameBoard.addGameBoardObject(moveableCar);

		Boolean exceptionThrown = false;
		try {
			gameBoard.move(move);
		} catch (IllegalMoveException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		assertTrue(mockCollisionDetector.calledCheckMove);
		assertFalse(mockCollisionDetector.calledDoMove);
		assertEquals(mockCollisionDetector.move, null);
	}

	public void testMoveWithLegalMove() throws IllegalMoveException,
			IllegalBoardPositionException {
		IMove move = new Move(moveableCar, 1);
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.move(move);
		assertTrue(mockCollisionDetector.calledCheckMove);
		assertTrue(mockCollisionDetector.calledDoMove);
		assertEquals(mockCollisionDetector.move, move);
	}

	public void testUndoLatestMove() throws IllegalBoardPositionException,
			IllegalMoveException {
		gameBoard.addGameBoardObject(moveableCar);
		gameBoard.addGameBoardObject(moveableCar2);
		gameBoard.addGameBoardObject(moveableTruck);

		IGameBoardObject boardObject = gameBoard.undoLatestMove();
		assertNull(boardObject);

		gameBoard.move(new Move(moveableCar, 2));

		boardObject = gameBoard.undoLatestMove();
		assertEquals(moveableCar, boardObject);

	}
}
