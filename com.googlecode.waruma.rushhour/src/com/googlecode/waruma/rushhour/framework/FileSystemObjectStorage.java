package com.googlecode.waruma.rushhour.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Sorgt für die (De-)Serialisierung von Objekten im Dateisystem
 * 
 * @author Tobias Rumrich
 */
public class FileSystemObjectStorage implements IObjectStorage {
	
	@Override
	public void serialize(Serializable serializableObject, String location) throws IOException {

		if (location == null || location.equals("")) {
			throw new IllegalArgumentException("Illegal filename");
		}
		
		//vgl. http://java.sun.com/developer/technicalArticles/Programming/serialization/
		
		FileOutputStream fileOutput = new FileOutputStream(location);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(serializableObject);
		objectOutput.close();
		fileOutput.close();
	}

	@Override
	public Serializable deserialize(String location) throws IOException, ClassNotFoundException {
		if (location == null || location.equals("")) {
			throw new IllegalArgumentException("Illegal filename");
		}
		
		//vgl. http://java.sun.com/developer/technicalArticles/Programming/serialization/
		
		FileInputStream fileOutput = new FileInputStream(location);
		ObjectInputStream objectInput = new ObjectInputStream(fileOutput);
		Serializable readObject = (Serializable) objectInput.readObject();
		objectInput.close();
		fileOutput.close();
		
		return readObject;
	}

}