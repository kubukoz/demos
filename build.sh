#!/bin/sh
cc demo.c -I ./headers -L ./native/target/scala-3.2.2 -l root-out -L ./native/target/scala-3.2.2/native/ -l c++ && ./a.out
