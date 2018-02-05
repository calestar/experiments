from . import core

from parsing.parser import Parser
from parsing.graph import Graph

import unittest

class GraphTests(unittest.TestCase):
  def test_1root_1child(self):
    lines = [
      "NODE_1_1[...,...]",
      "\t->NODE_1_2[...,...]",
    ]
    parser = Parser()
    graph = Graph()

    graph.build(parser, lines)

    self.assertEqual(2, len(graph.nodes()))
    self.assertEqual(1, len(graph.roots()))
    self.assertEqual(1, len(graph.tails()))

  def test_2root_1child(self):
    lines = [
      "NODE_1_1[...,...]",
      "\t->NODE_1_2[...,...]",
      "NODE_2_1[...,...]",
    ]
    parser = Parser()
    graph = Graph()

    graph.build(parser, lines)

    self.assertEqual(3, len(graph.nodes()))
    self.assertEqual(2, len(graph.roots()))
    self.assertEqual(2, len(graph.tails()))

  def test_2root_3child(self):
    lines = [
      "NODE_1_1[...,...]",
      "\t->NODE_1_2[...,...]",
      "\t->\t  NODE_1_3[...,...]",
      "\t->NODE_1_4[...,...]",
      "NODE_2_1[...,...]",
    ]
    parser = Parser()
    graph = Graph()

    graph.build(parser, lines)

    self.assertEqual(5, len(graph.nodes()))
    self.assertEqual(2, len(graph.roots()))
    self.assertEqual(3, len(graph.tails()))

  def test_reuse(self):
    lines = [
      "NODE_1_1[...,...]",
      "\t->NODE_1_2[...,...]",
      "NODE_2_1[...,...]",
      "\t->NODE_1_2[...,...]",
    ]
    parser = Parser()
    graph = Graph()

    graph.build(parser, lines)

    self.assertEqual(3, len(graph.nodes()))
    self.assertEqual(2, len(graph.roots()))
    self.assertEqual(1, len(graph.tails()))

