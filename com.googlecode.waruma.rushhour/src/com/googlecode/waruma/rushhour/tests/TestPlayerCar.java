package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.StandardCar;

import junit.framework.TestCase;

/**
 * 
 * @author tobias
 * 
 */
public class TestPlayerCar extends TestCase {
	public Boolean[][] collisionMap;
	private StandardCar car;

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };

		car = new StandardCar(collisionMap, new Point(3, 1), Orientation.NORTH);
	}

	public void testPlayerCar() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(3,1),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}

	public void testMoveInteger() {
		fail("Not yet implemented");
	}

	public void testRegisterReachedDestination() {
		fail("Not yet implemented");
	}

}
