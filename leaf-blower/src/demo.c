#include <leafblower.h>
#include <leafblower-scala.h>
#include <stdio.h>

int main()
{
  LBLanguage *scala = scala_leaf();
  int blown = blow_leaf(scala);
  free_leaf(scala);
  printf("blown: %d\n", blown);
  return 0;
}
