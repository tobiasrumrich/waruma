package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.framework.AbstractAdditionalFunctionality;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.CollisionDetector;
import com.googlecode.waruma.rushhour.game.PlayerCar;

public class TestAdditionalFunctionality extends TestCase {

	private class MockAbstractAdditionalFunctionality extends AbstractAdditionalFunctionality{

		public MockAbstractAdditionalFunctionality(
				AbstractMoveable abstractMoveable) {
			super(abstractMoveable);
		}
	}
	
	
	AbstractAdditionalFunctionality aFunc;
	
	protected void setUp() {
		
		Boolean[][] collisionMap = new Boolean[][] { { true, true } };
		CollisionDetector collisionDetector = new CollisionDetector(6);
		PlayerCar car = new PlayerCar(collisionMap, new Point(5, 5), Orientation.NORTH, collisionDetector);
		
		aFunc = new MockAbstractAdditionalFunctionality(car);
	}
	
	public void testGetCollisionMap() {
		Boolean[][] otherCollisionMap = new Boolean[][] { { true, true } };
		aFunc.setCollisionMap(otherCollisionMap);
		assertEquals(otherCollisionMap,aFunc.getCollisionMap());
	}

	public void testGetOrientation() {
		aFunc.setOrientation(Orientation.SOUTH);
		assertEquals(Orientation.SOUTH,aFunc.getOrientation());
	}

	public void testGetPosition() {
		aFunc.setPosition(new Point(9,8));
		assertEquals(new Point(9,8), aFunc.getPosition());
	}

}
