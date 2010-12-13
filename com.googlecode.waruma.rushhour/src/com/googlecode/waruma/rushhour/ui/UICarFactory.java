package com.googlecode.waruma.rushhour.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UICarFactory {

	private HashMap<Integer, HashSet<String>> availableImagesPerLength = new HashMap<Integer, HashSet<String>>();
	private HashMap<CarType, HashSet<String>> availableImagesPerType = new HashMap<CarType, HashSet<String>>();
	private String path;
	
	public void scanDirectory(String location) {
		String[] entries = new File(location).list();

		for (String file : entries) {
			if (file.toUpperCase().endsWith("_CARIMG.PNG")) {
				String[] filename = file.split("_");

				// System.out.println(file + "--" + Arrays.toString(filename));
				Integer carSize = Integer.valueOf(filename[0].replaceAll("F",
						""));
				CarType carType = CarType.valueOf(filename[1].toUpperCase());
				String carName = filename[2];
				// System.out.println(file + " ist ein " + carType + " mit " +
				// carSize + " Felder Länge und nennt sich " + carName);

				// Image in die ImageListe hinzufügen
				if (availableImagesPerLength.containsKey(carSize)) {
					HashSet<String> hashSet = availableImagesPerLength
							.get(carSize);
					hashSet.add(file);
					availableImagesPerLength.put(carSize, hashSet);
				} else {
					HashSet<String> hashSet = new HashSet<String>();
					hashSet.add(file);
					availableImagesPerLength.put(carSize, hashSet);
				}

				if (availableImagesPerType.containsKey(carType)) {
					HashSet<String> hashSet = availableImagesPerType
							.get(carType);
					hashSet.add(file);
					availableImagesPerType.put(carType, hashSet);
				} else {
					HashSet<String> hashSet = new HashSet<String>();
					hashSet.add(file);
					availableImagesPerType.put(carType, hashSet);
				}

				//

				/*
				 * String[]
				 * 
				 * String[] splitted = filename[0].split("_");
				 * System.out.println("FILE FOUND: " + file + " with " +
				 * splitted[0]);
				 */
			}
		}
		// System.out.println(Arrays.toString(entries));
		this.path=location;
	}
	
	public ArrayList<ImageBean> getAvailableImages(int length) {
		HashSet<String> hashSet = availableImagesPerLength.get(Integer.valueOf(length));
		ArrayList<ImageBean> arrayList = new ArrayList<ImageBean>();
		for (String file : hashSet) {
			
			String[] filename = file.split("_");
			Integer carSize = Integer.valueOf(filename[0].replaceAll("F",""));
			CarType carType = CarType.valueOf(filename[1].toUpperCase());
			String carName = filename[2];
			
			ImageBean bean = new ImageBean(file,carSize,carType,carName);
			arrayList.add(bean);
		}
		if (arrayList.isEmpty()) return new ArrayList<ImageBean>();
		return arrayList;
	}

	
	public ArrayList<ImageBean> getAvailableImages(CarType queriedCarType) {
		HashSet<String> hashSet = availableImagesPerType.get(queriedCarType);
		ArrayList<ImageBean> arrayList = new ArrayList<ImageBean>();
		for (String file : hashSet) {
			
			String[] filename = file.split("_");
			Integer carSize = Integer.valueOf(filename[0].replaceAll("F",""));
			CarType carType = CarType.valueOf(filename[1].toUpperCase());
			String carName = filename[2];
			
			ImageBean bean = new ImageBean( file,carSize,carType,carName);
			arrayList.add(bean);
		}
		if (arrayList.isEmpty()) return new ArrayList<ImageBean>();
		return arrayList;
	}

	public String getPath() {
		return path;
	}

}
