package com.googlecode.waruma.rushhour.game;

import java.awt.Point;
import java.io.IOException;
import java.util.Collection;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.framework.AbstractMoveable;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.ICollisionDetector;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.Orientation;

/**
 * Stellt die Vermittlungsfunktionen zwischen Pr�sentation und Fachlogik zur
 * Erstellung von Spielfeldern zur Verf�gung
 * 
 * @author Fabian & Tobias
 */
public class RushHourBoardCreationController {
	private GameBoard gameBoard;
	private ICollisionDetector collisionDetector;
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
	 * Erstellt ein Auto auf dem Spielbrett und gibt das GameBoardobjekt zur�ck
	 * 
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung des Autos
	 * @param steeringLock
	 *            Lenkradschloss
	 * @return Hinzugef�gtes Auto
	 * @throws IllegalBoardPositionException
	 *             Bei einer ung�ltigen Position
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
	 * Erstellt ein Auto auf dem Spielbrett und gibt das GameBoardobjekt zur�ck
	 * 
	 * @param position
	 *            Position auf dem Spielfeld
	 * @param orientation
	 *            Orientierung des Autos
	 * @param steeringLock
	 *            Lenkradschloss
	 * @return Hinzugef�gtes Auto
	 * @throws IllegalBoardPositionException
	 *             Bei einer ung�ltigen Position
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
	 * @return Hinzugef�gtes Spielerauto
	 * @throws IllegalBoardPositionException
	 *             Bei ung�ltigen Positionen oder bereits vorhandenem
	 *             Spielerauto
	 */
	public IGameBoardObject createPlayerCar(org.eclipse.swt.graphics.Point position, org.eclipse.swt.graphics.Point destination,
			Orientation orientation) throws IllegalBoardPositionException {
		if (hasPlayer) {
			throw new IllegalBoardPositionException(
					"Es ist nur ein Spielerauto zul�ssig!");
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
	 * Erm�glicht das nachtr�gliche Ver�ndern der Autoposition
	 * 
	 * @param gameBoardObject
	 * @param position
	 * @throws IllegalBoardPositionException
	 */
	public void changeCarPosition(IGameBoardObject gameBoardObject, org.eclipse.swt.graphics.Point position) throws IllegalBoardPositionException{
		gameBoard.repositionGameBoardObject(gameBoardObject, swtToAwtPoint(position));
	}
	
	public void changeDestination(org.eclipse.swt.graphics.Point destination){
		for (IGameBoardObject boardObject : gameBoard.getGameBoardObjects()) {
			if(boardObject instanceof PlayerCar){
				PlayerCar playerCar = (PlayerCar) boardObject;
				playerCar.setDestination(swtToAwtPoint(destination));
			}
		}
	}

	public void changeRotation(IGameBoardObject gameBoardObject, Orientation orientation) throws IllegalBoardPositionException{
		gameBoard.rotateGameBoardObject(gameBoardObject, orientation);
	}
	
	public boolean validTile(org.eclipse.swt.graphics.Point position) {
		return collisionDetector.validTile(swtToAwtPoint(position));
	}

	public void loadGameBoard(String location) throws IOException{
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		try {
			gameBoard = (GameBoard) fileSystemObjectStorage.deserialize(location);
			collisionDetector = gameBoard.getCollisionDetector();
			gameBoard.rebuildGameBoardObjects();
			unlockAllCars(); 
		} catch (ClassNotFoundException e) {
			throw new IOException("Fehler beim Laden der Datei");
		}
	}
	
	public void loadState(Object state){
		try{
			gameBoard = (GameBoard) state;
			collisionDetector = gameBoard.getCollisionDetector();
			gameBoard.rebuildGameBoardObjects();
			unlockAllCars();
		} catch (Exception e){
			throw new IllegalArgumentException();
		}
	}

	public void saveGameBoard(String location) throws IOException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		fileSystemObjectStorage.serialize(gameBoard, location);
	}
	
	public Object getCurrentState(){
		return gameBoard;
	}
	
	public void unlockAllCars(){
		Collection<IGameBoardObject> boardObjects = gameBoard.getGameBoardObjects();
		SteeringLock currentLockable;
		for (IGameBoardObject boardObject : boardObjects) {
			if(boardObject instanceof SteeringLock){
				currentLockable = (SteeringLock) boardObject;
				currentLockable.unlock();
			}
		}
	}
	
	public Collection<IGameBoardObject> getGameBoardObjects(){
		return gameBoard.getGameBoardObjects();
	}
	
	private Point swtToAwtPoint(org.eclipse.swt.graphics.Point swtPoint){
		if(swtPoint == null)
			throw new IllegalArgumentException("Punkt nicht gesetzt");
		
		return new Point(swtPoint.x, swtPoint.y);
	}

}