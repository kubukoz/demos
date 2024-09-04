#!/bin/bash

SCALA_VERSION=${SCALA_VERSION:-3}
rm -rf out

set -e

echo "Compiling with Scala $SCALA_VERSION"

echo "OLD"
scala-cli compile hellonew --output-directory out/old --scala $SCALA_VERSION
JAVAP_BEFORE=$(cd out/old && javap hellonew.MyHello)

echo "NEW"
scala-cli compile hellonew2 --output-directory out/new --scala $SCALA_VERSION
JAVAP_AFTER=$(cd out/new && javap hellonew.MyHello)

set +e

delta --paging never <(echo "$JAVAP_BEFORE") <(echo "$JAVAP_AFTER")

cs launch io.github.jeremyrsmith::mima-cli:0.2.0 -- out/old out/new -v -j
echo "Bincompat successful for Scala $SCALA_VERSION"

