package org.deepcover;

import static org.deepcover.ArgTypes.NUMBER;
import static org.deepcover.ArgTypes.OBJECT;
import static org.deepcover.ArgTypes.STRING;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ClassTrackerTest {

	private static final String TEST_METHOD = "testMethod";
	private ClassTracker classTracker;

	@Before
	public void beforeClass() {
		classTracker = new ClassTracker("TEST_CLASS",
				new HashMap<String, MethodTracker>() {
					{
						put(TEST_METHOD, new MethodTracker(TEST_METHOD,

						new ArgTypes[] { NUMBER, STRING, OBJECT }));
					}
				});
	}

	@Test
	public void shouldCalculateChecksAllNotNull() {
		classTracker.calculateChecksForMethod(TEST_METHOD, new Object[] { 1,
				"not null string", this });

		// assert
		MethodTracker methodTracker = classTracker.getMethodsMap().get(
				TEST_METHOD);
		assertEquals(Checks.NOT_NULL.getMask(),
				methodTracker.getArgumentCheck("arg1"));
		assertEquals(Checks.NOT_NULL.getMask(),
				methodTracker.getArgumentCheck("arg2"));
		assertEquals(Checks.NOT_NULL.getMask(),
				methodTracker.getArgumentCheck("arg3"));
	}

	@Test
	public void shouldCalculateChecksAllNull() {
		classTracker.calculateChecksForMethod(TEST_METHOD, new Object[] { null,
				null, null });

		// assert
		MethodTracker methodTracker = classTracker.getMethodsMap().get(
				TEST_METHOD);
		assertEquals(Checks.NULL.getMask(),
				methodTracker.getArgumentCheck("arg1"));
		assertEquals(Checks.NULL.getMask(),
				methodTracker.getArgumentCheck("arg2"));
		assertEquals(Checks.NULL.getMask(),
				methodTracker.getArgumentCheck("arg3"));
	}

	@Test
	public void shouldCalculateChecksEmpty() {
		classTracker.calculateChecksForMethod(TEST_METHOD, new Object[] { 0,
				"", this });

		// assert
		MethodTracker methodTracker = classTracker.getMethodsMap().get(
				TEST_METHOD);
		assertEquals(Checks.ZERO_OR_EMPTY.getMask(),
				methodTracker.getArgumentCheck("arg1"));
		assertEquals(Checks.ZERO_OR_EMPTY.getMask(),
				methodTracker.getArgumentCheck("arg2"));
		assertEquals(Checks.NOT_NULL.getMask(),
				methodTracker.getArgumentCheck("arg3"));
	}
}
