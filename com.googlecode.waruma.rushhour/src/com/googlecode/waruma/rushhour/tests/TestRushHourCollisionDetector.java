package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.RushHourCollisionDetector;

public class TestRushHourCollisionDetector extends TestCase {

	private class MockMoveable extends AbstractGameBoardObject implements
			IMoveable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8660743442145058342L;
		private boolean calledMove = false;
		private int distance = 0;

		public MockMoveable(Boolean[][] collisionMap, Point position,
				Orientation orientation) {
			super(collisionMap, position, orientation);
		}

		@Override
		public void move(int distance) throws IllegalMoveException {
			calledMove = true;
			this.distance = distance;
		}

		@Override
		public void checkMove(int distance) throws IllegalMoveException {			
		}
	}

	private RushHourCollisionDetector rushHourCollisionDetector;
	private Boolean[][] collisionMapCar;
	private Boolean[][] collisionMapTruck;
	private MockMoveable moveable1;
	private MockMoveable moveable2;

	private MockMoveable moveable3;

	private boolean addGameBoardObjectThrowsIllegalBoardPosEx(
			RushHourCollisionDetector rushHourCollisionDetector,
			IGameBoardObject moveable) {
		try {
			rushHourCollisionDetector.addGameBoardObject(moveable);
		} catch (IllegalBoardPositionException e) {
			return true;
		}
		return false;
	}

	private boolean moveThrowsIllegalMoveException(
			RushHourCollisionDetector rushHourCollisionDetector,
			IMoveable moveable, int distance) {
		IMove move = new Move(moveable, distance);
		try {
			rushHourCollisionDetector.checkMove(move);
		} catch (IllegalMoveException e) {
			return true;
		}
		return false;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rushHourCollisionDetector = new RushHourCollisionDetector(6);

		collisionMapCar = new Boolean[2][0];
		collisionMapTruck = new Boolean[3][0];

		moveable1 = new MockMoveable(collisionMapCar, new Point(0, 0),
				Orientation.EAST);
		moveable2 = new MockMoveable(collisionMapCar, new Point(0, 0),
				Orientation.EAST);
		moveable3 = new MockMoveable(collisionMapTruck, new Point(0, 0),
				Orientation.NORTH);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddGameBoardObject() {
		try {
			moveable1.setPosition(new Point(4, 5));
			rushHourCollisionDetector.addGameBoardObject(moveable1);

			moveable2.setPosition(new Point(2, 0));
			rushHourCollisionDetector.addGameBoardObject(moveable2);

			moveable3.setPosition(new Point(0, 3));
			rushHourCollisionDetector.addGameBoardObject(moveable3);
		} catch (Exception e) {
			fail();
		}
	}

	public void testAddGameBoardObjectCollision()
			throws IllegalBoardPositionException {
		moveable1.setPosition(new Point(2, 2));
		rushHourCollisionDetector.addGameBoardObject(moveable1);

		moveable3.setPosition(new Point(3, 0));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable3));

		moveable1.setPosition(new Point(3, 2));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));

		moveable1.setPosition(new Point(2, 2));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
	}

	public void testAddGameBoardObjectOutOfBounds() {
		moveable1.setPosition(new Point(-1, 0));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(0, -1));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(5, 5));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(-1, 5));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(5, -1));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(1000, 1000));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(-1000, -1000));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(6, 6));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
		moveable1.setPosition(new Point(-2, 0));
		assertTrue(addGameBoardObjectThrowsIllegalBoardPosEx(
				rushHourCollisionDetector, moveable1));
	}

	public void testMoveCollision() throws IllegalBoardPositionException {
		// 1 1 2 2 X X //
		moveable1.setPosition(new Point(0, 2));
		rushHourCollisionDetector.addGameBoardObject(moveable1);
		moveable2.setPosition(new Point(2, 2));
		rushHourCollisionDetector.addGameBoardObject(moveable2);
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 1));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 2));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 3));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 4));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable2, -1));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, -2));
	}

	public void testMoveOutsideOfBounds() throws IllegalBoardPositionException {
		moveable1.setPosition(new Point(2, 2));
		rushHourCollisionDetector.addGameBoardObject(moveable1);
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, -3));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, -4));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, -1000));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 3));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 4));
		assertTrue(moveThrowsIllegalMoveException(rushHourCollisionDetector,
				moveable1, 1000));
	}

	public void testMoveValidInputs() throws IllegalBoardPositionException,
			IllegalMoveException {
		moveable1.setPosition(new Point(2, 5));
		rushHourCollisionDetector.addGameBoardObject(moveable1);
		
		Move move = new Move(moveable1, -2);
		rushHourCollisionDetector.checkMove(move);
		rushHourCollisionDetector.doMove(move);
		
		moveable1.setPosition(new Point(0,5));
		Move move2 = new Move(moveable1, 4);
		rushHourCollisionDetector.checkMove(move2);
		rushHourCollisionDetector.doMove(move2);
		
		moveable1.setPosition(new Point(4,5));
		Move move3 = new Move(moveable1, -3);
		rushHourCollisionDetector.checkMove(move3);
		rushHourCollisionDetector.doMove(move3);
		
		moveable1.setPosition(new Point(1,5));
		Move move4 = new Move(moveable1, 2);
		rushHourCollisionDetector.checkMove(move4);
		rushHourCollisionDetector.doMove(move4);
		
		moveable1.setPosition(new Point(3,5));
		Move move5 = new Move(moveable1, -1);
		rushHourCollisionDetector.checkMove(move5);
		rushHourCollisionDetector.doMove(move5);
		
		moveable1.setPosition(new Point(2,5));
		Move move6 = new Move(moveable1, 1);
		rushHourCollisionDetector.checkMove(move6);
		rushHourCollisionDetector.doMove(move6);		
	}
}
