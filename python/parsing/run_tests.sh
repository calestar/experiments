#!/bin/bash


cwd=$(dirname $(readlink -f $0))
pushd .
cd ${cwd}/lib
nosetests --no-byte-compile ../tests

echo "-------------------------------------------"
python compare_files.py ${cwd}/before.txt ${cwd}/after.txt || true

popd
