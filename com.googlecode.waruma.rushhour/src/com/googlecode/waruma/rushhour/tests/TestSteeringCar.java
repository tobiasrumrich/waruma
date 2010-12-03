package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

import junit.framework.TestCase;


/**
 * 
 * @author Fabian
 * 
 */


public class TestSteeringCar extends TestCase {

	private Boolean[][] collisionMap;
	private SteeringLock car;
	private boolean thrownException;
	private MockMoveableObject mockMoveableObject;


	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };
		mockMoveableObject = new MockMoveableObject(collisionMap, new Point(5,5), Orientation.NORTH);
		car = new SteeringLock(mockMoveableObject);
		thrownException = false;
	}

	public void testSteeringCarInit() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(3,1),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}
	
	
	private class MockMoveableObject extends AbstractMoveable{
	
		private boolean called;
		private int distance;
	
		public MockMoveableObject(Boolean[][] collisionMap, Point position,
				Enum<Orientation> orientation) {
			super(collisionMap, position, orientation);
			// TODO Auto-generated constructor stub
		}

		public void move(int distance) throws IllegalMoveException {
			called = true;
			this.distance = distance;
		}
	}
			
		
	//Wir testen, ob wir move() aufrufen, wenn sich das Objekt noch einmal bewegen darf.
	public void testMoveOnce() throws IllegalMoveException{
		car.move(1);
		assertTrue(mockMoveableObject.called);
		assertEquals(1, mockMoveableObject.distance);
	}
	
	
	//Wir testen, ob wir nicht zwei mal in die gleiche Richtung fahren kšnnen	
	public void testLock() throws IllegalMoveException{
	
		car.move(1);
		try {
			car.move(1);
		}
			catch(IllegalMoveException e){
				thrownException=true;
			}
		assertTrue(thrownException);
	}
	
	
	//Wir testen, ob wir nicht in zwei unterschiedliche Richtungen fahren kšnnen	
	public void testLockDiffDirections() throws IllegalMoveException{
	
		car.move(2);
		try {
			car.move(-1);
		}
			catch(IllegalMoveException e){
				thrownException=true;
			}
		assertTrue(thrownException);
	}
}
