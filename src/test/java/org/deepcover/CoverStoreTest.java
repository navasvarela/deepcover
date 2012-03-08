package org.deepcover;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CoverStoreTest {

	CoverStore store = new CoverStore();
	
	@Test
	public void shouldAddClass() {
		// act
		store.addClass(DCAgent.class);
		// assert
		assertTrue(store.toString().contains("[methodName=premain, arguments={inst=0, args=0}]"));
	}
}
