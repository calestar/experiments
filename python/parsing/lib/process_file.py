
import sys

from parsing.parser import Parser

file_path = sys.argv[1]
file = open(file_path)

print ("Reading from '{}'".format(file_path))

parser = Parser()
try:
  nb_read, nb_parsed, nb_skipped = parser.parse_file(file, skip_until="Done building lattice!")

  print ("Processed {} out of {} lines (skipped {}, parsed {})".format(
    nb_parsed + nb_skipped,
    nb_read,
    nb_skipped,
    nb_parsed,
  ))
finally:
  file.close()

