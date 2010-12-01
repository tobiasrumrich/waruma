package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.PlayerCar;

import junit.framework.TestCase;

/**
 * 
 * @author tobias
 * 
 */
public class TestPlayerCar extends TestCase {
	public Boolean[][] collisionMap;
	private PlayerCar car;
	private boolean thrownException;

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };

		car = new PlayerCar(collisionMap, new Point(3, 1), Orientation.NORTH);
	}

	public void testPlayerCar() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(3,1),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}

	public void testMoveWithNorth() throws IllegalMoveException{
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,5));
		
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
		car.setPosition(new Point(5,5));
		
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
		car.setPosition(new Point(5,5));
		
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
		car.setPosition(new Point(5,5));
		
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
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,5));
		
		try {
			car.move(0);
		}
		catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
	}

	public void testRegisterReachedDestination() {
		fail("Not yet implemented");
	}

}
