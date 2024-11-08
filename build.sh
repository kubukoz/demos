#!/usr/bin/env bash

scala-cli package . --resource-dir resources -f -o demo --native-image
