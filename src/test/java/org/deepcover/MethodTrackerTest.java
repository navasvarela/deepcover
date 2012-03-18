package org.deepcover;

import static org.deepcover.ArgTypes.NUMBER;
import static org.deepcover.ArgTypes.OBJECT;
import static org.deepcover.ArgTypes.STRING;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MethodTrackerTest {

	private static final String ARG1 = "arg1";

	private MethodTracker tracker = new MethodTracker("testMethod",
			new ArgTypes[] { NUMBER, STRING, OBJECT });

	@Before
	public void setup() {

	}

	@Test
	public void shouldAddChecks() {
		System.err.println("running shouldAddChecks");
		// act
		tracker.addCheck(ARG1, Checks.NULL);
		// assert
		assertEquals(Checks.NULL.getMask(), tracker.getArgumentCheck(ARG1));
		tracker.addCheck(ARG1, Checks.NULL);
		assertEquals(Checks.NULL.getMask(), tracker.getArgumentCheck(ARG1));
	}

	@Test
	public void shouldAddConsecutiveChecks() {
		// act
		tracker.addCheck(ARG1, Checks.NULL);
		tracker.addCheck(ARG1, Checks.NOT_NULL);
		tracker.addCheck(ARG1, Checks.ZERO_OR_EMPTY);
		// assert
		int check = tracker.getArgumentCheck(ARG1);
		assertEquals(7, check);

	}

}
