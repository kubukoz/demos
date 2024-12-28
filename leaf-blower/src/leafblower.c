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

KEEPALIVE int hello_leaf()
{
  return 13;
}

KEEPALIVE
int blow_leaf(LBLanguage *leaf)
{
  if (leaf == NULL)
  {
    return -1;
  }
  return leaf->value + 1;
}

KEEPALIVE
void free_leaf(LBLanguage *leaf)
{
  free(leaf);
}
