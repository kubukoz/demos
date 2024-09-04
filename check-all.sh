#!/bin/bash

set -e

for scala_version in 2.12 2.13 3
do
  SCALA_VERSION=$scala_version ./check.sh
done
