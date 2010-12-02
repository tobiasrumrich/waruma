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
 * @author RuMa
 * 
 */


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
	
	
	//Wir testen, ob wir das einmalige Vor- und Zur�ckfahren jeweils funktioniert
	
	public void testMoveWithNorthForwardOnce()  throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorw�rts
		car.move(3);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
	}
	
	public void testMoveWithNorthBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (5,2) und richten es mit unserem Kompass nach Norden aus
		car.setOrientation(Orientation.NORTH);
		car.setPosition(new Point(5,2));
		
		//Wir stehen auf (5,2) und fahren 6 Felder r�ckw�rts
		car.move(-6);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());
	}
	
	public void testMoveWithSouthForwardOnce() throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass nach S�den aus
		car.setOrientation(Orientation.SOUTH);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorw�rts
		car.move(3);
		//Wir sollten nun auf (5,8) stehen
		assertEquals(new Point(5,8),car.getPosition());		
	}
	
	public void testMoveWithSouthBackwardOnce() throws IllegalMoveException{
		
		//Wir positionieren unser Auto auf (5,8) und richten es mit unserem Kompass nach S�den aus
		car.setOrientation(Orientation.SOUTH);
		car.setPosition(new Point(5,8));

		//Wir stehen auf (5,8) und fahren 6 Felder r�ckw�rts
		car.move(-6);
		//Wir sollten nun auf (5,2) stehen
		assertEquals(new Point(5,2),car.getPosition());
		
	}
	
	public void testMoveWithWestForwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (5,5) und richten es mit unserem Kompass zu den alten Bundesl�ndern aus
		car.setOrientation(Orientation.WEST);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorw�rts
		car.move(3);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
	}
	
	
	public void testMoveWithWestBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (2,5) und richten es mit unserem Kompass zu den alten Bundesl�ndern aus
		car.setOrientation(Orientation.WEST);
		car.setPosition(new Point(2,5));

		
		//Wir stehen auf (2,5) und fahren 6 Felder r�ckw�rts
		car.move(-6);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
		
	}
	
	
	public void testMoveWithEastForwardOnce() throws IllegalMoveException {
				
		//Wir positionieren unser Auto auf (5,5), es wurde offensichtlich gestohlen und ist Richtung Polen ausgerichtet
		car.setOrientation(Orientation.EAST);
		car.setPosition(new Point(5,5));
		
		//Wir stehen auf (5,5) und fahren 3 Felder vorw�rts
		car.move(3);
		//Wir sollten nun auf (8,5) stehen
		assertEquals(new Point(8,5),car.getPosition());
	}
	
	
	public void testMoveWithEastBackwardOnce() throws IllegalMoveException {
		
		//Wir positionieren unser Auto auf (8,5), es wurde offensichtlich gestohlen und ist Richtung Polen ausgerichtet
		car.setOrientation(Orientation.EAST);
		car.setPosition(new Point(8,5));
		
		//Wir stehen auf (8,5) und fahren 6 Felder r�ckw�rts
		car.move(-6);
		//Wir sollten nun auf (2,5) stehen
		assertEquals(new Point(2,5),car.getPosition());
	}
	
	
	
	private class MockMoveableObject extends AbstractMoveable{
		
	
		public MockMoveableObject(Boolean[][] collisionMap, Point position,
				Enum<Orientation> orientation) {
			super(collisionMap, position, orientation);
			// TODO Auto-generated constructor stub
		}

		public void move(int distance) throws IllegalMoveException {
			// wird eh nicht genutzt, da von SteeringLock �berschrieben
			
		}
			
		
	//Wir testen, ob wir nicht zwei mal fahren k�nnen	
	public void testLock() throws IllegalMoveException{
		thrownException=false;
		
		SteeringLock car = new SteeringLock(new MockMoveableObject(collisionMap, new Point(5,5), Orientation.NORTH));
		car.move(1);
		try {
			car.move(1);
		}
			catch(IllegalMoveException e){
				thrownException=true;
			}
		
		
		
	}
		
	}
	
}
