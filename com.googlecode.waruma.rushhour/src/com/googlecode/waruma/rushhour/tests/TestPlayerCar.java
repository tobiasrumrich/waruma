package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.PlayerCar;

import junit.framework.TestCase;

/**
 * 
 * @author WaRuMa
 * 
 */
public class TestPlayerCar extends TestCase {
	public Boolean[][] collisionMap;
	private PlayerCar car;
	private boolean thrownException;
	private MockObserver mockObserver1;
	private MockObserver mockObserver2;

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };
		car = new PlayerCar(collisionMap, new Point(5,5), Orientation.NORTH);
		car.setDestination(new Point(4,2));
		
		mockObserver1 = new MockObserver();
		mockObserver2 = new MockObserver();
		
		car.registerReachedDestination(mockObserver1);
		car.registerReachedDestination(mockObserver2);
	}

	public void testPlayerCar() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(5,5),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}

	public void testMoveNotReachedDestination() throws IllegalMoveException{
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
		//Wir stehen auf (5,2) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());
		
		assertFalse(mockObserver2.called);
	}
	

	public void testMoveWithZero () throws IllegalMoveException {
		thrownException = false;
		
		try {
			car.move(0);
		}
		catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
	}

	private class MockObserver implements IReachedDestinationObserver{
		private boolean called = false;
				
		@Override
		public void updateReachedDestination(IPlayer player) {
			called = true;
		}
	}
	
	public void testReachedDestination() throws IllegalMoveException {
		
		car.setOrientation(Orientation.EAST);
		car.setPosition(new Point (2,2));
		car.setDestination(new Point(4,2));
		car.move(2);
		assertTrue(mockObserver1.called);
		assertTrue(mockObserver2.called);
	}
}
