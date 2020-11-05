# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

from . import core

from parsing.parser import (
  Parser,
)

import unittest

class NodeLineParserTests(unittest.TestCase):
  def test_bad_basis(self):
    parser = Parser()
    content = parser.parse_line("9374a : NODE_1952482365_2914[...,...]")
    self.assertIsNone(content)

  def test_no_indent(self):
    parser = Parser()
    parsed = parser.parse_line("NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(0, indent)

  def test_single_indent_arrow(self):
    parser = Parser()
    parsed = parser.parse_line("\t->NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(1, indent)

  def test_single_indent_spaces(self):
    parser = Parser()
    parsed = parser.parse_line("\t  NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(1, indent)

  def test_multiple_indent_arrow(self):
    parser = Parser()
    parsed = parser.parse_line("\t->\t->NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(2, indent)

  def test_multiple_indent_mixed(self):
    parser = Parser()
    parsed = parser.parse_line("\t  \t->NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(2, indent)

  def test_elipsis(self):
    parser = Parser()
    parsed = parser.parse_line("NODE_1952482365_2914[...,...]")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual('elipsis', node.role())
    self.assertEqual('node', node.line_type())
    self.assertEqual('NODE_1952482365_2914[...,...]', node.line())
