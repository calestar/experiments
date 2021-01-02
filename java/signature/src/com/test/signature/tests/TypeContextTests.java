/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.test.signature.TypeContext;

class TypeContextTests {

	@Test
	void test_empty_resolver() {
		TypeContext resolver = new TypeContext();
		assertNull(resolver.resolveType("anything"));
	}

	@Test
	void test_one_result() {
		TypeContext resolver = new TypeContext();
		resolver.register("something", "x.y.z");
		resolver.register("else", "x.y.z");

		String[][] results = resolver.resolveType("something");
		assertEquals(1, results.length);
		String[] result = results[0];
		assertEquals(2, result.length);
		assertEquals("x.y.z", result[0]);
		assertEquals("something", result[1]);
	}

	@Test
	void test_multiple_result() {
		TypeContext resolver = new TypeContext();
		resolver.register("something", "x.y.z");
		resolver.register("something", "a.b.c");
		resolver.register("else", "x.y.z");

		String[][] results = resolver.resolveType("something");
		assertEquals(2, results.length);
		String[] result = results[0];
		assertEquals(2, result.length);
		assertEquals("x.y.z", result[0]);
		assertEquals("something", result[1]);
		result = results[1];
		assertEquals(2, result.length);
		assertEquals("a.b.c", result[0]);
		assertEquals("something", result[1]);
	}
}
