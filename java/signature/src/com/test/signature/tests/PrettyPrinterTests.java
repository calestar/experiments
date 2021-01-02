/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.test.signature.Node;
import com.test.signature.Parser;
import com.test.signature.PrettyPrinter;
import com.test.signature.Type;

import junit.framework.AssertionFailedError;

class PrettyPrinterTests {

	@Test
	void test_non_root() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);
		Type child_type = new Type("Qchild");
		Node child = new Node(child_type, parent);

		assertThrows(RuntimeException.class, () -> {
			printer.visit(child);
		});
	}

	@Test
	void test_simple_type() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_generic_one_child() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child_type = new Type("Qchild");
		Node child = new Node(child_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent<Qchild>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_generic_two_child() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent<Qchild1,Qchild2>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_generic_three_child() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent<Qchild1,Qchild2,Qchild3>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_generic_complex() {
		PrettyPrinter printer = new PrettyPrinter(false, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("Qchild4");
		Node child4 = new Node(child4_type, child2);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent<Qchild1,Qchild2<Qchild3,Qchild4>>", printer.getOutput());
	}

	@Test
	void test_clean_simple_type() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_clean_generic_one_child() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child_type = new Type("Qchild");
		Node child = new Node(child_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent<child>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_clean_generic_two_child() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent<child1,child2>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_clean_generic_three_child() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, parent);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent<child1,child2,child3>", printer.getOutput());
	}

	@Test
	@SuppressWarnings("unused")
	void test_clean_generic_complex() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("Qchild4");
		Node child4 = new Node(child4_type, child2);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent<child1,child2<child3,child4>>", printer.getOutput());

		Parser parser = new Parser(false);
		Node node;
		try {
			node = parser.parse(printer.getOutput(), true);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertNotNull(node);
	}

	@Test
	@SuppressWarnings("unused")
	void test_sigformat_generic_complex() {
		PrettyPrinter printer = new PrettyPrinter(false, true);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("Qchild4");
		Node child4 = new Node(child4_type, child2);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("Qparent<Qchild1;Qchild2<Qchild3;Qchild4;>;>", printer.getOutput());

		Parser parser = new Parser(false);
		Node node;
		try {
			node = parser.parse(printer.getOutput(), false);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertNotNull(node);
	}

	@Test
	@SuppressWarnings("unused")
	void test_clean_sigformat_generic_complex() {
		PrettyPrinter printer = new PrettyPrinter(true, true);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("Qchild4");
		Node child4 = new Node(child4_type, child2);

		try {
			printer.visit(parent);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertEquals("parent<child1;child2<child3;child4;>;>", printer.getOutput());

		Parser parser = new Parser(false);
		Node node;
		try {
			node = parser.parse(printer.getOutput(), false);
		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}

		assertNotNull(node);
	}

	@Test
	@SuppressWarnings("unused")
	void test_reuse() {
		PrettyPrinter printer = new PrettyPrinter(true, false);

		Type parent_type = new Type("Qparent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("Qchild1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("Qchild2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("Qchild3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("Qchild4");
		Node child4 = new Node(child4_type, child2);

		try {
			printer.visit(parent);
			assertEquals("parent<child1,child2<child3,child4>>", printer.getOutput());

			printer.visit(parent);
			assertEquals("parent<child1,child2<child3,child4>>", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}
}
