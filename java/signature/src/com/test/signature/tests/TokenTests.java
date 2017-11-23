package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.test.signature.Token;

class TokenTests {

	@Test
	void test_chars() {
		Token token = new Token("SimpleToken");
		assertEquals("SimpleToken", token.getChars());
	}

	@Test
	void test_typename() {
		Token token = new Token("SimpleTypename");
		assertTrue(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_array() {
		Token token = new Token("[");
		assertFalse(token.isTypeName());
		assertTrue(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_generic_start() {
		Token token = new Token("<");
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertTrue(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_generic_separator() {
		Token token = new Token(";");
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertTrue(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_generic_stop() {
		Token token = new Token(">");
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertTrue(token.isGenericStop());
	}
}
