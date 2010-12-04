package com.googlecode.waruma.rushhour.tests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestFileSystemObjectStorage extends TestCase {

	private class ListObjectMock implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7362834513833590292L;
		private int myNumber;
		private String myString;

		public ListObjectMock(int number, String string) {
			this.myNumber = number;
			this.myString = string;
		}
	}

	private class ObjectMock implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8668861856199245352L;
		private int fourtyTwo = 42;
		private String iLoveSquidSoup = "iLoveSquidSoup";
		private List<ListObjectMock> incredibleList = new ArrayList();
		// (new ListObjectMock(47,"zwei"),new ListObjectMock(1379,"Hund"))

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testLoadGameBoard() {
		fail("Not yet implemented");
	}

	public void testSaveGameBoard() {
		fail("Not yet implemented");
	}

}
