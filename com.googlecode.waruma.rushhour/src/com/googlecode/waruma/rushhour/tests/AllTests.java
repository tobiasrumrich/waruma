package com.googlecode.waruma.rushhour.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestMove.class);
		suite.addTestSuite(TestStandardCar.class);
		suite.addTestSuite(TestPlayerCar.class);
		suite.addTestSuite(TestGameBoard.class);
		suite.addTestSuite(TestFileSystemObjectStorage.class);
		// $JUnit-END$
		return suite;
	}

}
