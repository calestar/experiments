/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.test.signature.Input;

class InputTests {

	@Test
	void test_javasrc_single_token() {
		Input input = new Input("String", false);
		assertNotNull(input.peek());
		assertEquals("String", input.peek().getChars());
		assertEquals("String", input.consume().getChars());
		assertNull(input.peek());
		assertFalse(input.hasData());
	}

	@Test
	void test_javanotation_single_token() {
		Input input = new Input("String", false);
		assertNotNull(input.peek());
		assertEquals("String", input.peek().getChars());
		assertEquals("String", input.consume().getChars());
		assertNull(input.peek());
		assertFalse(input.hasData());
	}
}
