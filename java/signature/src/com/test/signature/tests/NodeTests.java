/***
 * Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.test.signature.Node;
import com.test.signature.Type;

class NodeTests {

	@Test
	void test_constructor_no_parent() {
		Type type = new Type("test");
		Node node = new Node(type);

		assertEquals(0, node.getChilds().size());
		assertNull(node.getParent());
		assertEquals(type, node.getType());
		assertTrue(node.isRoot());
	}

	@Test
	void test_constructor_with_parent() {
		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);
		assertEquals(0, parent.getChilds().size());

		Type type = new Type("test");
		Node node = new Node(type, parent);

		assertEquals(0, node.getChilds().size());
		assertEquals(parent, node.getParent());
		assertEquals(type, node.getType());

		assertEquals(1, parent.getChilds().size());
		assertEquals(node, parent.getChilds().get(0));

		assertFalse(node.isRoot());
		assertTrue(parent.isRoot());
	}

	@Test
	void test_childinfo_no_parent() {
		Type type = new Type("test");
		Node node = new Node(type);
		assertFalse(node.isParentFirstChild());
	}

	@Test
	void test_childinfo_only_child() {
		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type type = new Type("test");
		Node node = new Node(type, parent);

		assertEquals(1, parent.getChilds().size());
		assertTrue(node.isParentFirstChild());
		assertTrue(node.isParentLastChild());
	}

	@Test
	void test_childinfo_two_childs() {
		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type first_child_type = new Type("test_first");
		Node first_child_node = new Node(first_child_type, parent);
		Type second_child_type = new Type("test_second");
		Node second_child_node = new Node(second_child_type, parent);

		assertEquals(2, parent.getChilds().size());
		assertTrue(first_child_node.isParentFirstChild());
		assertFalse(first_child_node.isParentLastChild());
		assertFalse(second_child_node.isParentFirstChild());
		assertTrue(second_child_node.isParentLastChild());
	}

	@Test
	void test_childinfo_three_childs() {
		Type parent_type = new Type("parent");
		Node parent = new Node(parent_type);

		Type first_child_type = new Type("test_first");
		Node first_child_node = new Node(first_child_type, parent);
		Type second_child_type = new Type("test_second");
		Node second_child_node = new Node(second_child_type, parent);
		Type third_child_type = new Type("test_second");
		Node third_child_node = new Node(third_child_type, parent);

		assertEquals(3, parent.getChilds().size());
		assertTrue(first_child_node.isParentFirstChild());
		assertFalse(first_child_node.isParentLastChild());
		assertFalse(second_child_node.isParentFirstChild());
		assertFalse(second_child_node.isParentLastChild());
		assertFalse(third_child_node.isParentFirstChild());
		assertTrue(third_child_node.isParentLastChild());

	}
}
