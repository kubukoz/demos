#!/bin/bash
set -e

# Useful for `git bisect run` in scala-native. When merging main into your branch,
# do the bisect on a branch with the new commits cherry-picked on top of yours - otherwise PD-specific changes won't be in the bisected commits.

cd ~/projects/scala-native
nix develop --command sbt "clean;publish-local-dev 3.3.1"
cd ~/projects/native-demos
nix develop --command sbt clean nativeLink
