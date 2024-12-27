#include <leafblower.h>
#include <stdlib.h>

int blow_leaf(LBLanguage *leaf)
{
  return leaf->value + 1;
}

void free_leaf(LBLanguage *leaf)
{
  free(leaf);
}
