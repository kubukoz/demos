#!/bin/bash

set -euo pipefail

mkdir -p build
mkdir -p build/dep

PLAYDATE_SDK="/Users/kubukoz/Developer/PlaydateSDK"

BASEDIR=$(dirname "$0")
clang -g -g -dynamiclib -rdynamic    -lm -DTARGET_SIMULATOR=1 -DTARGET_EXTENSION=1 -I . -I $PLAYDATE_SDK/C_API \
  -I "$BASEDIR/../lib" \
  -ldemo-out \
  -L "$BASEDIR/../app/.native/target/scala-3.3.1" \
  -Wl,--no-demangle \
  -o "$BASEDIR/build/pdex.dylib" \
  "$BASEDIR/src/main.c" \
  "$PLAYDATE_SDK/C_API/buildsupport/setup.c"

cp "$BASEDIR/build/pdex.dylib" Source
$PLAYDATE_SDK/bin/pdc "$BASEDIR/Source" "$BASEDIR/HelloWorld.pdx"
cp "$BASEDIR/../app/.native/target/scala-3.3.1/libdemo-out.dylib" /usr/local/lib
open "$BASEDIR/HelloWorld.pdx"

