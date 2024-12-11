#!/usr/bin/env bash

scala-cli package . \
  --native-linking '-lhidapi' \
  --native-linking "-L$(pwd)" \
  --output run \
  --native \
  --force
