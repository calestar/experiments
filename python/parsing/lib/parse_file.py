
import sys

from parsing.parser import Parser
from parsing.graph import Graph

file_path = sys.argv[1]
file = open(file_path)

print ("Reading from '{}'".format(file_path))

parser = Parser()
graph = Graph()
try:
  stats = graph.build(parser, file, skip_until="Done building lattice!")

  print(" -> Processed {} out of {} lines (skipped {}, parsed {})".format(
    stats['nb_parsed'] + stats['nb_skipped'],
    stats['nb_read'],
    stats['nb_skipped'],
    stats['nb_parsed'],
  ))
  print(" -> Found {} nodes ({} roots, {} tails)".format(
    stats['nb_nodes'],
    stats['nb_roots'],
    stats['nb_tails'],
  ))
finally:
  file.close()

# Purge for fun
def selector(node):
  return node.has_feature('adhoc') or node.is_root()

print ("Purging ...")
purged_graph, stats = graph.purge(selector)

# Compare !
different_childs = []
different_parents = []

def on_difference(nodeid, node_before, node_after):
  if node_before.childs().keys() != node_after.childs().keys():
    different_childs.append(nodeid)
  if node_before.parents().keys() != node_after.parents().keys():
    different_parents.append(nodeid)

stats, only_before, only_after, equal_in_both = Graph.compare(graph, purged_graph, on_difference)

print ("Comparing original graph with purged graph")
print (" -> Found {} nodes only in the first file".format(stats['nb_nodes_only_before']))
print (" -> Found {} nodes only in the second file".format(stats['nb_nodes_only_after']))
print (" -> Found {} nodes in common between both files".format(stats['nb_nodes_in_common']))
print (" -> Found {} common nodes with a difference in parents definition".format(len(different_parents)))
print (" -> Found {} common nodes with a difference in childs definition".format(len(different_childs)))

