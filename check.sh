#!/bin/bash

set -e

echo "OLD"
scala-cli compile hellonew --output-directory out/old
(cd out/old && javap hellonew.MyHello)

echo "NEW"
scala-cli compile hellonew2 --output-directory out/new
(cd out/new && javap hellonew.MyHello)

cs launch io.github.jeremyrsmith::mima-cli:0.2.0 -- out/old out/new -v -j
