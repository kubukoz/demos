#ifndef MYLIB_H
#define MYLIB_H
#include "pd_api.h"

#ifdef __cplusplus
extern "C"
{
#endif

int ScalaNativeInit(void);
int foo(int arg);
int sn_event(PlaydateAPI *, PDSystemEvent event, uint32_t arg);

void pd_scalanative_init(PlaydateAPI *pd);
void pd_log(const char *fmt);

#ifdef __cplusplus
}
#endif

#endif
