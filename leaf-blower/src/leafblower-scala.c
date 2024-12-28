#include <leafblower.h>
#include <stdlib.h>

#ifdef __EMSCRIPTEN__
#include <emscripten.h>
#endif

// Apply EMSCRIPTEN_KEEPALIVE for Emscripten builds
#ifdef __EMSCRIPTEN__
#define KEEPALIVE __attribute__((used)) EMSCRIPTEN_KEEPALIVE
#else
#define KEEPALIVE
#endif

KEEPALIVE
LBLanguage *scala_leaf()
{
  LBLanguage *leaf = (LBLanguage *)malloc(sizeof(LBLanguage));
  leaf->value = 42;
  return leaf;
}
