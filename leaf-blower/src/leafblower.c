#include <leafblower.h>
#include <stdlib.h>

int hello_leaf()
{
  return 13;
}

int blow_leaf(LBLanguage *leaf)
{
  return leaf->value + 1;
}

void free_leaf(LBLanguage *leaf)
{
  free(leaf);
}
