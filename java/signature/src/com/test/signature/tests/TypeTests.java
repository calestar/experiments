package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.test.signature.Type;

class TypeTests {

	@Test
	void test_constructor_1() {
		Type type = new Type("test");

		assertEquals("test", type.getTypename());
		assertFalse(type.isArray());
	}

	@Test
	void test_constructor_2_array() {
		Type type = new Type("test", true);

		assertEquals("test", type.getTypename());
		assertTrue(type.isArray());
	}

	@Test
	void test_constructor_2_not_array() {
		Type type = new Type("test", false);

		assertEquals("test", type.getTypename());
		assertFalse(type.isArray());
	}

	@Test
	void test_update_typename() {
		Type type = new Type("test");
		assertEquals("test", type.getTypename());

		type.updateTypeName("oops");
		assertEquals("oops", type.getTypename());
	}
}
