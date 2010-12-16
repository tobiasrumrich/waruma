package com.googlecode.waruma.rushhour.framework;

import java.io.IOException;
import java.io.Serializable;

/**
 * (De-)Serialisiert Objekte auf ein persistentes Speichermedium
 * 
 * @author Tobias Rumrich
 */
public interface IObjectStorage {
	/**
	 * Läd ein Objekt aus der in location übergebenen Datei.
	 * 
	 * @param location
	 *            Speicherort(Filesystem, Database, FTP, WebDAV,...)
	 * 
	 * @return Deserialisiertes Objekt
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deserialize(String location) throws IOException,
			ClassNotFoundException;

	/**
	 * Speichert ein Objekt an den über location übergebenen einen Speicherort.
	 * 
	 * @param location
	 *            Speicherort(Filesystem, Database, FTP, WebDAV,...)
	 * @param object
	 *            Das zu serialisierende Objekt
	 * @throws IOException
	 */
	public void serialize(Serializable object, String location)
			throws IOException;
}