
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

unsigned int pd_getSecondsSinceEpoch(unsigned int *milliseconds)
{
    return _pd->system->getSecondsSinceEpoch(milliseconds);
}

void pd_scalanative_init(PlaydateAPI *pd)
{
    _pd = pd;
}

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
        // pd->system->logToConsole("eventHandler(kEventInit) stack @ %p", &ehMarker);

        ScalaNativeInit();
        pd->system->setUpdateCallback(update, pd);
    }

    // log_old_errors();

    // pd_log_error("eventHandler before terminate check");
    // #ifdef TARGET_PLAYDATE
    //     if (event == kEventTerminate)
    //     {
    //         _pd->system->logToConsole("App exiting normally, truncating errors...");
    //         truncate_errors();
    //     }
    // #endif

    // pd_log_error("eventHandler logging event");
    // pd->system->logToConsole("Event: %d", event);

    // pd_log_error("eventHandler calling sn_event");
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
    _exit(42069);
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

const char *pd_system_getLaunchArgs()
{
    const char *outpath;
    const char *args = _pd->system->getLaunchArgs(&outpath);
    return args;
}

void pd_system_resetElapsedTime()
{
    _pd->system->resetElapsedTime();
}

// --- Playdate TCP API bindings ---

typedef void (*pd_tcp_open_callback)(TCPConnection *conn, PDNetErr err, void *ud);
typedef void (*pd_tcp_connection_callback)(TCPConnection *conn, PDNetErr err);

static const struct playdate_tcp *pd_tcp(void)
{
    return _pd->network->tcp;
}

TCPConnection *pd_tcp_newConnection(const char *server, int port, int usessl)
{
    return pd_tcp()->newConnection(server, port, usessl);
}

int pd_tcp_open(TCPConnection *conn, pd_tcp_open_callback cb, void *ud)
{
    return pd_tcp()->open(conn, cb, ud);
}

int pd_tcp_write(TCPConnection *conn, const void *buffer, size_t length)
{
    return pd_tcp()->write(conn, buffer, length);
}

int pd_tcp_read(TCPConnection *conn, void *buffer, size_t length)
{
    return pd_tcp()->read(conn, buffer, length);
}

size_t pd_tcp_getBytesAvailable(TCPConnection *conn)
{
    return pd_tcp()->getBytesAvailable(conn);
}

void pd_tcp_setReadTimeout(TCPConnection *conn, int ms)
{
    pd_tcp()->setReadTimeout(conn, ms);
}

// --- Playdate HTTP API bindings ---

typedef void (*pd_http_header_callback)(HTTPConnection *conn, const char *key, const char *value);
typedef void (*pd_http_connection_callback)(HTTPConnection *conn);
typedef void (*pd_http_access_callback)(bool allowed, void *userdata);

static const struct playdate_http *pd_http(void)
{
    return _pd->network->http;
}

int pd_http_available(void)
{
    return _pd->network != NULL && _pd->network->http != NULL;
}

int pd_http_requestAccess(const char *server, int port, int usessl, const char *purpose, pd_http_access_callback callback, void *userdata)
{
    return pd_http()->requestAccess(server, port, usessl, purpose, callback, userdata);
}

HTTPConnection *pd_http_newConnection(const char *server, int port, int usessl)
{
    return pd_http()->newConnection(server, port, usessl);
}

void pd_http_release(HTTPConnection *conn)
{
    pd_http()->release(conn);
}

int pd_http_query(HTTPConnection *conn, const char *method, const char *path, const char *headers, size_t headerlen, const char *body, size_t bodylen)
{
    return pd_http()->query(conn, method, path, headers, headerlen, body, bodylen);
}

int pd_http_getError(HTTPConnection *conn)
{
    return pd_http()->getError(conn);
}

int pd_http_getResponseStatus(HTTPConnection *conn)
{
    return pd_http()->getResponseStatus(conn);
}

size_t pd_http_getBytesAvailable(HTTPConnection *conn)
{
    return pd_http()->getBytesAvailable(conn);
}

int pd_http_read(HTTPConnection *conn, void *buf, unsigned int buflen)
{
    return pd_http()->read(conn, buf, buflen);
}

void pd_http_close(HTTPConnection *conn)
{
    pd_http()->close(conn);
}

void pd_http_setHeaderReceivedCallback(HTTPConnection *conn, pd_http_header_callback callback)
{
    pd_http()->setHeaderReceivedCallback(conn, callback);
}

void pd_http_setHeadersReadCallback(HTTPConnection *conn, pd_http_connection_callback callback)
{
    pd_http()->setHeadersReadCallback(conn, callback);
}

void pd_http_setResponseCallback(HTTPConnection *conn, pd_http_connection_callback callback)
{
    pd_http()->setResponseCallback(conn, callback);
}

void pd_http_setRequestCompleteCallback(HTTPConnection *conn, pd_http_connection_callback callback)
{
    pd_http()->setRequestCompleteCallback(conn, callback);
}

void pd_http_setConnectionClosedCallback(HTTPConnection *conn, pd_http_connection_callback callback)
{
    pd_http()->setConnectionClosedCallback(conn, callback);
}

void pd_http_setConnectTimeout(HTTPConnection *conn, int ms)
{
    pd_http()->setConnectTimeout(conn, ms);
}

void pd_http_setReadTimeout(HTTPConnection *conn, int ms)
{
    pd_http()->setReadTimeout(conn, ms);
}

void pd_http_setReadBufferSize(HTTPConnection *conn, int bytes)
{
    pd_http()->setReadBufferSize(conn, bytes);
}
