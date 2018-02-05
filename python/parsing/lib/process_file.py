
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

  print("Processed {} out of {} lines (skipped {}, parsed {})".format(
    stats['nb_parsed'] + stats['nb_skipped'],
    stats['nb_read'],
    stats['nb_skipped'],
    stats['nb_parsed'],
  ))
  print("Found {} nodes ({} roots, {} tails)".format(
    stats['nb_nodes'],
    stats['nb_roots'],
    stats['nb_tails'],
  ))
finally:
  file.close()

