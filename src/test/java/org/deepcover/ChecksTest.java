package org.deepcover;

import static org.deepcover.Checks.NOT_NULL;
import static org.deepcover.Checks.NULL;
import static org.deepcover.Checks.ZERO_OR_EMPTY;
import static org.deepcover.Checks.hasMask;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ChecksTest {

	private static final Log LOG = LogFactory.getLog(ChecksTest.class);

	@Test
	public void shouldHaveRightMasks() {
		assertEquals("1", Integer.toBinaryString(Checks.NULL.getMask()));
		assertEquals("10",
				Integer.toBinaryString(Checks.ZERO_OR_EMPTY.getMask()));
		assertEquals("100", Integer.toBinaryString(Checks.NOT_NULL.getMask()));
	}

	@Test
	public void testBinaryOps() {
		LOG.info("Running testBinaryOps");
		int nullMask = Checks.NULL.getMask();
		int notNullMask = Checks.NOT_NULL.getMask();
		int emptyMask = Checks.ZERO_OR_EMPTY.getMask();
		assertEquals(nullMask, nullMask & 3);
		assertNotSame(nullMask, nullMask & 2);
		assertEquals(notNullMask, notNullMask & 5);
		assertEquals(emptyMask, emptyMask & 2);

	}

	@Test
	public void shouldFindMasks() {
		assertTrue(hasMask(7, NOT_NULL));
		assertTrue(hasMask(7, NULL));
		assertTrue(hasMask(7, ZERO_OR_EMPTY));
		assertFalse(hasMask(1, NOT_NULL));
		assertTrue(hasMask(1, NULL));
	}
}
