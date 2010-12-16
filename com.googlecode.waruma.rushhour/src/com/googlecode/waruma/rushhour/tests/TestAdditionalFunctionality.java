package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.CollisionDetector;
import com.googlecode.waruma.rushhour.game.PlayerCar;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

public class TestAdditionalFunctionality extends TestCase {

	private class MockAbstractAdditionalFunctionality extends
			AbstractAdditionalFunctionality {

		public MockAbstractAdditionalFunctionality(
				AbstractMoveable abstractMoveable) {
			super(abstractMoveable);
		}
	}

	AbstractAdditionalFunctionality aFunc;
	boolean exceptionThrown;
	SteeringLock steeringLockCar;

	@Override
	protected void setUp() {

		Boolean[][] collisionMap = new Boolean[][] { { true, true } };
		CollisionDetector collisionDetector = new CollisionDetector(6);
		PlayerCar car =
				new PlayerCar(collisionMap, new Point(5, 5), Orientation.NORTH,
						collisionDetector);
		StandardCar standardCar =
				new StandardCar(new Boolean[][] { { true, true } }, new Point(
						3, 3), Orientation.NORTH);
		steeringLockCar = new SteeringLock(standardCar);

		aFunc = new MockAbstractAdditionalFunctionality(car);
	}

	public void testCheckMove() {

		exceptionThrown = false;

		try {
			steeringLockCar.checkMove(3);
		} catch (IllegalMoveException e) {
		}

		assertFalse(exceptionThrown);

		try {
			steeringLockCar.move(3);
		} catch (IllegalMoveException e1) {
		}

		try {
			steeringLockCar.checkMove(3);
		} catch (IllegalMoveException e3) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	public void testGetCollisionMap() {
		Boolean[][] otherCollisionMap = new Boolean[][] { { true, true } };
		aFunc.setCollisionMap(otherCollisionMap);
		assertEquals(otherCollisionMap, aFunc.getCollisionMap());
	}

	public void testGetOrientation() {
		aFunc.setOrientation(Orientation.SOUTH);
		assertEquals(Orientation.SOUTH, aFunc.getOrientation());
	}

	public void testGetPosition() {
		aFunc.setPosition(new Point(9, 8));
		assertEquals(new Point(9, 8), aFunc.getPosition());
	}
}
