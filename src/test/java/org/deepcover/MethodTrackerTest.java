package org.deepcover;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.deepcover.MethodTracker;
import org.junit.Before;
import org.junit.Test;


public class MethodTrackerTest {
	
	private String[] arguments = new String[] {"numberArg", "stringArg", "objectArg"};
	private MethodTracker tracker = new MethodTracker("testMethod", arguments);
	@Before
	public void setup() {
		
	}
	
	@Test
	public void shouldAddChecks() {
		System.err.println("running shouldAddChecks");
		// act
		tracker.addCheck("numberArg", Checks.NULL);
		// assert
		assertEquals(Checks.NULL.getMask(), tracker.getArgumentCheck("numberArg"));
		tracker.addCheck("numberArg", Checks.NULL);
		assertEquals(Checks.NULL.getMask(), tracker.getArgumentCheck("numberArg"));
	}
	
	@Test
	public void shouldAddConsecutiveChecks() {
		// act
		tracker.addCheck("numberArg", Checks.NULL);
		tracker.addCheck("numberArg", Checks.NOT_NULL);
		tracker.addCheck("numberArg", Checks.ZERO_OR_EMPTY);
		// assert
		int check = tracker.getArgumentCheck("numberArg");
		assertEquals(7, check);
		
	}

}
