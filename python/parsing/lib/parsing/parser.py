
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


class Node(Line):
  def __init__(self, role, line):
    super(Node, self).__init__('node', line)
    self._role = role

  def role(self):
    return self._role


class Dummy(Line):
  def __init__(self, line):
    super(Dummy, self).__init__('dummy', line)

  def should_skip(self):
    return True


class Feature(Line):
  def __init__(self, features, line):
    super(Feature, self).__init__('feature', line)
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
      return indent_level, Node("elipsis", line)

    # Split content
    content = regex.match(self.NODE_CONTENT, node_content)
    if content:
      return indent_level, Node("content", line)

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

    return indent_level, Feature(features, line)

  def try_parse_dummy_line(self, line):
    match = regex.match(self.CHILDREN_BASIS, line)
    if match:
      indent_level = len(match.group("indent")) / len("\t->")
      return indent_level, Dummy(line)
    match = regex.match(self.UNKNOWN_ITYPE_BASIS, line)
    if match:
      indent_level = len(match.group("indent")) / len("\t->")
      return indent_level, Dummy(line)
    if line in self.IGNORED:
      return 0, Dummy(line)

    return None

  def parse_line(self, line):
    parsed = self.try_parse_node_line(line)
    if parsed:
      return parsed
    parsed = self.try_parse_feature_line(line)
    if parsed:
      return parsed
    parsed = self.try_parse_dummy_line(line)
    if parsed:
      return parsed
    return None

  def parse_file(self, file, skip_until=None):
    nb_read = 0
    nb_parsed = 0
    nb_skipped = 0
    for line in file:
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

    return nb_read, nb_parsed, nb_skipped

