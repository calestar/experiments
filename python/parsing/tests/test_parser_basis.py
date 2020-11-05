# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

from . import core

from parsing.parser import (
  Parser,
)

import unittest

class BasicParserTests(unittest.TestCase):
  def test_parse_file_stats(self):
    lines = [
      "true", "Using complex purge",
      "bloop", "bleep",
      "FEATURE TYPE:  ## ADHOC",
    ]
    parser = Parser()

    stats = parser.parse_file(lines)

    self.assertEqual(5, stats['nb_read'])
    self.assertEqual(1, stats['nb_parsed'])
    self.assertEqual(2, stats['nb_skipped'])

  def test_parse_file_skip(self):
    lines = [
      "FEATURE TYPE:  ## ADHOC",
      "true", "Using complex purge",
      "bloop", "bleep",
      "FEATURE TYPE:  ## ADHOC",
    ]
    parser = Parser()

    stats = parser.parse_file(lines, skip_until="bloop")

    self.assertEqual(6, stats['nb_read'])
    self.assertEqual(1, stats['nb_parsed'])
    self.assertEqual(4, stats['nb_skipped'])

  def test_on_parsed_line(self):
    lines = [
      "FEATURE TYPE:  ## ADHOC",
      "true",
      "bleep",
    ]
    parser = Parser()

    parsed_lines = []
    def on_parsed_line(indent, content):
      parsed_lines.append(content.line())

    parser.parse_file(lines, on_parsed_line=on_parsed_line)

    self.assertEqual(["FEATURE TYPE:  ## ADHOC"], parsed_lines)
