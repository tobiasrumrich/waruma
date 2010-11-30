package com.googlecode.waruma.rushhour.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestMove.class);
		suite.addTestSuite(TestStandardCar.class);
		//$JUnit-END$
		return suite;
	}

}
