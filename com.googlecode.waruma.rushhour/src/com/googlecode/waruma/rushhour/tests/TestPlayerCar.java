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

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };

		car = new PlayerCar(collisionMap, new Point(5,5), Orientation.NORTH);
	}

	public void testPlayerCar() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(5,5),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}

	public void testMoveWithNorth() throws IllegalMoveException{
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
		//Wir stehen auf (5,2) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());
	}
	
	public void testMoveWithSouth() throws IllegalMoveException {
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Süden aus
		car.setOrientation(Orientation.SOUTH);
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());
		
		//Wir stehen auf (5,8) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
	}
	
	public void testMoveWithWest() throws IllegalMoveException {
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass zu den alten Bundesländern aus
		car.setOrientation(Orientation.WEST);
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
		
		//Wir stehen auf (2,5) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
		
	}
	
	public void testMoveWithEast() throws IllegalMoveException {
		//Wir positionieren unser Auto auf (5,5), es wurde offensichtlich gestohlen und ist Richtung Polen ausgerichtet
		car.setOrientation(Orientation.EAST);
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
		
		//Wir stehen auf (8,5) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
		
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
		
		MockObserver mockObserver1 = new MockObserver();
		MockObserver mockObserver2 = new MockObserver();
		car.registerReachedDestination(mockObserver1);
		car.registerReachedDestination(mockObserver2);
		car.move(4);
		
		assertTrue(mockObserver1.called);
		assertTrue(mockObserver2.called);
	}
}
