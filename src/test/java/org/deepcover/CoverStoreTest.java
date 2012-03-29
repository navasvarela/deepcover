package org.deepcover;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.objectweb.asm.Type;

public class CoverStoreTest {

	@Test
	public void shouldFindCorrectArgTypesForString() {
		assertEquals(ArgTypes.STRING,
				CoverStore.parseType(Type.getType(String.class)));
	}

	@Test
	public void shouldFindCorrectArgTypesForObject() {
		assertEquals(ArgTypes.OBJECT,
				CoverStore.parseType(Type.getType(Object.class)));
	}

	@Test
	public void shouldFindCorrectArgTypesForNumber() {
		assertEquals(ArgTypes.NUMBER,
				CoverStore.parseType(Type.getType(Long.class)));
		assertEquals(ArgTypes.NUMBER,
				CoverStore.parseType(Type.getType(Integer.class)));
		assertEquals(ArgTypes.NUMBER,
				CoverStore.parseType(Type.getType(Double.class)));
		assertEquals(ArgTypes.NUMBER,
				CoverStore.parseType(Type.getType(Float.class)));

	}

	@Test
	public void shouldSerialize() {

	}

}
