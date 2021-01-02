# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

import re as regex


class Line(object):
  def __init__(self, line_type, line):
    self._line_type = line_type
    self._line = line

  def line_type(self):
    return self._line_type

  def line(self):
    return self._line

  def should_skip(self):
    return False


class NodeLine(Line):
  def __init__(self, role, nodeid, line):
    super(NodeLine, self).__init__('node', line)
    self._role = role
    self._nodeid = nodeid

  def role(self):
    return self._role

  def nodeid(self):
    return self._nodeid


class DummyLine(Line):
  def __init__(self, line):
    super(DummyLine, self).__init__('dummy', line)

  def should_skip(self):
    return True


class FeatureLine(Line):
  def __init__(self, features, line):
    super(FeatureLine, self).__init__('feature', line)
    self._features = features

  def features(self):
    return self._features


class Parser(object):
  INDENT_REGEX = r"(?P<indent>(\t(->|  ))*)"

  NODE_BASIS = regex.compile(INDENT_REGEX + r"(?P<nodeid>NODE_-?\d+_-?\d+)\[(?P<node_content>.*)\]$")
  NODE_CONTENT = regex.compile(r"\[(?P<extent>.*)\],\[(?P<intent>.*)\]$")
  FEATURE_BASIS = regex.compile(INDENT_REGEX + r"FEATURE TYPE: *(?P<features>.*)$")

  CHILDREN_BASIS = regex.compile(INDENT_REGEX + r"ITS CHILDREN:=================$")
  UNKNOWN_ITYPE_BASIS = regex.compile(INDENT_REGEX + r"Could not find IType for .*$")

  IGNORED = {
    "Printing candidate nodes",
    "Done printing candidate nodes!",
    "Done printing lattice!",
    "Using complex purge",
    "Printing lattice after purging extents",
    "Done printing lattice!",
    "true",
  }

  def try_parse_node_line(self, line):
    match = regex.match(self.NODE_BASIS, line)
    if not match:
      return None

    indent_level = len(match.group("indent")) / len("\t->")
    nodeid = match.group("nodeid")
    node_content = match.group("node_content")

    # Test for elipsis
    if node_content == "...,...":
      return indent_level, NodeLine("elipsis", nodeid, line)

    # Split content
    content = regex.match(self.NODE_CONTENT, node_content)
    if content:
      return indent_level, NodeLine("content", nodeid, line)

    return None

  def try_parse_feature_line(self, line):
    match = regex.match(self.FEATURE_BASIS, line)

    if not match:
      return None

    indent_level = len(match.group("indent")) / len("\t->")
    features_str = match.group("features")
    features = []

    # Test for AdHoc feature
    if features_str == "## ADHOC":
      features.append("adhoc")

    return indent_level, FeatureLine(features, line)

  def try_parse_dummy_line(self, line):
    match = regex.match(self.CHILDREN_BASIS, line)
    if match:
      indent_level = len(match.group("indent")) / len("\t->")
      return indent_level, DummyLine(line)
    match = regex.match(self.UNKNOWN_ITYPE_BASIS, line)
    if match:
      indent_level = len(match.group("indent")) / len("\t->")
      return indent_level, DummyLine(line)
    if line in self.IGNORED:
      return 0, DummyLine(line)

    return None

  def parse_line(self, line):
    parsed = self.try_parse_node_line(line)
    parsed = parsed or self.try_parse_feature_line(line)
    parsed = parsed or self.try_parse_dummy_line(line)
    return parsed

  def parse_file(self, file, skip_until=None, on_parsed_line=None):
    nb_read = 0
    nb_parsed = 0
    nb_skipped = 0
    try:
      for linenb, line in enumerate(file, start=1):
        # Clean end of line chars
        line = line.rstrip()

        # Process the line
        nb_read += 1
        if skip_until:
          nb_skipped += 1
          if line == skip_until:
            skip_until = None
          continue
        parsed = self.parse_line(line)
        if parsed:
          indent, content = parsed
          if content.should_skip():
            nb_skipped += 1
          else:
            nb_parsed += 1
            if on_parsed_line:
              on_parsed_line(indent, content)
    except:
      print("Error while processing line #{}: '{}'".format(linenb, line))
      raise

    stats = {
      'nb_read': nb_read,
      'nb_parsed': nb_parsed,
      'nb_skipped': nb_skipped,
    }
    return stats
