#!/usr/bin/env bash

scala-cli package . \
  --native-linking "-L$(pwd)" \
  --native-linking '-lhidapi' \
  --output run \
  --native \
  --force
