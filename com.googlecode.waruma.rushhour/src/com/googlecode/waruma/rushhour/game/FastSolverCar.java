package com.googlecode.waruma.rushhour.game;

import java.util.Random;

/**
 * Dies ist die nach Anforderungen minimal mögliche Implementierung eines Autos.
 * Auf Erweiterbarkeit wurde hier zu Gunsten von Performance bewusst verzichtet.
 * Um eine Klassenzuordnung zur Kompilierungszeit zu ermöglichen ist die Klasse
 * final. Die Datentypen sind auf Grund von Speicherkomplexität und Performance
 * primitiv und so klein wie irgendmöglich.
 * 
 * @author Florian
 */
public final class FastSolverCar {
	public int id;
	public byte x, y, length;
	public boolean isPlayer, isLockCar, locked, orientation;

	/**
	 * Erzeugt ein neues Auto Objekt und weist ihm eine zufällige Id zu die vom
	 * Solver zur Zuordnung zu den Originalautos verwendet wird
	 * 
	 * @param x
	 *            X-Koordinate
	 * @param y
	 *            Y-Koordinate
	 * @param length
	 *            Länge des Autos
	 * @param orientation
	 *            true - horizonal, false - vertikal
	 * @param isPlayer
	 *            Ist Spielerauto
	 * @param isLockCar
	 *            Hat Lenkradschloss
	 * @param locked
	 *            Lenkradschloss gesperrt
	 */
	public FastSolverCar(byte x, byte y, byte length, boolean orientation,
			boolean isPlayer, boolean isLockCar, boolean locked) {
		Random random = new Random();
		id = random.nextInt();
		this.x = x;
		this.y = y;
		this.length = length;
		this.orientation = orientation;
		this.isPlayer = isPlayer;
		this.isLockCar = isLockCar;
		this.locked = locked;
	}

	/**
	 * Erzeugt ein FastSolverCar mit den gleichen Eigenschaften wie das
	 * übergebene
	 * 
	 * @param car
	 *            Kopiervorlage
	 */
	public FastSolverCar(FastSolverCar car) {
		id = car.id;
		x = car.x;
		y = car.y;
		length = car.length;
		orientation = car.orientation;
		isPlayer = car.isPlayer;
		isLockCar = car.isLockCar;
		locked = car.locked;
	}
}
