package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.test.signature.Node;
import com.test.signature.PrettyPrinter;
import com.test.signature.Type;

class PrettyPrinterTests {

	@Test
	void test_non_root() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);
		Type child_type = new Type("child");
		Node child = new Node(child_type, parent);

		assertThrows(RuntimeException.class, () -> {
			printer.visit(child);
		});
	}

	@Test
	void test_simple_type() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		printer.visit(parent);

		assertEquals("parent", printer.getOutput());
	}

	@Test
	void test_generic_one_child() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type child_type = new Type("child");
		Node child = new Node(child_type, parent);

		printer.visit(parent);

		assertEquals("parent<child>", printer.getOutput());
	}

	@Test
	void test_generic_two_child() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("child1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("child2");
		Node child2 = new Node(child2_type, parent);

		printer.visit(parent);

		assertEquals("parent<child1,child2>", printer.getOutput());
	}

	@Test
	void test_generic_three_child() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("child1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("child2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("child3");
		Node child3 = new Node(child3_type, parent);

		printer.visit(parent);

		assertEquals("parent<child1,child2,child3>", printer.getOutput());
	}

	@Test
	void test_generic_complex() {
		PrettyPrinter printer = new PrettyPrinter();

		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type child1_type = new Type("child1");
		Node child1 = new Node(child1_type, parent);

		Type child2_type = new Type("child2");
		Node child2 = new Node(child2_type, parent);

		Type child3_type = new Type("child3");
		Node child3 = new Node(child3_type, child2);

		Type child4_type = new Type("child4");
		Node child4 = new Node(child4_type, child2);

		printer.visit(parent);

		assertEquals("parent<child1,child2<child3,child4>>", printer.getOutput());
	}
}
