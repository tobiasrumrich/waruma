package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Stellt die Vermittlungsfunktionen zwischen Präsentation und Fachlogik zur
 * Erstellung von Spielfeldern zur Verfügung
 * 
 * @author Fabian & Tobias
 */
public class RushHourBoardCreationController {
	private GameBoard gameBoard;
	private CollisionDetector collisionDetector;
	private boolean hasPlayer;

	public RushHourBoardCreationController() {
		this(6, 6);
	}
	
	public RushHourBoardCreationController(int width, int height) {
		this.collisionDetector = new CollisionDetector(width, height);
		this.gameBoard = new GameBoard(this.collisionDetector);
		this.hasPlayer = false;
	}
	
	/**
	 * Erstellt ein Auto auf dem Spielbrett und gibt das GameBoardobjekt zurück
	 * 
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung des Autos
	 * @param steeringLock
	 *            Lenkradschloss
	 * @return Hinzugefügtes Auto
	 * @throws IllegalBoardPositionException
	 *             Bei einer ungültigen Position
	 */
	public IGameBoardObject createCar(org.eclipse.swt.graphics.Point position, Orientation orientation,
			boolean steeringLock) throws IllegalBoardPositionException {

		IGameBoardObject car = new StandardCar(new Boolean[][] { { true },
				{ true } }, swtToAwtPoint(position), orientation);
		
		if (steeringLock) {
			car = new SteeringLock((AbstractMoveable) car);
		}

		gameBoard.addGameBoardObject(car);

		return car;
	}

	/**
	 * Erstellt ein Auto auf dem Spielbrett und gibt das GameBoardobjekt zurück
	 * 
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung des Autos
	 * @param steeringLock
	 *            Lenkradschloss
	 * @return Hinzugefügtes Auto
	 * @throws IllegalBoardPositionException
	 *             Bei einer ungültigen Position
	 */
	public IGameBoardObject createTruck(org.eclipse.swt.graphics.Point position, Orientation orientation,
			boolean steeringLock) throws IllegalBoardPositionException {
		IGameBoardObject truck = new StandardCar(new Boolean[][] { { true },
				{ true }, { true } }, swtToAwtPoint(position), orientation);
		
		if (steeringLock) {
			truck = new SteeringLock((AbstractMoveable) truck);
		}

		gameBoard.addGameBoardObject(truck);

		return truck;
	}

	/**
	 * Erstellt ein Spielerauto - Es kann pro Spiel nur ein Spielerauto geben
	 * 
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param destination
	 *            Zielposition
	 * @param orientation
	 *            Orientierung
	 * @return Hinzugefügtes Spielerauto
	 * @throws IllegalBoardPositionException
	 *             Bei ungültigen Positionen oder bereits vorhandenem
	 *             Spielerauto
	 */
	public IGameBoardObject createPlayerCar(org.eclipse.swt.graphics.Point position, org.eclipse.swt.graphics.Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if (hasPlayer) {
			throw new IllegalBoardPositionException(
					"Es ist nur ein Spielerauto zulässig!");
		}
		PlayerCar car = new PlayerCar(new Boolean[][] { { true},{ true } },
				swtToAwtPoint(position), orientation, collisionDetector);
		car.setDestination(swtToAwtPoint(destination));
		// throws IllegalBoardPositionException
		gameBoard.addGameBoardObject(car);
		
		return car;
	}
	
	public void removeObjectFromBoard(IGameBoardObject gameBoardObject) {
		gameBoard.removeGameBoardObject(gameBoardObject);
	}
	
	/**
	 * Ermöglicht das nachträgliche Verändern der Autoposition
	 * 
	 * @param gameBoardObject
	 * @param position
	 * @throws IllegalBoardPositionException
	 */
	public void changeCarPosition(IGameBoardObject gameBoardObject, org.eclipse.swt.graphics.Point position) throws IllegalBoardPositionException{
		gameBoard.repositionGameBoardObject(gameBoardObject, swtToAwtPoint(position));
	}

	public void changeRotation(IGameBoardObject gameBoardObject, Orientation orientation) throws IllegalBoardPositionException{
		gameBoard.rotateGameBoardObject(gameBoardObject, orientation);
	}
	
	public boolean validTile(org.eclipse.swt.graphics.Point position) {
		return collisionDetector.validTile(swtToAwtPoint(position));
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
	
	public Object getCurrentState(){
		return gameBoard;
	}
	
	private Point swtToAwtPoint(org.eclipse.swt.graphics.Point swtPoint){
		if(swtPoint == null)
			throw new IllegalArgumentException("Punkt nicht gesetzt");
		
		return new Point(swtPoint.x, swtPoint.y);
	}

}