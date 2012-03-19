package org.deepcover.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.deepcover.ClassTracker;
import org.deepcover.report.ReportWriter;
import org.junit.Test;

public class ReportWriterTest {

	@Test
	public void shouldFindPackageName() {
		// setup
		ClassTracker tracker = mock(ClassTracker.class);
		when(tracker.getClassName()).thenReturn("org.example.core.CoreClass");
		// act
		String packageName = ReportWriter.getPackageName(tracker);
		// assert
		assertEquals("org.example.core", packageName);
	}

}
