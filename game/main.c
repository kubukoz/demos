
#include <string.h>
#include <stdio.h>
#include <pd_api.h>
#include <pdcpp/pdnewlib.h>
#include "mylib.h"
#include <stdarg.h>
#include <time.h>

#ifndef PLAYDATE_HOLDER
#define PLAYDATE_HOLDER
PlaydateAPI *_pd;
#endif

LCDFont *font;

void pd_scalanative_init(PlaydateAPI *pd)
{
    _pd = pd;
}

static int gameTick(void *userdata)
{
    return sn_update((PlaydateAPI *)userdata);
};

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{

    // This is required, otherwise linker errors abound
    eventHandler_pdnewlib(pd, event, arg);

    if (event == kEventInit)
    {

        pd_scalanative_init(pd);
        ScalaNativeInit();

        pd->system->setUpdateCallback(gameTick, pd);
    }

    if (event == kEventTerminate)
    {
        pd->system->logToConsole("Tearing down...");
    }

    return sn_event(pd, event, arg);
}

// polyfills

void __error(char *str)
{
    _pd->system->logToConsole("Error! %s", str);
}

// PD API forwarders

void pd_system_drawFPS(int x, int y)
{
    _pd->system->drawFPS(x, y);
}

void pd_display_setRefreshRate(float rate)
{
    _pd->display->setRefreshRate(rate);
}

void pd_system_getButtonState(PDButtons *current, PDButtons *pressed, PDButtons *released)
{
    _pd->system->getButtonState(current, pressed, released);
}

void pd_graphics_clear(LCDColor color)
{
    _pd->graphics->clear(color);
}

void pd_graphics_fillRect(int x, int y, int w, int h /* , LCDColor color */)
{
    _pd->graphics->fillRect(x, y, w, h, kColorBlack);
}

void pd_graphics_drawRect(int x, int y, int w, int h /* , LCDColor color */)
{
    _pd->graphics->drawRect(x, y, w, h, kColorBlack);
}

float pd_system_getCrankChange()
{
    return _pd->system->getCrankChange();
}

void pd_system_logToConsole(const char *fmt)
{
    _pd->system->logToConsole(fmt);
}
