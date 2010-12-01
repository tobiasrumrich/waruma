package com.googlecode.waruma.rushhour.tests;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.Orientation;
import com.googlecode.waruma.rushhour.game.StandardCar;
import com.googlecode.waruma.rushhour.game.SteeringLock;

import junit.framework.TestCase;

public class TestSteeringCar extends TestCase {

	private Boolean[][] collisionMap;
	private SteeringLock car;
	private boolean thrownException;

	protected void setUp() throws Exception {
		super.setUp();
		collisionMap = new Boolean[][] { { true, true } };
		thrownException = false;
	 
		car = new SteeringLock(new StandardCar(collisionMap,new Point(3,1), Orientation.NORTH));
	}

	public void testSteeringCarInit() {
		assertEquals(collisionMap, car.getCollisionMap());
		assertEquals(new Point(3,1),car.getPosition());
		assertEquals(Orientation.NORTH,car.getOrientation());
	}
	
	
	//Wir testen, ob wir das einmalige Vor- und Zurückfahren jeweils funktioniert
	
	public void testMoveWithNorthForwardOnce()  throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
	}
	
	public void testMoveWithNorthBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (5,2) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,2));
		
		//Wir stehen auf (5,2) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());
	}
	
	public void testMoveWithSouthForwardOnce() throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Süden aus
		car.setOrientation(Orientation.SOUTH);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());		
	}
	
	public void testMoveWithSouthBackwardOnce() throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,8) und richten es mit unserem Kompass nach Süden aus
		car.setOrientation(Orientation.SOUTH);
		car.setPosition(new Point(5,8));

		//Wir stehen auf (5,8) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
	}
	
	public void testMoveWithWestForwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass zu den alten Bundesländern aus
		car.setOrientation(Orientation.WEST);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
	}
	
	
	public void testMoveWithWestBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (2,5) und richten es mit unserem Kompass zu den alten Bundesländern aus
		car.setOrientation(Orientation.WEST);
		car.setPosition(new Point(2,5));

		
		//Wir stehen auf (2,5) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
		
	}
	
	
	public void testMoveWithEastForwardOnce() throws IllegalMoveException {
				
		//Wir positionieren unser Auto auf (5,5), es wurde offensichtlich gestohlen und ist Richtung Polen ausgerichtet
		car.setOrientation(Orientation.EAST);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
	}
	
	
	public void testMoveWithEastBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (8,5), es wurde offensichtlich gestohlen und ist Richtung Polen ausgerichtet
		car.setOrientation(Orientation.EAST);
		car.setPosition(new Point(8,5));
		
		//Wir stehen auf (8,5) und fahren 6 Felder rückwärts
		car.move(-6);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
	}
	
	
	
	//Wir testen, ob wir nicht zwei mal Vor- oder Zurückfahren können
	public void testLock() throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorwärts
		car.move(3);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
		//Wir stehen auf (5,2) und versuchen, 6 Felder rückwärts zu fahren
		try {
			car.move(-6);		
		} 
		//Wir erwarten eine Illegal Move Exception
		catch (IllegalMoveException e) {
			thrownException = true;
		}
		assertTrue(thrownException);
		
		
		
	}
}
