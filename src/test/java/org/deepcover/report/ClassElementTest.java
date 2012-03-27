package org.deepcover.report;

import static org.junit.Assert.assertEquals;

import org.deepcover.ArgTypes;
import org.deepcover.Checks;
import org.deepcover.MethodTracker;
import org.junit.Before;
import org.junit.Test;

public class ClassElementTest {

	private static final String D_D = "%d / %d";
	private ClassElement classElement = new ClassElement();

	@Before
	public void before() {

	}

	@Test
	public void shouldCalculateChecksFromMethodElements() {
		// setup
		MethodElement method1 = new MethodElement();
		method1.actualEmpty = 1;
		method1.actualNotNull = 2;
		method1.actualNull = 3;
		method1.totalEmpty = 4;
		method1.totalNotNull = 5;
		method1.totalNull = 6;

		// act
		classElement.addElement(method1);
		// assert
		assertEquals(String.format(D_D, 1, 4), classElement.getEmptyChecks());
		assertEquals(String.format(D_D, 2, 5), classElement.getNotNullChecks());
		assertEquals(String.format(D_D, 3, 6), classElement.getNullChecks());
	}

	@Test
	public void shouldCalculateChecksFromMethodTracker() {
		// setup
		MethodTracker tracker = new MethodTracker("methodName", new ArgTypes[] {
				ArgTypes.NUMBER, ArgTypes.OBJECT, ArgTypes.STRING });
		tracker.addCheck("arg1", Checks.NULL);
		tracker.addCheck("arg2", Checks.NOT_NULL);
		tracker.addCheck("arg3", Checks.ZERO_OR_EMPTY);
		// act
		classElement.addMethod(tracker);
		// assert
		assertEquals(String.format(D_D, 1, 2), classElement.getEmptyChecks());
		assertEquals(String.format(D_D, 1, 3), classElement.getNotNullChecks());
		assertEquals(String.format(D_D, 1, 3), classElement.getNullChecks());
	}
}
