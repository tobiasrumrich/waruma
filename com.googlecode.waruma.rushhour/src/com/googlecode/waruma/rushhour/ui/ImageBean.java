package com.googlecode.waruma.rushhour.ui;

public class ImageBean {
	private String filename;
	private int length;
	private CarType carType;
	private String carName;

	public ImageBean(String filename, int length, CarType carType,
			String carName) {
		super();
		this.filename = filename;
		this.length = length;
		this.carType = carType;
		this.carName = carName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

}
