# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

import sys

from parsing.parser import Parser
from parsing.graph import Graph

file_paths = [
  sys.argv[1],
  sys.argv[2],
]

files = [
  open(file_paths[0]),
  open(file_paths[1]),
]

print ("Comparing '{}' and '{}'".format(
  file_paths[0],
  file_paths[1],
))


# Helper
def parse(file):
  parser = Parser()
  graph = Graph()
  stats = graph.build(parser, file, skip_until="Done building lattice!")
  return graph, stats

# Read both files
graphs = None
try:
  print (" -> Reading first file ...")
  first, stats = parse(files[0])
  print(" ---> Processed {} out of {} lines (skipped {}, parsed {})".format(
    stats['nb_parsed'] + stats['nb_skipped'],
    stats['nb_read'],
    stats['nb_skipped'],
    stats['nb_parsed'],
  ))
  print(" ---> Found {} nodes ({} roots, {} tails)".format(
    stats['nb_nodes'],
    stats['nb_roots'],
    stats['nb_tails'],
  ))

  print (" -> Reading second file ...")
  second, stats = parse(files[1])
  print(" ---> Processed {} out of {} lines (skipped {}, parsed {})".format(
    stats['nb_parsed'] + stats['nb_skipped'],
    stats['nb_read'],
    stats['nb_skipped'],
    stats['nb_parsed'],
  ))
  print(" ---> Found {} nodes ({} roots, {} tails)".format(
    stats['nb_nodes'],
    stats['nb_roots'],
    stats['nb_tails'],
  ))

  graphs = [first, second]

finally:
  files[0].close()
  files[1].close()

# Compare !
different_childs = []
different_parents = []

def on_difference(nodeid, node_before, node_after):
  if node_before.childs().keys() != node_after.childs().keys():
    different_childs.append(nodeid)
  if node_before.parents().keys() != node_after.parents().keys():
    different_parents.append(nodeid)

stats, only_before, only_after, equal_in_both = Graph.compare(graphs[0], graphs[1], on_difference)
print ("Found {} nodes only in the first file".format(stats['nb_nodes_only_before']))
print ("Found {} nodes only in the second file".format(stats['nb_nodes_only_after']))
print ("Found {} nodes in common between both files".format(stats['nb_nodes_in_common']))
print ("Found {} common nodes with a difference in parents definition".format(len(different_parents)))
print ("Found {} common nodes with a difference in childs definition".format(len(different_childs)))
