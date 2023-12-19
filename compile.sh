#!/bin/bash
# "/nix/store/a5v30qll5i02vr9y97bk1rdx3mm6kvlm-clang-16.0.6/bin/clang-16" \
# "-cc1" \
# "-triple" \
# "arm-none-unknown-eabi" \
# "-emit-obj" \
# "-disable-free" \
# "-clear-ast-before-backend" \
# "-disable-llvm-verifier" \
# "-discard-value-names" \
# "-main-file-name" "main.c" \
# "-mrelocation-model" "pic" \
# "-pic-level" "2" \
# "-fhalf-no-semantic-interposition" \
# "-mframe-pointer=all" \
# "-fmath-errno" \
# "-ffp-contract=on" \
# "-fno-rounding-math" "-fno-verbose-asm" "-mconstructor-aliases" "-nostdsysteminc" \
# "-target-feature" "+soft-float-abi" "-target-feature" "+crc" "-target-feature" "+fullfp16" "-target-feature" "+crypto" "-target-feature" "-sha2" "-target-feature" "-aes" "-target-feature" "+strict-align" "-target-abi" "aapcs" "-mfloat-abi" "soft" "-Wunaligned-access" "-mllvm" "-treat-scalable-fixed-error-as-warning" "-debugger-tuning=gdb" "-target-linker-version" "609" "-fcoverage-compilation-dir=/Users/kubukoz/projects/demos/scala-native-lib" "-nostdsysteminc" "-resource-dir" "/nix/store/3fpl3hs6ii820yxp0rbafx5phnmj8ng7-clang-wrapper-16.0.6/resource-root" "-idirafter" "/nix/store/g1w6cr7w95mpny3pg0j4jwwlmwqbmdzm-libSystem-11.0.0/include" "-isystem" "/nix/store/kn42w75jaxn67cwasjc8bamvqvvmwj4j-libcxx-16.0.6-dev/include" "-isystem" "/nix/store/kn42w75jaxn67cwasjc8bamvqvvmwj4j-libcxx-16.0.6-dev/include" "-isystem" "/nix/store/x26rhb948xf8jxqcrrm431qda6pgpjn1-libcxxabi-16.0.6-dev/include" "-isystem" "/nix/store/x26rhb948xf8jxqcrrm431qda6pgpjn1-libcxxabi-16.0.6-dev/include" "-isystem" "/nix/store/vlajrml5iynvz4b6g1hivjdwa7614g8q-compiler-rt-libc-16.0.6-dev/include" "-isystem" "/nix/store/vlajrml5iynvz4b6g1hivjdwa7614g8q-compiler-rt-libc-16.0.6-dev/include" "-U" "_FORTIFY_SOURCE" "-U" "_FORTIFY_SOURCE" "-D" "_FORTIFY_SOURCE=2" "-internal-isystem" "/nix/store/3fpl3hs6ii820yxp0rbafx5phnmj8ng7-clang-wrapper-16.0.6/resource-root/include" "-source-date-epoch" "315532800" "-O2" "-Wformat" "-Wformat-security" "-Werror=format-security" "-fdebug-compilation-dir=/Users/kubukoz/projects/demos/scala-native-lib" "-ferror-limit" "19" "-fwrapv" "-stack-protector" "2" "-stack-protector-buffer-size" "4" "-fno-signed-char" "-fgnuc-version=4.2.1" "-fcolor-diagnostics" "-vectorize-loops" "-vectorize-slp" "-faddrsig" "-o" "/var/folders/f_/cbwjhbrs2jjg59qn23y6nhvm0000gn/T/main-93bac3.o" "-x" "c" "main.c"


"ld" "-no_uuid" "/var/folders/f_/cbwjhbrs2jjg59qn23y6nhvm0000gn/T/main-93bac3.o"  "-L/nix/store/vf7k7l7ny35g1nmninaqjbryl2xqkag5-libcxx-16.0.6/lib" "-L/nix/store/vf7k7l7ny35g1nmninaqjbryl2xqkag5-libcxx-16.0.6/lib" "-L/nix/store/x622mzi1g7v7nh7zq5r8d0nxbhrg9px3-libcxxabi-16.0.6/lib" "-L/nix/store/x622mzi1g7v7nh7zq5r8d0nxbhrg9px3-libcxxabi-16.0.6/lib" "-L/nix/store/l92ls1vba62zsywdj81hlgmgx5adn6bn-compiler-rt-libc-16.0.6/lib" "-L/nix/store/l92ls1vba62zsywdj81hlgmgx5adn6bn-compiler-rt-libc-16.0.6/lib" "-L/nix/store/g1w6cr7w95mpny3pg0j4jwwlmwqbmdzm-libSystem-11.0.0/lib" "-L/nix/store/yl2yc5pjp29aqdphfyav7vka3ysdm9x4-clang-16.0.6-lib/lib" "-L/nix/store/vf7k7l7ny35g1nmninaqjbryl2xqkag5-libcxx-16.0.6/lib" "-L/nix/store/a5v30qll5i02vr9y97bk1rdx3mm6kvlm-clang-16.0.6/bin/../lib/clang-runtimes/arm-none-eabi/lib" "-L/nix/store/a5v30qll5i02vr9y97bk1rdx3mm6kvlm-clang-16.0.6/bin/../lib/clang-runtimes/arm-none-eabi/lib" "-L/nix/store/3fpl3hs6ii820yxp0rbafx5phnmj8ng7-clang-wrapper-16.0.6/resource-root/lib/baremetal" "-lc" "-lm" "-o" "a.out"
