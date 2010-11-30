package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.StandardCar;

import junit.framework.TestCase;

public class TestStandardCar extends TestCase {

	public Boolean[][] collisionMap;
	private StandardCar car;

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };
		
		car = new StandardCar(collisionMap,new Point(3,1), Orientation.NORTH);
	}

	/**
	 * Dieser Test prüft das Verhalten des Konstruktors
	 */
	public void testStandardCar() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(3,1),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}

	public void testMove() {

		car.move(-3);
		assertEquals(new Point(3,4), car.getPosition());
		
		car.move(1);
		assertEquals(new Point (3,3), car.getPosition());
		
	}
	
	public void testMoveWithZero () {
		boolean thrownException = false;
		try {
			car.move(0);
		}
		catch (IllegalArgumentException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
	}
	

}
