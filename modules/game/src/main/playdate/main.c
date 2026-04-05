
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#ifdef TARGET_PLAYDATE
#include <sys/stat.h>
#include <errno.h>
#ifdef errno
#undef errno
#endif
#endif
#include <pd_api.h>
#include "mylib.h"
#include <stdarg.h>
#include <time.h>
#include <stdbool.h>

#ifndef PLAYDATE_HOLDER
#define PLAYDATE_HOLDER
PlaydateAPI *_pd;
#endif

LCDFont *font;

void pd_log_error(char *str, ...);

unsigned int pd_getCurrentTimeMilliseconds(void)
{
    return _pd->system->getCurrentTimeMilliseconds();
}

void pd_scalanative_init(PlaydateAPI *pd)
{
    _pd = pd;
}

void pd_httpbin_request(void);
static int update(void *userdata)
{
    PlaydateAPI *pd = userdata;

    PDButtons pressed;
    pd->system->getButtonState(NULL, &pressed, NULL);

    return sn_update(pd);
};

#ifdef TARGET_PLAYDATE
void log_old_errors(void);
void truncate_errors(void);
#endif

__attribute__((visibility("default"))) int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{

    if (event == kEventInit)
    {
        volatile int ehMarker = 0;
        pd_scalanative_init(pd);
        pd->system->logToConsole("eventHandler(kEventInit) stack @ %p", &ehMarker);

        ScalaNativeInit();

        sn_event(pd, kEventInit);
        pd->system->setUpdateCallback(update, pd);
    }

    // log_old_errors();

    pd_log_error("eventHandler before terminate check");
    // #ifdef TARGET_PLAYDATE
    //     if (event == kEventTerminate)
    //     {
    //         _pd->system->logToConsole("App exiting normally, truncating errors...");
    //         truncate_errors();
    //     }
    // #endif

    pd_log_error("eventHandler logging event");
    pd->system->logToConsole("Event: %d", event);

    pd_log_error("eventHandler calling sn_event");
    return sn_event(pd, event);
}

// error reporting

void pd_log_error_raw(char *str)
{
    int ts = _pd->system->getCurrentTimeMilliseconds();
    _pd->system->logToConsole("[t=%u] %s\n", ts, str);
    // SDFile *file = _pd->file->open("jk-logs.txt", kFileAppend);
    // _pd->file->write(file, str, strlen(str));
    // _pd->file->write(file, "\n", strlen("\n"));
    // _pd->file->close(file);
}

void pd_log_error(char *str, ...)
{
    char buffer[256];
    va_list args;
    va_start(args, str);
    vsprintf(buffer, str, args);
    va_end(args);
    pd_log_error_raw(buffer);
}

void scalanative_pd_exit(int status, const char *file, int line)
{
    pd_log_error("exit(%d) called at %s:%d", status, file, line);
    _exit(status);
}

void scalanative_pd_abort(const char *file, int line)
{
    pd_log_error("abort() called at %s:%d", file, line);
    _exit(1);
}

#ifdef TARGET_PLAYDATE
int errno = 0;

int *__error(void)
{
    return &errno;
}

// newlib stubs — backed by Playdate file API where possible

void _exit(int code)
{
    while (1)
    {
        _pd->system->error("exited with code %d.", code);
    }
}
int _kill(int pid, int sig) { return 0; }
int _getpid(void) { return 1; }

#define HANDLE_STDIN 0
#define HANDLE_STDOUT 1
#define HANDLE_STDERR 2
#define MAXFILES 32
#define FILEHANDLEOFF 3

static SDFile *openfiles[MAXFILES];

int _isatty(int file) { return file >= 0 && file <= HANDLE_STDERR; }

int _write(int handle, char *data, int size)
{
    if (size == 0 || data == NULL)
        return 0;

    if (handle == HANDLE_STDOUT || handle == HANDLE_STDERR)
    {
        if (_pd == NULL)
            return size;
        char buf[256];
        int n = size < 255 ? size : 255;
        memcpy(buf, data, n);
        buf[n] = '\0';
        _pd->system->logToConsole("%s", buf);
        return size;
    }
    else if (handle >= FILEHANDLEOFF && handle < FILEHANDLEOFF + MAXFILES)
    {
        SDFile *f = openfiles[handle - FILEHANDLEOFF];
        if (f)
        {
            int s = _pd->file->write(f, data, size);
            return s < 0 ? -1 : s;
        }
    }
    return -1;
}

int _read(int handle, char *ptr, int len)
{
    if (handle >= FILEHANDLEOFF && handle < FILEHANDLEOFF + MAXFILES)
    {
        SDFile *f = openfiles[handle - FILEHANDLEOFF];
        if (f)
        {
            int s = _pd->file->read(f, ptr, len);
            return s < 0 ? -1 : s;
        }
    }
    return 0;
}

int _open(const char *name, int flags, int mode)
{
    for (size_t i = 0; i < MAXFILES; ++i)
    {
        if (!openfiles[i])
        {
            openfiles[i] = _pd->file->open(name, mode);
            if (openfiles[i] == NULL)
            {
                errno = ENOENT;
                return -1;
            }
            return FILEHANDLEOFF + i;
        }
    }
    errno = ENFILE;
    return -1;
}

int _close(int handle)
{
    if (handle >= FILEHANDLEOFF && handle < FILEHANDLEOFF + MAXFILES)
    {
        int idx = handle - FILEHANDLEOFF;
        SDFile *f = openfiles[idx];
        if (f != NULL)
        {
            if (_pd->file->close(f))
                return -1;
            openfiles[idx] = NULL;
            return 0;
        }
    }
    return -1;
}

int _lseek(int handle, int pos, int whence)
{
    if (handle >= FILEHANDLEOFF && handle < FILEHANDLEOFF + MAXFILES)
    {
        SDFile *f = openfiles[handle - FILEHANDLEOFF];
        if (f)
        {
            if (_pd->file->seek(f, pos, whence))
                return -1;
            return _pd->file->tell(f);
        }
    }
    return -1;
}

int _fstat(int file, struct stat *st)
{
    memset(st, 0, sizeof(*st));
    if (_isatty(file))
        st->st_mode = S_IFCHR;
    return 0;
}

void log_old_errors()
{
    SDFile *file = _pd->file->open("jk-errors.txt", kFileReadData);
    char buf[256];
    int read = 0;
    _pd->system->logToConsole("============== REPLAYING LOGS =================");

    while ((read = _pd->file->read(file, buf, 256)) != 0)
    {
        char str[256 + 1];
        memcpy(str, buf, read);
        str[read] = '\0';
        _pd->system->logToConsole("%s", str);
    }

    _pd->system->logToConsole("============== REPLAY DONE =================");
    _pd->file->close(file);
}

void truncate_errors()
{
    SDFile *file = _pd->file->open("jk-errors.txt", kFileWrite);
    _pd->file->close(file);
}
#endif /* TARGET_PLAYDATE */

// PD API forwarders

void pd_system_logToConsole(const char *fmt)
{
    _pd->system->logToConsole(fmt);
}

void pd_display_setRefreshRate(float rate)
{
    _pd->display->setRefreshRate(rate);
}

void pd_system_getButtonState(PDButtons *pressed, PDButtons *held, PDButtons *released)
{
    _pd->system->getButtonState(pressed, held, released);
}

void pd_graphics_fillRect(int x, int y, int w, int h, LCDColor color)
{
    _pd->graphics->fillRect(x, y, w, h, color);
}

void pd_graphics_drawRect(int x, int y, int w, int h, LCDColor color)
{
    _pd->graphics->drawRect(x, y, w, h, color);
}

void pd_graphics_clear(LCDColor color)
{
    _pd->graphics->clear(color);
}

float pd_system_getCrankChange()
{
    return _pd->system->getCrankChange();
}

bool pd_system_isCrankDocked()
{
    return _pd->system->isCrankDocked();
}

SamplePlayer *pd_sound_sampleplayer_newPlayer()
{
    return _pd->sound->sampleplayer->newPlayer();
}

AudioSample *pd_sound_sample_load(const char *path)
{
    return _pd->sound->sample->load(path);
}

void pd_sound_sampleplayer_freePlayer(SamplePlayer *player)
{
    _pd->sound->sampleplayer->freePlayer(player);
}

void pd_sound_sample_freeSample(AudioSample *sample)
{
    _pd->sound->sample->freeSample(sample);
}

void pd_sound_sampleplayer_setSample(SamplePlayer *player, AudioSample *sample)
{
    _pd->sound->sampleplayer->setSample(player, sample);
}

void pd_sound_sampleplayer_setVolume(SamplePlayer *player, float left, float right)
{
    _pd->sound->sampleplayer->setVolume(player, left, right);
}

int pd_sound_sampleplayer_play(SamplePlayer *player, int repeat, float rate)
{
    return _pd->sound->sampleplayer->play(player, repeat, rate);
}

LCDBitmap *pd_graphics_loadBitmap(const char *path, const char **outErr)
{
    return _pd->graphics->loadBitmap(path, outErr);
}

LCDBitmap *pd_graphics_newBitmap(int width, int height, LCDColor bgcolor)
{
    return _pd->graphics->newBitmap(width, height, bgcolor);
}

LCDSprite *pd_sprite_newSprite()
{
    return _pd->sprite->newSprite();
}

void pd_sprite_freeSprite(LCDSprite *sprite)
{
    _pd->sprite->freeSprite(sprite);
}

void pd_sprite_addSprite(LCDSprite *sprite)
{
    _pd->sprite->addSprite(sprite);
}

void pd_sprite_setTag(LCDSprite *sprite, uint8_t tag)
{
    _pd->sprite->setTag(sprite, tag);
}

void pd_sprite_setImage(LCDSprite *sprite, LCDBitmap *image, LCDBitmapFlip flip)
{
    _pd->sprite->setImage(sprite, image, flip);
}

void pd_sprite_setCenter(LCDSprite *sprite, float x, float y)
{
    _pd->sprite->setCenter(sprite, x, y);
}

void pd_sprite_setVisible(LCDSprite *sprite, int flag)
{
    _pd->sprite->setVisible(sprite, flag);
}

void pd_sprite_moveTo(LCDSprite *sprite, float x, float y)
{
    _pd->sprite->moveTo(sprite, x, y);
}

void pd_graphics_getBitmapData(LCDBitmap *bitmap, int *width, int *height, int *rowbytes, uint8_t **mask, uint8_t **data)
{
    _pd->graphics->getBitmapData(bitmap, width, height, rowbytes, mask, data);
}

void pd_graphics_freeBitmap(LCDBitmap *bitmap)
{
    _pd->graphics->freeBitmap(bitmap);
}

void pd_graphics_drawScaledBitmap(LCDBitmap *bitmap, int x, int y, float xscale, float yscale)
{
    _pd->graphics->drawScaledBitmap(bitmap, x, y, xscale, yscale);
}

int pd_graphics_getTextWidth(LCDFont *font, const char *text, size_t len, PDStringEncoding encoding, int tracking)
{
    return _pd->graphics->getTextWidth(font, text, len, encoding, tracking);
}

int pd_graphics_getTextHeightForMaxWidth(LCDFont *font, const char *text, size_t len, int maxWidth, PDStringEncoding encoding, PDTextWrappingMode wrap, int tracking, int extraLeading)
{
    return _pd->graphics->getTextHeightForMaxWidth(font, text, len, maxWidth, encoding, wrap, tracking, extraLeading);
}

void pd_graphics_drawRotatedBitmap(LCDBitmap *bitmap, int x, int y, float rotation, float centerx, float centery, float xscale, float yscale)
{
    return _pd->graphics->drawRotatedBitmap(bitmap, x, y, rotation, centerx, centery, xscale, yscale);
}

void pd_graphics_pushContext(LCDBitmap *ctx)
{
    _pd->graphics->pushContext(ctx);
}

void pd_graphics_popContext()
{
    _pd->graphics->popContext();
}

int pd_graphics_getTextTracking()
{
    return _pd->graphics->getTextTracking();
}
void pd_graphics_drawText(const char *text, size_t len, PDStringEncoding encoding, int x, int y)
{
    _pd->graphics->drawText(text, len, encoding, x, y);
}
void pd_system_drawFPS(int x, int y)
{
    _pd->system->drawFPS(x, y);
}

float pd_system_getElapsedTime()
{
    return _pd->system->getElapsedTime();
}

void pd_system_resetElapsedTime()
{
    _pd->system->resetElapsedTime();
}

// --- HTTPBin sample request ---

static const struct playdate_http *_http;
static HTTPConnection *_httpbin_conn;

static void httpbin_header_received(HTTPConnection *conn, const char *key, const char *value)
{
    _pd->system->logToConsole("httpbin: header %s = %s", key, value);
}

static void httpbin_headers_read(HTTPConnection *conn)
{
    int status = _http->getResponseStatus(conn);
    _pd->system->logToConsole("httpbin: headers done, HTTP %d", status);
}

static void httpbin_response(HTTPConnection *conn)
{
    _pd->system->logToConsole("httpbin: response callback");
    int avail = _http->getBytesAvailable(conn);
    _pd->system->logToConsole("httpbin: %d bytes available", (int)avail);
}

static void httpbin_request_complete(HTTPConnection *conn)
{
    PDNetErr err = _http->getError(conn);
    if (err != NET_OK)
    {
        _pd->system->logToConsole("httpbin: request error %d", err);
        _http->release(conn);
        _httpbin_conn = NULL;
        return;
    }

    _pd->system->logToConsole("httpbin: request complete");

    int avail;
    while ((avail = _http->getBytesAvailable(conn)) > 0)
    {
        char buf[512];
        int n = _http->read(conn, buf, sizeof(buf) - 1);
        if (n > 0)
        {
            buf[n] = '\0';
            _pd->system->logToConsole("%s", buf);
        }
    }

    _http->release(conn);
    _httpbin_conn = NULL;
}

static void httpbin_closed(HTTPConnection *conn)
{
    PDNetErr err = _http->getError(conn);
    _pd->system->logToConsole("httpbin: connection closed, err=%d", err);
}

static void httpbin_do_request(void)
{
    _httpbin_conn = _http->newConnection("192.168.1.96", 8000, false);
    _http->setHeaderReceivedCallback(_httpbin_conn, httpbin_header_received);
    _http->setHeadersReadCallback(_httpbin_conn, httpbin_headers_read);
    _http->setResponseCallback(_httpbin_conn, httpbin_response);
    _http->setRequestCompleteCallback(_httpbin_conn, httpbin_request_complete);
    _http->setConnectionClosedCallback(_httpbin_conn, httpbin_closed);

    PDNetErr err = _http->get(_httpbin_conn, "/game/mylib.h", NULL, 0);
    _pd->system->logToConsole("httpbin: GET /get sent, err=%d", err);
}

static void httpbin_access_callback(bool allowed, void *userdata)
{
    _pd->system->logToConsole("httpbin: access %s", allowed ? "granted" : "denied");
    if (allowed)
        httpbin_do_request();
}

void pd_httpbin_request(void)
{
    if (_pd->network == NULL)
    {
        _pd->system->logToConsole("httpbin: network is NULL");
        return;
    }
    _http = _pd->network->http;
    if (_http == NULL)
    {
        _pd->system->logToConsole("httpbin: http is NULL");
        return;
    }

    enum accessReply reply = _http->requestAccess("192.168.1.96", 8000, false, "HTTP test", httpbin_access_callback, NULL);
    if (reply == kAccessAllow)
        httpbin_do_request();
}
