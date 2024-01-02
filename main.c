#include <pd_api.h>

int update(void *userdata)
{
  PlaydateAPI *pd = (PlaydateAPI *)userdata;
  return 0;
}

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{
  (void)arg; // arg is currently only used for event = kEventKeyPressed

  if (event == kEventInit)
  {
    pd->system->logToConsole("hello from C compiled with clang!");
    pd->system->setUpdateCallback(update, pd);
  }

  return 0;
}
