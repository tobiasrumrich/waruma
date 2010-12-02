package com.googlecode.waruma.rushhour.tests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import junit.framework.TestCase;

public class TestFileSystemObjectStorage extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	private class ObjectMock implements Serializable {
		private int fourtyTwo = 42;
		private String iLoveSquidSoup = "iLoveSquidSoup";
		private List<ListObjectMock> incredibleList = new ArrayList();
		//(new ListObjectMock(47,"zwei"),new ListObjectMock(1379,"Hund"))
		
	}
	
	private class ListObjectMock implements Serializable {
		private int myNumber;
		private String myString;
		public ListObjectMock(int number,String string) {
			this.myNumber = number;
			this.myString = string;
		}
	}
	
	public void testLoadGameBoard() {
		fail("Not yet implemented");
	}

	public void testSaveGameBoard() {
		fail("Not yet implemented");
	}

}
