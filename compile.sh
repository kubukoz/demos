#!/usr/bin/env bash

set -euo pipefail

# SDK_PATH with a default of /Users/kubukoz/Developer/PlaydateSDK
SDK_PATH=${SDK_PATH:-/Users/kubukoz/Developer/PlaydateSDK/C_API}
TOOLCHAIN_PATH=${TOOLCHAIN_PATH:-/usr/local/playdate/gcc-arm-none-eabi-9-2019-q4-major/arm-none-eabi}
LD_PATH=${LD_PATH:-ld}

SIM_FLAGS=(
  -dynamiclib
  -rdynamic
  -lm
  -DTARGET_SIMULATOR=1 -DTARGET_EXTENSION=1
  -I "$SDK_PATH"
  main.c
  "$SDK_PATH/buildsupport/setup.c"
  -o pdex.dylib
)

# SIM_CC=/usr/bin/clang
# $SIM_CC ${SIM_FLAGS[@]}

CC=clang

FLAGS=(
  -target arm-none-eabi
  -g3
  -c
  -mthumb
  -mcpu=cortex-m7
  -mfloat-abi=hard
  -mfpu=fpv5-sp-d16
  -D__FPU_USED=1
  -O2
  -falign-functions=16
  -fomit-frame-pointer
  -gdwarf-2
  -Wall
  -Wno-unused
  -Wstrict-prototypes
  -Wno-unknown-pragmas
  -fverbose-asm
  -Wdouble-promotion
  # -mword-relocations
  -fno-common
  -ffunction-sections
  -fdata-sections
  # '-Wa,-ahlms=build/main.lst'
  -DTARGET_PLAYDATE=1
  -DTARGET_EXTENSION=1
  -MD
  -MP
  -MF
  dep/main.o.d
  -I .
  -I .
  -I "$SDK_PATH"
  main.c
  -o
  main.o
  -I$TOOLCHAIN_PATH/include
)

mkdir -p dep
$CC ${FLAGS[@]}

FLAGS=(
  -target arm-none-eabi
  -g3
  -c
  -mthumb
  -mcpu=cortex-m7
  -mfloat-abi=hard
  -mfpu=fpv5-sp-d16
  -D__FPU_USED=1
  -O2
  -falign-functions=16
  -fomit-frame-pointer
  -gdwarf-2
  -Wall
  -Wno-unused
  -Wstrict-prototypes
  -Wno-unknown-pragmas
  -fverbose-asm
  -Wdouble-promotion
  # -mword-relocations
  -fno-common
  -ffunction-sections
  -fdata-sections
  # '-Wa,-ahlms=build/setup.lst'
  -DTARGET_PLAYDATE=1
  -DTARGET_EXTENSION=1
  -MD
  -MP
  -MF
  dep/setup.o.d
  -I .
  -I .
  -I "$SDK_PATH"
  "$SDK_PATH/buildsupport/setup.c"
  -o
  setup.o
  -I$TOOLCHAIN_PATH/include
)

$CC ${FLAGS[@]}

FLAGS=(
  -target arm-none-eabi
  -g3
  main.o
  setup.o
  -nostartfiles
  -mthumb
  -mcpu=cortex-m7
  -mfloat-abi=hard
  -mfpu=fpv5-sp-d16
  -D__FPU_USED=1
  -T$SDK_PATH/buildsupport/link_map.ld
  -Wl,-Map=pdex.map,--cref,--gc-sections,--no-warn-mismatch,--emit-relocs
  -o
  pdex.elf
  -L"/root/arm-gnu-toolchain-13.2.Rel1-x86_64-arm-none-eabi/arm-none-eabi/lib"
  -fuse-ld=$(LD_PATH)
)

$CC ${FLAGS[@]}

