package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.test.signature.Token;

class TokenTests {

	@Test
	void test_javanotation_chars() {
		Token token = new Token("SimpleToken", false);
		assertEquals("SimpleToken", token.getChars());
	}

	@Test
	void test_javanotation_typename() {
		Token token = new Token("SimpleTypename", false);
		assertTrue(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javanotation_array() {
		Token token = new Token("[", false);
		assertFalse(token.isTypeName());
		assertTrue(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javanotation_generic_start() {
		Token token = new Token("<", false);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertTrue(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javanotation_generic_separator() {
		Token token = new Token(";", false);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertTrue(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javanotation_generic_stop() {
		Token token = new Token(">", false);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertTrue(token.isGenericStop());
	}

	@Test
	void test_javasrc_chars() {
		Token token = new Token("SimpleToken", true);
		assertEquals("SimpleToken", token.getChars());
	}

	@Test
	void test_javasrc_typename() {
		Token token = new Token("SimpleTypename", true);
		assertTrue(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javasrc_array() {
		Token token = new Token("[", true);
		assertFalse(token.isTypeName());
		assertTrue(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javasrc_generic_start() {
		Token token = new Token("<", true);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertTrue(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javasrc_generic_separator() {
		Token token = new Token(",", true);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertTrue(token.isGenericSeparator());
		assertFalse(token.isGenericStop());
	}

	@Test
	void test_javasrc_generic_stop() {
		Token token = new Token(">", true);
		assertFalse(token.isTypeName());
		assertFalse(token.isArray());
		assertFalse(token.isGenericStart());
		assertFalse(token.isGenericSeparator());
		assertTrue(token.isGenericStop());
	}
}
