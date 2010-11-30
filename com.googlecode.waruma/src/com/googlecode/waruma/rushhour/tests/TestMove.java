package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.Move;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.StandardCar;

import junit.framework.TestCase;

public class TestMove extends TestCase {

	private StandardCar iMoveable;

	protected void setUp() throws Exception {
		super.setUp();
		Boolean[][] collisionMap = { { true, true } };
		iMoveable = new StandardCar(collisionMap, new Point(2, 3),
				Orientation.NORTH);
	}

	public void testMoveWithMoveableNull() {
		boolean thrownException = false;
		try {
			new Move(null, 3);
		} catch (IllegalArgumentException e) {
			thrownException = true;
		}
		catch (Exception e) {
		}
		assertTrue(thrownException);
	}
	
	public void testMoveWithDistanceNull() {
		boolean thrownException = false;
		try {
			new Move(iMoveable, null);
		} catch (IllegalArgumentException e) {
			thrownException = true;
		}
		catch (Exception e) {
		}
		assertTrue(thrownException);
	}

	public void testGetMoveable() {
		IMove myMove = new Move(iMoveable, 3);
		IMoveable myMoveable = myMove.getMoveable();
		assertTrue(myMoveable.equals(iMoveable));
		assertEquals(3, myMove.getDistance());
	}

	public void testGetDistance() {
		IMove myMove = new Move(iMoveable, 3);
		IMoveable myMoveable = myMove.getMoveable();
		assertTrue(myMoveable.equals(iMoveable));
	}

}