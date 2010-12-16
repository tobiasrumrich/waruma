package com.googlecode.waruma.rushhour.tests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileSystemObjectStorageMock implements Serializable {
	private static final long serialVersionUID = 8668861856199245352L;
	private int fourtyTwo = 42;
	private String iLoveSquidSoup = "iLoveSquidSoup";
	private List<Integer> listInteger = new ArrayList();

	// private List<ListObjectMock> incredibleList = new ArrayList();
	public FileSystemObjectStorageMock() {
		fourtyTwo = fourtyTwo / 7;
		fourtyTwo = fourtyTwo * 7;
		listInteger.add(Integer.valueOf(42));
		listInteger.add(Integer.valueOf(43));
		listInteger.add(Integer.valueOf(44));
		listInteger.add(Integer.valueOf(45));
		listInteger.add(Integer.valueOf(46));
		listInteger.add(Integer.valueOf(47));
		listInteger.add(Integer.valueOf(48));
		listInteger.add(Integer.valueOf(49));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FileSystemObjectStorageMock other = (FileSystemObjectStorageMock) obj;
		if (fourtyTwo != other.fourtyTwo) {
			return false;
		}
		if (iLoveSquidSoup == null) {
			if (other.iLoveSquidSoup != null) {
				return false;
			}
		} else if (!iLoveSquidSoup.equals(other.iLoveSquidSoup)) {
			return false;
		}
		if (listInteger == null) {
			if (other.listInteger != null) {
				return false;
			}
		} else if (!listInteger.equals(other.listInteger)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fourtyTwo;
		result = prime * result
				+ ((iLoveSquidSoup == null) ? 0 : iLoveSquidSoup.hashCode());
		result = prime * result
				+ ((listInteger == null) ? 0 : listInteger.hashCode());
		return result;
	}

}
