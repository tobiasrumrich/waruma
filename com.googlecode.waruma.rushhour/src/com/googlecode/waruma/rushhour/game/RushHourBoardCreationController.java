package com.googlecode.waruma.rushhour.game;

import java.awt.Point;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.Orientation;


/**
 * 
 * @author fabian
 * 
 */

public class RushHourBoardCreationController {
	
	
	

	private GameBoard gameBoard;

	public void createCar(Point position, Orientation orientation,
			Boolean steeringLock) {
		
		if (steeringLock==false){
			StandardCar car = new StandardCar(new Boolean[][] {{true, true}}, position, orientation);
			try {
				gameBoard.addGameBoardObject(car);
			} catch (IllegalBoardPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(new Boolean[][] {{true, true}}, position, orientation));
			try {
				gameBoard.addGameBoardObject(car);
			} catch (IllegalBoardPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
		}
	}


	public void createTruck(Point position, Orientation orientation,
			Boolean steeringLock) {
		
		if (steeringLock==false){
			StandardCar car = new StandardCar(new Boolean[][] {{true, true, true}}, position, orientation);
			try {
				gameBoard.addGameBoardObject(car);
			} catch (IllegalBoardPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(new Boolean[][] {{true, true, true}}, position, orientation));
			try {
				gameBoard.addGameBoardObject(car);
			} catch (IllegalBoardPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
		}
	}
	
	
	public void createPlayerCar(Point position, Point destination,
			Orientation orientation) {
		PlayerCar car = new PlayerCar(new Boolean[][] {{true, true}}, position, orientation);
		car.setDestination(destination);
		try {
			gameBoard.addGameBoardObject(car);
		} catch (IllegalBoardPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
	}


	public void saveGameBoard(String location) {
	}

}