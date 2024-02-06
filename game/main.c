
#include <string.h>
#include <stdio.h>
#include <pd_api.h>
#include <pdcpp/pdnewlib.h>
#include "mylib.h"
#include <stdarg.h>
#include <time.h>
#include <stdbool.h>

#ifndef PLAYDATE_HOLDER
#define PLAYDATE_HOLDER
PlaydateAPI *_pd;
#endif

LCDFont *font;

#ifdef PD_DEBUG
void pd_log_error(char *str, ...);
#endif

int allocationCount = 16 * 1024;

static int update(void *userdata)
{
    PlaydateAPI *pd = userdata;
    PDButtons pressed;
    pd->system->getButtonState(NULL, &pressed, NULL);

    if (pressed & kButtonUp)
    {
        allocationCount *= 2;
        if (allocationCount <= 0)
            allocationCount = 1;
    }
    if (pressed & kButtonDown)
    {
        allocationCount /= 2;
    }

    char txt[100];
    int megas = allocationCount >= 1024 * 1024;
    int number = megas ? (allocationCount / 1024 / 1024) : (allocationCount / 1024);
    char symbol = megas ? 'M' : 'K';
    sprintf(txt, "Current allocation count: %d (%d%c)", allocationCount, number, symbol);

    pd->graphics->clear(kColorWhite);
    pd->graphics->drawText(txt, strlen(txt), kUTF8Encoding, 50, 100);

    if (pressed & kButtonA)
    {
        pd->system->logToConsole("Calling Scala with %d allocations", allocationCount);
        foo(allocationCount);
    }

    return 1;
};

int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{

    // This is required, otherwise linker errors abound <- this comes from playdate-cpp, not me
    eventHandler_pdnewlib(pd, event, arg);

    if (event == kEventInit)
    {
        _pd = pd;
        ScalaNativeInit();

        pd->system->setUpdateCallback(update, pd);
    }

    return 0;
}

// error reporting

void pd_log_error_internal(char *str)
{
    int ts = _pd->system->getCurrentTimeMilliseconds();
    _pd->system->logToConsole("[t=%u] %s\n", ts, str);
    SDFile *file = _pd->file->open("jk-logs.txt", kFileAppend);
    _pd->file->write(file, str, strlen(str));
    _pd->file->write(file, "\n", strlen("\n"));
    _pd->file->close(file);
}

void pd_log_error(char *str, ...)
{
    char buffer[128];
    va_list args;
    va_start(args, str);
    vsprintf(buffer, str, args);
    va_end(args);
    pd_log_error_internal(buffer);
}

int errno = 0;

int *__error(void)
{
    return &errno;
}

// more polyfills

size_t
scalanative_unwind_sizeof_context()
{
    // pd_log_error("scalanative_unwind_sizeof_context");
    exit(19);
    return 0;
}

size_t scalanative_unwind_sizeof_cursor()
{
    // pd_log_error("scalanative_unwind_sizeof_cursor");
    exit(20);
    return 0;
}

int scalanative_unwind_init_local(void *cursor, void *context)
{
    // pd_log_error("scalanative_unwind_init_local");
    exit(21);
    return 0;
}

int scalanative_unwind_step(void *cursor)
{
    // pd_log_error("scalanative_unwind_step");
    exit(22);
    return 0;
}

int scalanative_unw_reg_ip()
{
    // pd_log_error("scalanative_unw_reg_ip");
    exit(23);
    return 0;
}

int scalanative_unwind_get_reg(void *cursor, int regnum, size_t *valp)
{
    // pd_log_error("scalanative_unwind_get_reg");
    exit(24);
    return 0;
}

int scalanative_unwind_get_context(void *context)
{
    // pd_log_error("scalanative_unwind_get_context");
    exit(25);
    return 0;
}

long long scalanative_atomic_load_llong(long long *atm)
{
    // pd_log_error("scalanative_atomic_load_llong");
    exit(26);
    return 0;
}

bool scalanative_atomic_compare_exchange_strong_llong(long long *atm, long long *expected, long long desired)
{
    // pd_log_error("scalanative_atomic_compare_exchange_strong_llong");
    exit(27);
    return false;
}
