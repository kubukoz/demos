#include <leafblower.h>
#include <stdlib.h>

LBLanguage *scala_leaf()
{
  LBLanguage *leaf = (LBLanguage *)malloc(sizeof(LBLanguage));
  leaf->value = 42;
  return leaf;
}
