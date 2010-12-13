package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IObjectStorage;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * 
 * @author fabian & tobias
 * 
 */

public class RushHourBoardCreationController {
	private GameBoard gameBoard;
	private CollisionDetector collisionDetector;
	private boolean hasPlayer;
	private Map<String, IGameBoardObject> gameBoardObjectMap;
	private int carCounter;
	private int truckCounter;

	public RushHourBoardCreationController() {
		this.collisionDetector = new CollisionDetector(6);
		this.gameBoard = new GameBoard(this.collisionDetector);
		this.gameBoardObjectMap = new HashMap<String, IGameBoardObject>();
		this.hasPlayer = false;
		this.carCounter = 1;
		this.truckCounter = 1;
	}

	public String createCar(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {

		IGameBoardObject car = new StandardCar(new Boolean[][] { { true },
				{ true } }, position, orientation);

		if (steeringLock) {
			car = new SteeringLock((AbstractMoveable) car);
		}

		gameBoard.addGameBoardObject(car);

		String name = "PKW" + carCounter;
		carCounter++;
		
		gameBoardObjectMap.put(name, car);
		
		return name;
	}

	public String createTruck(Point position, Orientation orientation,
			Boolean steeringLock) throws IllegalBoardPositionException {
		IGameBoardObject truck = new StandardCar(new Boolean[][] { { true },
				{ true }, { true } }, position, orientation);

		if (steeringLock) {
			truck = new SteeringLock((AbstractMoveable) truck);
		}

		gameBoard.addGameBoardObject(truck);

		String name = "LKW" + truckCounter;
		truckCounter++;
		
		gameBoardObjectMap.put(name, truck);
		
		return name;
	}

	public String createPlayerCar(Point position, Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if (hasPlayer) {
			throw new IllegalBoardPositionException(
					"Es ist nur ein Spielerauto zulässig!");
		}
		PlayerCar car = new PlayerCar(new Boolean[][] { { true, true } },
				position, orientation, collisionDetector);
		car.setDestination(destination);
		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
		
		String name = "Spieler";
		gameBoardObjectMap.put(name, car);
		hasPlayer = true;
		
		return name;
	}
	
	public void changeCarPosition(String car, Point position) throws IllegalBoardPositionException{
		IGameBoardObject gameBoardObject = gameBoardObjectMap.get(car);
		
		gameBoard.repositionGameBoardObject(gameBoardObject, position);
	}

	public boolean validTile(Point position) {
		return collisionDetector.validTile(position);
	}

	public void loadGameBoard(String location) throws IOException,
			ClassNotFoundException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		gameBoard = (GameBoard) fileSystemObjectStorage.deserialize(location);
	}

	public void saveGameBoard(String location) throws IOException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		fileSystemObjectStorage.serialize(gameBoard, location);
	}

}