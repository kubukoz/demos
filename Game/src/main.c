#include "pd_api.h"
#include "demo.h"

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{
	if (event == kEventInit)
	{
		ScalaNativeInit();
	}

	sn_event(pd, event, arg);

	return 0;
}
