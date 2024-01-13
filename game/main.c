
#include <string.h>
#include <stdio.h>
#include <pd_api.h>
#include <pdcpp/pdnewlib.h>
#include "mylib.h"
#include <stdarg.h>

LCDFont *font;

/**
 * The Playdate API requires a C-style, or static function to be called as the
 * main update function. Here we use such a function to delegate execution to
 * our class.
 */
static int gameTick(void *userdata)
{
    PlaydateAPI *pd = (PlaydateAPI *)userdata;

    pd->graphics->clear(kColorWhite);
    pd->graphics->setFont(font);

    char *text = (char *)malloc(sizeof(char) * 50);
    sprintf(text, "Hello Scala %s: %d", __TIMESTAMP__, foo(42));

    pd->graphics->drawText(text, strlen(text), kASCIIEncoding, 25, 120);
    free(text);

    pd->system->drawFPS(0, 0);

    return 1;
};

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{

    // This is required, otherwise linker errors abound
    eventHandler_pdnewlib(pd, event, arg);

    if (event == kEventInit)
    {

        ScalaNativeInit();
        pd_scalanative_init(pd);

        const char *err;
        font = pd->graphics->loadFont("/System/Fonts/Asheville-Sans-14-Bold.pft", &err);

        if (font == NULL)
            pd->system->error("%s:%i Couldn't load font: %s", __FILE__, __LINE__, err);

        pd->display->setRefreshRate(50);

        // and sets the tick function to be called on update to turn off the
        // typical 'Lua'-ness.
        pd->system->setUpdateCallback(gameTick, pd);
    }

    if (event == kEventTerminate)
    {
        pd->system->logToConsole("Tearing down...");
    }

    pd->system->logToConsole("SN Event: %d", sn_event(pd, event, arg));
    return 0;
}

#ifndef PLAYDATE_HOLDER
#define PLAYDATE_HOLDER
PlaydateAPI *_pd;
#endif

void pd_scalanative_init(PlaydateAPI *pd)
{
    _pd = pd;
}
void pd_log(const char *fmt)
{
    _pd->system->logToConsole(fmt);
}
