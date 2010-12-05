package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian & tobias
 * 
 */

public class RushHourBoardCreationController {

	public RushHourBoardCreationController() {
		super();
	}

	private GameBoard gameBoard;
	private RushHourCollisionDetector collisionDetector;

	public void createCar(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {

		if (steeringLock == false) {
			StandardCar car = new StandardCar(
					new Boolean[][] { { true, true } }, position, orientation);
			gameBoard.addGameBoardObject(car);
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(
					new Boolean[][] { { true, true } }, position, orientation));
			gameBoard.addGameBoardObject(car);
		}
	}

	public void createTruck(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {

		if (!steeringLock) {
			StandardCar car = new StandardCar(new Boolean[][] { { true, true,
					true } }, position, orientation);
			gameBoard.addGameBoardObject(car);
		} else {
			SteeringLock car = new SteeringLock(new StandardCar(
					new Boolean[][] { { true, true, true } }, position,
					orientation));
			gameBoard.addGameBoardObject(car);
		}
	}

	public void createPlayerCar(Point position, Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		PlayerCar car = new PlayerCar(new Boolean[][] { { true, true } },
				position, orientation, collisionDetector);
		car.setDestination(destination);

		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
	}

	public void loadGameBoard(IObjectStorage objectStorage, String location) {
		objectStorage.deserialize(location);
	}
	public void saveGameBoard(IObjectStorage objectStorage, String location) {
		objectStorage.serialize(location);
	}

}