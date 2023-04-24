#include "libroot-out.h"
#include <assert.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
  assert(ScalaNativeInit() == 0);
  dupa();
}
