#ifndef MYLIB_H
#define MYLIB_H
#include "pd_api.h"
int ScalaNativeInit(void);
int foo(int arg);
int sn_event(PlaydateAPI *, PDSystemEvent event, uint32_t arg);

#endif
