package com.googlecode.waruma.rushhour.game;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;

import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;
import com.googlecode.waruma.rushhour.framework.FileSystemObjectStorage;
import com.googlecode.waruma.rushhour.framework.GameBoard;
import com.googlecode.waruma.rushhour.framework.GameState;
import com.googlecode.waruma.rushhour.framework.IGameBoardObject;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IGameWonSubject;
import com.googlecode.waruma.rushhour.framework.IMove;
import com.googlecode.waruma.rushhour.framework.IMoveable;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.Move;

/**
 * Verwaltet die Aufrufe der Spielablauflogik durch die Prï¿½sentationsschickt
 * 
 * @author Florian
 */
public class RushHourGameplayControler implements IGameWonSubject {

	private GameState gameState;
	private long gameStartTime;
	private GameBoard gameBoard;
	private boolean timerStarted;

	/**
	 * Erstellt einen neuen GamePlayControler
	 */
	private RushHourGameplayControler() {
		gameState = new GameState();
		timerStarted = false;
	}

	/**
	 * Erstellt einen neuen GamePlayControler durch laden eines Spielstandes
	 * 
	 * @param location
	 *            Speicherort auf dem Datentrï¿½ger
	 * @throws IOException
	 */
	public RushHourGameplayControler(String location) throws IOException {
		this();

		FileSystemObjectStorage storage = new FileSystemObjectStorage();
		try {
			gameBoard = (GameBoard) storage.deserialize(location);
			registerPlayer();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("No valid RushHour State");
		}
	}

	/**
	 * Erstellt einen neuen GamePlayControler aus dem ï¿½bergebenen Spielstand
	 * 
	 * @param state
	 *            Spielstand
	 */
	public RushHourGameplayControler(Object state) {
		this();
		if (state instanceof GameBoard) {
			gameBoard = (GameBoard) state;
			registerPlayer();
		} else {
			throw new IllegalArgumentException("No valid RushHour State");
		}
	}

	public Rectangle getMoveRange(IGameBoardObject gameBoardObject) {
		return gameBoard.getMoveRange(gameBoardObject);
	}

	/**
	 * Gibt eine Collection der auf dem Spielbrett vorhandenen Autos zurï¿½ck
	 * 
	 * @return Liste der Autos
	 */
	public Collection<IGameBoardObject> getCars() {
		return gameBoard.getGameBoardObjects();
	}

	/**
	 * Fï¿½hrt einen Zug auf dem Spielbrett durch
	 * 
	 * @param gameBoardObject
	 *            Zu bewegendes Objekt auf dem Spielbrett
	 * @param distance
	 *            Distanz des Zuges
	 * @throws IllegalMoveException
	 */
	public void moveCar(IGameBoardObject gameBoardObject, int distance)
			throws IllegalMoveException {
		if (gameBoardObject instanceof IMoveable) {
			IMove move = new Move((IMoveable) gameBoardObject, distance);
			gameBoard.move(move);
			if (!timerStarted) {
				gameStartTime = System.currentTimeMillis();
				timerStarted = true;
			}
		} else {
			throw new IllegalMoveException("Auto nicht beweglich!");
		}

	}

	/**
	 * Macht den zuletzt ausgeführten Zug rückgängig und gibt bei Erfolg das
	 * bewegte Auto zurück
	 * 
	 * @return Bewegtes Auto mit neuer Position
	 */
	public IGameBoardObject undoLatestMove() {
		return gameBoard.undoLatestMove();
	}

	/**
	 * Speichert einen Spielstand im angegebenen Pfad
	 * 
	 * @param location
	 *            Pfad und Dateiname
	 * @throws IOException
	 */
	public void saveGame(String location) throws IOException {
		FileSystemObjectStorage fileSystemObjectStorage = new FileSystemObjectStorage();
		fileSystemObjectStorage.serialize(gameBoard, location);
	}

	public Object getCurrentState() {
		return gameBoard;
	}

	public void loadGame(String location) throws IOException {
		FileSystemObjectStorage storage = new FileSystemObjectStorage();
		try {
			gameBoard = (GameBoard) storage.deserialize(location);
			registerPlayer();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("No valid RushHour State");
		}
	}

	/**
	 * Gibt die Liste mit den Notwendigen Zï¿½gen zur Lï¿½sung des momentanen
	 * Spielbretts aus
	 * 
	 * @return Liste der Zï¿½ge
	 */
	public List<IMove> solveGame() {
		FastSolver fastSolver = new FastSolver(gameBoard);
		return fastSolver.solveGameBoard();
	}

	/**
	 * Observer fï¿½r Gewinnmitteilung registrieren
	 * 
	 * @param eventTarget
	 *            Ziel des Aufrufs
	 */
	public void registerGameWon(IGameWonObserver eventTarget) {
		gameState.registerGameWon(eventTarget);
	}

	/**
	 * Gibt die seit Spielstart verstrichene Zeit zurï¿½ck. Format der
	 * Darstellung: Stunden:Minuten:Sekunden
	 * 
	 * @return Zeitstring
	 */
	public String elapsedGameTime() {
		if (gameStartTime > 0) {
			Date date = new Date(System.currentTimeMillis() - gameStartTime
					- 60 * 60 * 1000);
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			return dateFormat.format(date);
		} else {
			return "Game not started";
		}
	}

	/**
	 * Spieler im GameState registrieren
	 */
	private void registerPlayer() {
		for (IGameBoardObject gameBoardObject : gameBoard.getGameBoardObjects()) {
			if (gameBoardObject instanceof IPlayer) {
				gameState.addPlayer((IPlayer) gameBoardObject);
			}
		}
	}

}