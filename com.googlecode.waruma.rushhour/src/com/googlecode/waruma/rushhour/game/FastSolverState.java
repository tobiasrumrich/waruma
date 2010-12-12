package com.googlecode.waruma.rushhour.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Diese Klasse repr�sentiert eine vom Solver untersuchte Spielbrettposition.
 * Die Kollisionserkennung wurde aus Performancegr�nden in einer deutlich
 * entschlankten Version direkt im SolverState implementiert.
 * 
 * @author Florian
 */
public final class FastSolverState {
	public FastSolverState previousState;
	public List<FastSolverCar> cars;
	public FastSolverCar player;
	public int movedCar;
	public byte movedDistance;
	/*
	 * Die collisionMap unterscheidet zwischen drei Stati: 0 - Leer 1 -
	 * Horizontales Auto 2 - Vertikales Auto
	 */
	public byte[][] collisionMap;

	/**
	 * Erzeugt einen neuen SolverState mit einer leern Kollisionsmap in den
	 * angegebenen Dimensionen
	 * 
	 * @param width
	 *            Breite des Spielbretts
	 * @param height
	 *            H�he des Spielbretts
	 */
	public FastSolverState(int width, int height) {
		cars = new ArrayList<FastSolverCar>();
		collisionMap = new byte[width][height];
		// CollisionMap initialisieren
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				collisionMap[x][y] = 0;
			}
		}
	}

	/**
	 * Erzeugt einen neuen Nachfolger des �bergebenen Status, wobei die Felder
	 * auf die gleichen Werte gesetzt werden.
	 * 
	 * @param previousState
	 *            Vorg�ngerstatus
	 */
	public FastSolverState(FastSolverState previousState) {
		this.cars = previousState.deepCloneCars();
		this.collisionMap = previousState.deepCloneCollisionMap();
		this.previousState = previousState;
		// Player ermitteln
		for (FastSolverCar car : cars) {
			if (car.isPlayer) {
				player = car;
			}
		}
	}

	/**
	 * F�gt dem Status ein Auto hinzu und aktualisiert die Kollisionsmap. Eine
	 * Kollisionspr�fung wird dabei nicht durchgef�hrt.
	 * 
	 * @param car
	 *            Hinzuzuf�gendes SolverCar
	 */
	public void addSolverCar(FastSolverCar car) {
		// Kollisionsmap aktualisieren
		if (car.orientation == true) {
			for (int x = car.x; x < car.x + car.length; x++) {
				collisionMap[x][car.y] = 1;
			}
		} else {
			for (int y = car.y; y < car.y + car.length; y++) {
				collisionMap[car.x][y] = 2;
			}
		}
		// Zur Liste hinzuf�gen
		cars.add(car);
		// Ist Spielerauto
		if (car.isPlayer) {
			player = car;
		}
	}

	/**
	 * Hilfsfunktion zur Ermittlung ob die �bergebene Koordinate auf dem
	 * Spielfeld liegt und leer ist.
	 * 
	 * @param x
	 *            X-Koordinate
	 * @param y
	 *            Y-Koordinate
	 * @return True bei g�ltigem und leeren Feld
	 */
	private boolean validTile(int x, int y) {
		if (x < 0 || x >= collisionMap.length)
			return false;
		if (y < 0 || y >= collisionMap[x].length)
			return false;
		if (collisionMap[x][y] == 0)
			return true;
		return false;
	}

	/**
	 * �berpr�ft ob das Feld, das in der �bergebenen Distanz entfernt vom Auto
	 * liegt frei ist.
	 * 
	 * @param car
	 *            Auto f�r das gepr�ft wird
	 * @param distance
	 *            Distanz vom Auto - Kann positiv und negativ sein
	 * @return True bei g�ltigem und leeren Feld
	 */
	public boolean validTile(FastSolverCar car, byte distance) {
		if (car.locked)
			return false;
		// Bei positiven Z�gen die Autol�nge auf die Distanz addieren
		if (distance > 0)
			distance += car.length - 1;
		// Auf X- oder Y-Achse bewegen - true entspricht einer hor. Bewegung
		if (car.orientation == true) {
			return validTile(car.x + distance, car.y);
		} else {
			return validTile(car.x, car.y + distance);
		}
	}

	/**
	 * Verschiebt das Auto an der angegeben Listenposition ohne
	 * Kollisionserkennung um die �bergebene Distanz
	 * 
	 * @param arrayPosition
	 *            Index des Autos aus der Liste
	 * @param distance
	 *            Distanz des Zuges
	 */
	public void moveCar(byte arrayPosition, byte distance) {	
		FastSolverCar car = cars.get(arrayPosition);
		
		if (car.orientation == true) {
			// Aus collisionMap entfernen
			for (int x = car.x; x < car.x + car.length; x++) {
				collisionMap[x][car.y] = 0;

			}
			// Position aktualisieren
			car.x += distance;
			// In collisionMap einf�gen
			for (int x = car.x; x < car.x + car.length; x++) {
				collisionMap[x][car.y] = 1;
			}

		} else {
			// Aus collisionMap entfernen
			for (int y = car.y; y < car.y + car.length; y++) {
				collisionMap[car.x][y] = 0;
			}
			// Position aktualisieren
			car.y += distance;
			// In collisionMap einf�gen
			for (int y = car.y; y < car.y + car.length; y++) {
				collisionMap[car.x][y] = 2;
			}
		}
		
		// Speichern welches Auto bewegt wurde
		movedCar = car.id;
		movedDistance = distance;
	}
	
	/**
	 * Erstellt eine Kopie der CollisionMap
	 * 
	 * @return Kopie des Car-Liste
	 */
	private List<FastSolverCar> deepCloneCars() {
		List<FastSolverCar> clone = new ArrayList<FastSolverCar>(cars.size());
		for (FastSolverCar car : cars)
			clone.add(new FastSolverCar(car));
		return clone;
	}

	/**
	 * Erstellt eine Kopie der CollisionMap Verwendet aus Performancegr�nden
	 * System.arraycopy f�r das Kopieren der Subarrays
	 * 
	 * @return Kopie der CollisionMap
	 */
	private byte[][] deepCloneCollisionMap() {
		byte[][] clonedCollisionMap = new byte[collisionMap.length][];
		for (int i = 0; i < collisionMap.length; i++) {
			clonedCollisionMap[i] = new byte[collisionMap[i].length];
			System.arraycopy(collisionMap[i], 0, clonedCollisionMap[i], 0,
					collisionMap[i].length);
		}
		return clonedCollisionMap;
	}

	/**
	 * Erzeugt den zur Identifizierung des SolverStates verwendeten HashCode.
	 * Der HashCode wird nur in Abh�ngigkeit der collisionMap gebildet.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (collisionMap == null) {
			return 0;
		}
		int hashCode = 1;
		for (int i = 0; i < collisionMap.length; i++) {
			for (int j = 0; j < collisionMap[i].length; j++) {
				// Abh�ngig von dem Wert an der momentanen Position den Hash
				// bestimmen
				// Code in Anlehnung an die Boolean Hashmethode aus dem
				// Java-Framework
				if (collisionMap[i][j] == 1)
					hashCode = 31 * hashCode + 1231;

				if (collisionMap[i][j] == 2)
					hashCode = 17 * hashCode + 1237;

				if (collisionMap[i][j] == 0)
					hashCode = 41 * hashCode + 1361;
			}
		}

		return hashCode;
	}

	/**
	 * ACHTUNG: Es wird nur auf Gleichheit der Kollisionsmap gepr�ft!
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FastSolverState other = (FastSolverState) obj;
		if (!Arrays.deepEquals(collisionMap, other.collisionMap))
			return false;
		return true;
	}
}
