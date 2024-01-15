#include "pd_api.h"
#include "demo.h"

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{
	if (event == kEventInit)
	{
		ScalaNativeInit();
	}

	return sn_event(pd, event, arg);
}
