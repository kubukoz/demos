# Playdate Scala Native Project

## Overview
This project builds a Playdate game using Scala Native, targeting ARM Cortex-M7 (bare-metal, single-threaded, no OS).

## Key Paths
- **Scala Native fork**: `../scala-native` (`/Users/kubukoz/projects/scala-native`)
- **Game C code / CMake build**: `game/`
- **Game entry point**: `game/main.c` (defines `eventHandler`, PD API forwarders, error logging via `pd_log_error`)
- **CMake links**: `game/CMakeLists.txt` — links `pdcpp_core` + `libroot.a` (the Scala Native static lib)
- **Playdate C++ submodule**: `game/submodules/playdate-cpp`

## Build
- `sbt nativeLink` produces `libroot.a`
- `sbt playdateBuild` copies `libroot.a` into `game/`, runs `make`, produces `game/HelloWorld.pdx`
- `sbt run` deploys to a connected Playdate device

## Scala Native Config (build.sbt)
- Target triple: `arm-none-eabi`
- GC: Immix
- Multithreading: disabled (`withMultithreading(false)`)
- Build target: static library
- Compile flags include `-DTARGET_PLAYDATE=1`, `-DPD_DEBUG=1`
- ARM toolchain: `/Applications/ArmGNUToolchain/13.2.Rel1/arm-none-eabi/`

## Scala Native Fork Modifications (under `TARGET_PLAYDATE` ifdefs)
- **`nativelib/.../gc/immix/MutatorThread.c`**: Stubbed `pthread_self` and `pthread_kill` (no pthreads on Playdate)
- **`nativelib/.../gc/shared/MemoryMap.c`**: Fixed missing braces bug where `return NULL` was unconditional; uses `malloc` instead of `mmap`
- **`nativelib/.../stackOverflowGuards.c`**: Entire file no-oped (no MMU, no signals, no `mprotect`)
- **`clib/.../stdatomic.c`**: 64-bit atomic ops (`llong`/`ullong`) use plain reads/writes via `PD_LL`/`PD_ULL` casts to avoid `__atomic_load_8` (no `libatomic` on this ARM toolchain)

## Code Style
- Ignore unused import warnings — don't fix or mention them.

## Debugging
- Crash logs: `sbt playdateCopyCrashLogs` copies from device to `./crashlog.txt`
- ELF for addr2line: `game/Source/pdex.elf` or `game/HelloWorld.elf` (note: Playdate loads at base `0x90000000`)
- `pd_log_error` in `game/main.c` logs to Playdate console with timestamp
- `PD_DEBUG` flag enables debug logging in the SN fork's C code
