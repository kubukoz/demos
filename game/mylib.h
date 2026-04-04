#ifndef MYLIB_H
#define MYLIB_H
#include "pd_api.h"

int ScalaNativeInit(void);
// trying to pass arg crashes the game on resume events
int sn_event(PlaydateAPI *, PDSystemEvent event /* , uint32_t arg */);
int sn_update(PlaydateAPI *pd);

#endif
