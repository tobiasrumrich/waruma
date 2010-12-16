package com.googlecode.waruma.rushhour.framework;

import java.awt.Point;

import org.eclipse.swt.graphics.Rectangle;

import com.googlecode.waruma.rushhour.exceptions.IllegalBoardPositionException;
import com.googlecode.waruma.rushhour.exceptions.IllegalMoveException;

/**
 * Diese Klasse implementiert die Kollisionserkennung.
 * 
 * @author Florian Warninghoff
 */
public interface ICollisionDetector {
	/**
	 * Überprüft den übergebenen Zug auf Gültigkeit
	 * 
	 * @param move
	 * @throws IllegalMoveException
	 *             Bei einem ungültigen Zug
	 */
	public void checkMove(IMove move) throws IllegalMoveException;

	/**
	 * Führt den übergebenen Zug ohne weitere Kollisionserkennung aus, sofern er
	 * der zuletzt übergebene ist
	 * 
	 * @param move
	 * @throws IllegalMoveException
	 *             Wenn der Zug nicht der zuvor überprüfte ist
	 */
	public void doMove(IMove move) throws IllegalMoveException;

	/**
	 * Versucht das übergebene GameBoardObject dem Spielbrett hinzuzufügen.
	 * 
	 * @param gameBoardObject
	 * @throws IllegalBoardPositionException
	 *             Bei einer ungültigen Position auf dem Spielbrett
	 */
	public void addGameBoardObject(IGameBoardObject gameBoardObject)
			throws IllegalBoardPositionException;

	/**
	 * Versucht das Objekt auf die angegebene Position zu bewegen
	 * 
	 * @param gameBoardObject
	 * @param position
	 * @throws IllegalBoardPositionException
	 */
	public void moveGameBoardObjectToPosition(IGameBoardObject gameBoardObject,
			Point position) throws IllegalBoardPositionException;

	/**
	 * Enfernt das Objekt auf dem Spielbrett
	 * 
	 * @param gameBoardObject
	 */
	public void removeGameBoardObject(IGameBoardObject gameBoardObject);

	/**
	 * Überprüft ob das übergebene Objekt den spezifizierten Punkt berührt
	 * 
	 * @param gameBoardObject
	 * @param point
	 * @return True bei Berührung
	 */
	public boolean hitPoint(IGameBoardObject gameBoardObject, Point point);

	/**
	 * überprüft ob ein Punkt auf dem Spielbrett frei und gültig ist und gibt
	 * sofern die Überprüfung positiv war true zurück
	 * 
	 * @param point
	 * @return True bei freiem Spielfeld
	 */
	public boolean validTile(Point point);

	/**
	 * Rotiert das Objekt auf dem Spielbrett in die übergebene Orientierung
	 * 
	 * @param gameBoardObject
	 * @param orientation
	 * @throws IllegalBoardPositionException
	 *             Tritt auf wenn die Rotation nicht möglich ist
	 */
	public void rotateGameBoardObject(IGameBoardObject gameBoardObject,
			Orientation orientation) throws IllegalBoardPositionException;

	/**
	 * Ermittelt den Korridor in dem sich das übergebe Objekt bewegen kann.
	 * Dabei ist das Rectangle in Abhängigkeit der Orientierung des GameBoardObjects zu verstehen.
	 * Der Ausgangspunkt ist der hintere linke Punkt des Objektes.
	 * 
	 * @param gameBoardObject
	 * @return
	 */
	public Rectangle getMoveRange(IGameBoardObject gameBoardObject);
}