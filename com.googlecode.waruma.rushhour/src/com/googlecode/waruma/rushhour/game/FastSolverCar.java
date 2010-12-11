package com.googlecode.waruma.rushhour.game;

import java.util.Random;

/**
 * Dies ist die nach Anforderungen minimal mögliche Implementierung eines Autos.
 * Auf Erweiterbarkeit wurde hier zu Gunsten von Performance bewusst verzichtet.
 * Um eine Klassenzuordnung zur Kompilierungszeit zu ermöglichen ist die Klasse 
 * final. Die Datentypen sind auf Grund von Speicherkomplexität und Performance
 * primitiv und so klein wie irgendmöglich.
 * @author Florian
 */
public final class FastSolverCar {
	public int id;
	public byte x,y, length;
	public boolean isPlayer, isLockCar, locked, orientation;
	
	/**
	 * Erzeugt ein neues Auto Objekt und weist ihm eine zufällige Id zu
	 * die vom Solver zur Zuordnung zu den Originalautos verwendet wird
	 * @param X-Koordinate
	 * @param Y-Koordinate
	 * @param Länge des Autos
	 * @param true - horizonal, false - vertikal
	 * @param Ist Spielerauto
	 * @param Hat Lenkradschloss
	 * @param Lenkradschloss gesperrt
	 */
	public FastSolverCar(byte x, byte y, byte length, boolean orientation, 
						 boolean isPlayer, boolean isLockCar, boolean locked){
		Random random = new Random();
		this.id = random.nextInt();
		this.x = x;
		this.y = y;
		this.length = length;
		this.orientation = orientation;
		this.isPlayer = isPlayer;
		this.isLockCar = isLockCar;
		this.locked = locked;		
	}
	
	/**
	 * Erzeugt ein FastSolverCar mit den gleichen Eigenschaften wie das Übergebene
	 * @param Kopiervorlage
	 */
	public FastSolverCar(FastSolverCar car){
		this.id = car.id;
		this.x = car.x;
		this.y = car.y;
		this.length = car.length;
		this.orientation = car.orientation;
		this.isPlayer = car.isPlayer;
		this.isLockCar = car.isLockCar;
		this.locked = car.locked;		
	}	
}
