#!/usr/bin/env bash

bindgen \
  --header include/hidapi.h \
  --package libhidapi \
  --flavour scala-native05 \
  --scala \
  --out ./bindings.scala
