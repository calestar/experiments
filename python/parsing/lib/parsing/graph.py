
from collections import deque

class Node(object):
  def __init__(self, nodeid, extent=None, intent=None):
    self._nodeid = nodeid
    self._extent = extent
    self._intent = intent
    self._parents = {}
    self._childs = {}
    self._features = set()

  @staticmethod
  def link(parent, child):
    assert not parent.has_child(child.nodeid())
    assert not child.has_parent(parent.nodeid())
    parent._childs[child.nodeid()] = child
    child._parents[parent.nodeid()] = parent

  def __eq__(self, other):
    if isinstance(other, Node):
      return all([
        self._nodeid == other._nodeid,
        self._parents.keys() == other._parents.keys(),
        self._childs.keys() == other._childs.keys(),
        self._features == other._features,
      ])
    return NotImplemented

  def __ne__(self, other):
    result = self.__eq__(other)
    if result is NotImplemented:
      return result
    return not result

  def parents(self):
    return self._parents

  def childs(self):
    return self._childs

  def nodeid(self):
    return self._nodeid

  def features(self):
    return self._features

  def is_root(self):
    return not self._parents

  def has_feature(self, feature):
    return feature in self._features

  def add_feature(self, feature):
    assert not self.has_feature(feature)
    self._features.add(feature)

  def has_child(self, nodeid):
    return self.find_child(nodeid) != None

  def find_child(self, nodeid):
    return self._childs.get(nodeid, None)

  def has_parent(self, nodeid):
    return self.find_parent(nodeid) != None

  def find_parent(self, nodeid):
    return self._parents.get(nodeid, None)

  def output(self, processed, indent=''):
    lines = [
      "{}{}{}".format(
        indent,
        "->" if indent else "",
        self.nodeid(),
      )
    ]

    for childid in self._childs:
      if childid not in processed:
        processed.add(childid)
        lines.extend(
          self._childs[childid].output(
            processed,
            "  " + indent,
          )
        )
      else:
        lines.append(
          "{}  ->{}[...]".format(
            indent,
            childid,
          )
        )

    return lines

class BuildContext(object):
  def __init__(self, graph):
    self._stack = []
    self._graph = graph

  def indent(self):
    return len(self._stack) - 1

  def find_node(self, nodeid):
    return self._graph.find_node(nodeid)

  def add_or_get_node(self, nodeid):
    node = self.find_node(nodeid)
    if not node:
      assert self._graph._nodes.get(nodeid, None) is None
      node = Node(nodeid)
      self._graph._nodes[nodeid] = node
    if self._stack and not self._stack[-1].has_child(nodeid):
      Node.link(self._stack[-1], node)
    return node

  def exit_to(self, target_indent):
    delta = self.indent() - target_indent
    while delta > 0:
      assert self._stack
      self._stack.pop()
      delta -= 1

  def enter(self, node):
    self._stack.append(node)


class Graph(object):
  def __init__(self):
    self._nodes = {}
    self._roots = {}
    self._tails = {}
    self._context = None

  def output(self):
    lines = []
    processed = set()
    for root in self._roots.itervalues():
      lines.extend(root.output(processed))

    return "\n".join(lines)

  def nodes(self):
    return self._nodes

  def find_node(self, nodeid):
    return self._nodes.get(nodeid, None)

  def roots(self):
    return self._roots

  def tails(self):
    return self._tails

  def build_stats(self):
    self._roots = {
      node.nodeid(): node
      for node in self._nodes.itervalues()
      if not node.parents()
    }
    self._tails = {
      node.nodeid(): node
      for node in self._nodes.itervalues()
      if not node.childs()
    }

  def build(self, parser, file, skip_until=None):
    self._context = BuildContext(self)

    # Build the graph
    stats = parser.parse_file(
      file,
      skip_until=skip_until,
      on_parsed_line=self.on_parsed_line,
    )

    self.build_stats()
    stats.update({
      'nb_nodes': len(self._nodes),
      'nb_roots': len(self._roots),
      'nb_tails': len(self._tails),
    })

    return stats

  def on_parsed_line(self, indent, content):
    if content.line_type() == "node":
      self.on_parsed_node(indent, content)
    elif content.line_type() == "feature":
      self.on_parsed_feature(indent, content)

  def on_parsed_node(self, indent, content):
    # Make sure we go to the content's parent
    self._context.exit_to(indent - 1)

    # Create or get the node
    node = self._context.add_or_get_node(content.nodeid())

    # Enter the node's context
    self._context.enter(node)

  def on_parsed_feature(self, indent, content):
    if 'adhoc' in content.features():
      current_node = self._context._stack[-1]
      current_node.add_feature('adhoc')

  def purge(self, selector):
    stats = dict()
    done = set()
    todo = deque()
    new_graph = Graph()

    # Start at the roots
    for root in self.roots():
      todo.append((root, None))

    # Work as long as there is work
    while todo:
      nodeid, parentid = todo.popleft()
      node = self.find_node(nodeid)

      if nodeid in done:
        if parentid and not node.has_parent(parentid):
          parent = new_graph.find_node(parentid)
          Node.link(parent, node)
        continue

      done.add(nodeid)

      if selector(node):
        new_node = Node(nodeid)
        if parentid:
          parent = new_graph.find_node(parentid)
          Node.link(parent, new_node)
        new_graph._nodes[nodeid] = new_node
        for childid in node.childs().iterkeys():
          if childid not in done:
            todo.append((childid, nodeid))
          else:
            child = new_graph.find_node(childid)
            if child:
              Node.link(new_node, child)

    new_graph.build_stats()
    stats.update({
    })
    return new_graph, stats

  @staticmethod
  def compare(before, after, on_difference):
    stats = {
      'before' : {
        'nb_nodes': len(before.nodes())
      },
      'after' : {
        'nb_nodes': len(after.nodes())
      }
    }

    nodes_before = set(before.nodes().keys())
    nodes_after = set(after.nodes().keys())

    only_before = nodes_before.difference(nodes_after)
    only_after = nodes_after.difference(nodes_before)
    common = nodes_before.intersection(nodes_after)
    equal_nodes = set()

    for nodeid in common:
      node_before = before.find_node(nodeid)
      node_after = after.find_node(nodeid)

      if node_before == node_after:
        equal_nodes.add(nodeid)
      else:
        on_difference(nodeid, node_before, node_after)

    stats.update({
      'nb_nodes_only_before': len(only_before),
      'nb_nodes_only_after': len(only_after),
      'nb_nodes_in_common': len(common),
      'nb_nodes_equal': len(equal_nodes),
    })
    return (
      stats,
      {
        nodeid: before.find_node(nodeid)
        for nodeid in only_before
      },
      {
        nodeid: after.find_node(nodeid)
        for nodeid in only_after
      },
      {
        nodeid: after.find_node(nodeid)
        for nodeid in equal_nodes
      },
    )

