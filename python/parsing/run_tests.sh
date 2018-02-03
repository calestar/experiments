#!/bin/bash


cwd=$(dirname $(readlink -f $0))
pushd .
cd ${cwd}/lib
nosetests --no-byte-compile ../tests

echo "-------------------------------------------"
python process_file.py ${cwd}/before.txt || true

echo "-------------------------------------------"
python process_file.py ${cwd}/after.txt || true

popd
