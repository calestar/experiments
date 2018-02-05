

class Node(object):
  def __init__(self, nodeid, extent=None, intent=None):
    self._nodeid = nodeid
    self._extent = extent
    self._intent = intent
    self._parents = {}
    self._childs = {}

  @staticmethod
  def link(parent, child):
    assert not parent.has_child(child)
    assert not child.has_parent(parent)
    parent._childs[child.nodeid()] = child
    child._parents[parent.nodeid()] = parent

  def parents(self):
    return self._parents

  def childs(self):
    return self._childs

  def nodeid(self):
    return self._nodeid

  def has_child(self, nodeid):
    return self.find_child(nodeid) != None

  def find_child(self, nodeid):
    return self._childs.get(nodeid, None)

  def has_parent(self, nodeid):
    return self.find_parent(nodeid) != None

  def find_parent(self, nodeid):
    return self._parents.get(nodeid, None)


class BuildContext(object):
  def __init__(self, graph):
    self._stack = []
    self._graph = graph

  def indent(self):
    return len(self._stack) - 1

  def find_node(self, nodeid):
    return self._graph.nodes().get(nodeid, None)

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

  def nodes(self):
    return self._nodes

  def roots(self):
    return self._roots

  def tails(self):
    return self._tails

  def build(self, parser, file, skip_until=None):
    self._context = BuildContext(self)

    # Build the graph
    stats = parser.parse_file(
      file,
      skip_until=skip_until,
      on_parsed_line=self.on_parsed_line,
    )

    # Extract some stuff ...
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
    pass

