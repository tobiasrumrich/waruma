package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.SteeringLock;

/**
 * 
 * @author Fabian
 * 
 */

public class TestSteeringCar extends TestCase {

	private class MockMoveableObject extends AbstractMoveable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4518726288615582859L;
		private boolean called;
		private int distance;

		public MockMoveableObject(Boolean[][] collisionMap, Point position,
				Orientation orientation) {
			super(collisionMap, position, orientation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void move(int distance) throws IllegalMoveException {
			called = true;
			this.distance = distance;
		}

		@Override
		public void checkMove(int distance) throws IllegalMoveException {
			// TODO Auto-generated method stub
			
		}
	}

	private Boolean[][] collisionMap;
	private SteeringLock car;
	private boolean thrownException;

	private MockMoveableObject mockMoveableObject;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };
		mockMoveableObject = new MockMoveableObject(collisionMap, new Point(5,
				5), Orientation.NORTH);
		car = new SteeringLock(mockMoveableObject);
		thrownException = false;
	}

	// Wir testen, ob wir nicht zwei mal in die gleiche Richtung fahren können
	public void testLock() throws IllegalMoveException {

		car.move(1);
		try {
			car.move(1);
		} catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
	}
	

	// Wir testen, ob wir nicht in zwei unterschiedliche Richtungen fahren
	// können
	public void testLockDiffDirections() throws IllegalMoveException {

		car.move(2);
		try {
			car.move(-1);
		} catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
	}

	// Wir testen, ob wir move() aufrufen, wenn sich das Objekt noch einmal
	// bewegen darf.
	public void testMoveOnce() throws IllegalMoveException {
		car.move(1);
		assertTrue(mockMoveableObject.called);
		assertEquals(1, mockMoveableObject.distance);
		
		Boolean exceptionThrown = false;
		try {
		car.checkMove(1);
		}
		catch (IllegalMoveException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	public void testSteeringCarInit() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(5, 5), car.getPosition());
		assertEquals(Orientation.NORTH, car.getOrientation());
	}
	
	
	// Wir testen, ob wir ein gelocktes Moveable wieder unlocken können.
	public void testUnlock() throws IllegalMoveException {

		car.move(1);
		car.unlock();
		try {
			car.move(1);
		} catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertFalse(thrownException);
	}
}
