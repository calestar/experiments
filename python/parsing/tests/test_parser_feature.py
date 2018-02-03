from . import core

from parsing.parser import (
  Parser,
)

import unittest

class FeatureLineParserTests(unittest.TestCase):
  def test_bad_basis(self):
    parser = Parser()
    content = parser.parse_line("9374aFEATURE TYPE:  ## ADHOC")
    self.assertIsNone(content)

  def test_no_indent(self):
    parser = Parser()
    parsed = parser.parse_line("FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(0, indent)

  def test_single_indent_arrow(self):
    parser = Parser()
    parsed = parser.parse_line("\t->FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(1, indent)

  def test_single_indent_spaces(self):
    parser = Parser()
    parsed = parser.parse_line("\t  FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(1, indent)

  def test_multiple_indent_arrow(self):
    parser = Parser()
    parsed = parser.parse_line("\t->\t->FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(2, indent)

  def test_multiple_indent_mixed(self):
    parser = Parser()
    parsed = parser.parse_line("\t  \t->FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(2, indent)

  def test_adhoc(self):
    parser = Parser()
    parsed = parser.parse_line("FEATURE TYPE:  ## ADHOC")
    self.assertIsNotNone(parsed)

    indent, node = parsed
    self.assertEqual(["adhoc"], node.features())
    self.assertEqual('feature', node.line_type())
    self.assertEqual('FEATURE TYPE:  ## ADHOC', node.line())

