package com.googlecode.waruma.rushhour.framework;

import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author dep18237
 *
 */
public interface IObjectStorage {
	/**
	 * Speichert ein Objekt an den über location übergebenen einen Speicherort.
	 * @param location ist der Speicherort(Filesystem, Database, FTP, WebDAV,...)
	 */
	public Object deserialize(String location) throws IOException, ClassNotFoundException;
	void serialize(Serializable object, String location) throws IOException;
}