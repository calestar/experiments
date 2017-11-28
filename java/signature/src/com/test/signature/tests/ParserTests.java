package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test.signature.Node;
import com.test.signature.Parser;

import junit.framework.AssertionFailedError;

class ParserTests {

	@Test
	void test_typename() {
		Parser parser = new Parser();
		try {
			Node root = parser.parse("String", false);
			assertEquals("String", root.getType().getTypename());
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_full_type() {
		Parser parser = new Parser();
		try {
			Node root = parser.parse("java.lang.String", false);
			assertEquals("java.lang.String", root.getType().getTypename());
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_simple_generic() {
		Parser parser = new Parser();
		try {
			Node root = parser.parse("ArrayList<String>", true);
			assertEquals("ArrayList", root.getType().getTypename());
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}
}
