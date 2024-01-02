#!/usr/bin/env bash

# SDK_PATH with a default of /Users/kubukoz/Developer/PlaydateSDK
SDK_PATH=${SDK_PATH:-/Users/kubukoz/Developer/PlaydateSDK/C_API}

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
  # -isystem /Users/kubukoz/Downloads/foo/include/c++/v1
  # -isystem /Users/kubukoz/Downloads/foo/include/ #A
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
  # -Wa,-ahlms=build/main.lst
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
)

mkdir -p dep
$CC ${FLAGS[@]}
# /usr/local/bin/arm-none-eabi-gcc -g3 -c -mthumb -mcpu=cortex-m7 -mfloat-abi=hard -mfpu=fpv5-sp-d16 -D__FPU_USED=1 -O2 -falign-functions=16 -fomit-frame-pointer -gdwarf-2 -Wall -Wno-unused -Wstrict-prototypes -Wno-unknown-pragmas  -fverbose-asm -Wdouble-promotion -mword-relocations -fno-common -ffunction-sections -fdata-sections -Wa,-ahlms=build/setup.lst -DTARGET_PLAYDATE=1 -DTARGET_EXTENSION=1  -MD -MP -MF build/dep/setup.o.d -I . -I . -I /Users/kubukoz/Developer/PlaydateSDK/C_API /Users/kubukoz/Developer/PlaydateSDK/C_API/buildsupport/setup.c -o build//Users/kubukoz/Developer/PlaydateSDK/C_API/buildsupport/setup.o
# /usr/local/bin/arm-none-eabi-gcc -g3 build/src/main.o build//Users/kubukoz/Developer/PlaydateSDK/C_API/buildsupport/setup.o -nostartfiles -mthumb -mcpu=cortex-m7 -mfloat-abi=hard -mfpu=fpv5-sp-d16 -D__FPU_USED=1 -T/Users/kubukoz/Developer/PlaydateSDK/C_API/buildsupport/link_map.ld -Wl,-Map=build/pdex.map,--cref,--gc-sections,--no-warn-mismatch,--emit-relocs    -o build/pdex.elf