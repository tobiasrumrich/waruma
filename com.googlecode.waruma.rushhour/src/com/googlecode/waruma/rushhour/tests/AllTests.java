package com.googlecode.waruma.rushhour.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestFastSolver.class);
		suite.addTestSuite(TestFileSystemObjectStorage.class);
		suite.addTestSuite(TestAdditionalFunctionality.class);
		suite.addTestSuite(TestGameBoard.class);
		suite.addTestSuite(TestGameState.class);
		suite.addTestSuite(TestController.class);
		suite.addTestSuite(TestMove.class);
		suite.addTestSuite(TestPlayerCar.class);
		suite.addTestSuite(TestCollisionDetector.class);
		suite.addTestSuite(TestStandardCar.class);
		suite.addTestSuite(TestSteeringCar.class);
		
		
		// $JUnit-END$
		return suite;
	}

}
