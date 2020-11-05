# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

from . import core

from parsing.graph import Node

import unittest

class NodeTests(unittest.TestCase):
  ##### Helpers
  def assertNodeEqual(self, node1, node2):
    self.assertTrue(node1 == node2)
    self.assertTrue(node2 == node1)
    self.assertFalse(node1 != node2)
    self.assertFalse(node2 != node1)

  def assertNodeNotEqual(self, node1, node2):
    self.assertFalse(node1 == node2)
    self.assertFalse(node2 == node1)
    self.assertTrue(node1 != node2)
    self.assertTrue(node2 != node1)

  ##### Tests
  def test_equals_same_no_parents_no_childs(self):
    node1 = Node('1')
    node2 = Node('1')

    self.assertNodeEqual(node1, node2)

  def test_equals_notsame_no_parents_no_childs(self):
    node1 = Node('1')
    node2 = Node('2')

    self.assertNodeNotEqual(node1, node2)

  def test_equal_same(self):
    parent1 = Node('1')
    parent2 = Node('1')
    node1 = Node('2')
    node2 = Node('2')
    child1 = Node('3')
    child2 = Node('3')

    Node.link(parent1, node1)
    Node.link(parent2, node2)
    Node.link(node1, child1)
    Node.link(node2, child2)

    self.assertNodeEqual(parent1, parent2)
    self.assertNodeEqual(node1, node2)
    self.assertNodeEqual(child1, child2)

  def test_equal_notsame_parent(self):
    parent1 = Node('1')
    parent2 = Node('3')
    node1 = Node('2')
    node2 = Node('2')

    Node.link(parent1, node1)
    Node.link(parent2, node2)

    self.assertNodeNotEqual(parent1, parent2)
    self.assertNodeNotEqual(node1, node2)

  def test_equal_notsame_child(self):
    node1 = Node('2')
    node2 = Node('2')
    child1 = Node('1')
    child2 = Node('3')

    Node.link(node1, child1)
    Node.link(node2, child2)

    self.assertNodeNotEqual(node1, node2)
    self.assertNodeNotEqual(child1, child2)

  def test_feature(self):
    node = Node('x')
    self.assertFalse(node.has_feature('bloop'))
    node.add_feature('bloop')
    self.assertTrue(node.has_feature('bloop'))

    with self.assertRaises(AssertionError):
      node.add_feature('bloop')

    self.assertTrue(node.has_feature('bloop'))
