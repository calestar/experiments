import sys, os

here = os.path.abspath(os.path.dirname(__file__))
lib_root = os.path.join(here, "..", "lib")

sys.path = [lib_root] + sys.path

