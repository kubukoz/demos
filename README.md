# Setup

## Prerequisites

Initialize git submodules:

```sh
git submodule update --init --recursive
```

## Building

Before the first build (and after cleaning), generate the Makefile with CMake:

```sh
cd game && cmake .
```

Then build the game via sbt:

```sh
sbt playdateBuild
```

or run it with:

```sh
sbt run
```
