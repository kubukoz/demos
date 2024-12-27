#ifndef LEAFBLOWER_H
#define LEAFBLOWER_H
typedef struct
{
  int value;
} LBLanguage;

int blow_leaf(LBLanguage *language);
void free_leaf(LBLanguage *language);

#endif
