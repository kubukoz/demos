//
//  main.c
//  Extension
//
//  Created by Dave Hayden on 7/30/14.
//  Copyright (c) 2014 Panic, Inc. All rights reserved.
//

#include "pd_api.h"
#include <stdio.h>

static int update(void *userdata);
const char *fontpath = "/System/Fonts/Asheville-Sans-14-Bold.pft";
LCDFont *font = NULL;

int clickedGowno = 0;
void menuItemCallback(void *userdata)
{
  clickedGowno = 1;
}

int clickedA = 0;
#ifdef _WINDLL
__declspec(dllexport)
#endif
    int eventHandler(PlaydateAPI *pd, PDSystemEvent event, uint32_t arg)
{
  (void)arg; // arg is currently only used for event = kEventKeyPressed

  if (event == kEventInit)
  {

    PDMenuItem *menuItem = pd->system->addMenuItem("gowno", menuItemCallback, pd);
    const char *err;
    font = pd->graphics->loadFont(fontpath, &err);

    if (font == NULL)
      pd->system->error("%s:%i Couldn't load font %s: %s", __FILE__, __LINE__,
                        fontpath, err);

    // Note: If you set an update callback in the kEventInit handler, the system
    // assumes the game is pure C and doesn't run any Lua code in the game
    pd->system->setUpdateCallback(update, pd);
    // clickedA = 0;
  }
  else if (event == kEventKeyPressed)
  {
    clickedA = 1;
  }

  return 0;
}

#define TEXT_WIDTH 86
#define TEXT_HEIGHT 16

int x = (400 - TEXT_WIDTH) / 2;
int y = (240 - TEXT_HEIGHT) / 2;
int dx = 1;
int dy = 2;

int color = 0;

static int update(void *userdata)
{
  PlaydateAPI *pd = userdata;

  pd->graphics->clear(kColorWhite);
  pd->graphics->setFont(font);

  pd->display->setInverted((color / 10) % 2);
  color += 1;

  char *str = clickedGowno ? "GOWNO CLICKED" : "GOWNO";

  PDButtons *current = malloc(sizeof(PDButtons));
  PDButtons *pushed = malloc(sizeof(PDButtons));

  pd->system->getButtonState(current, pushed, NULL);

  if ((*current & kButtonA) != 0)
  {
    clickedA = 1;
  }
  else if ((*pushed & kButtonA) != 0)
  {
    clickedA = 1;
  }
  else
    clickedA = 0;

  pd->graphics->drawText(str, strlen(str), kUTF8Encoding, 100, 0);
  if (clickedA)
    pd->graphics->drawText("A CLICKED", strlen("A CLICKED"), kUTF8Encoding, 100, 20);

  char *text = "UYSY CHUJ!";
  pd->graphics->drawText(text, strlen(text), kUTF8Encoding, x, y);

  pd->graphics->drawEllipse(50, 50, 50, 50, 2, 0, 0, kColorBlack);
  pd->graphics->drawEllipse(150, 50, 50, 50, 2, 0, 0, kColorBlack);
  pd->graphics->drawEllipse(250, 50, 50, 50, 2, 0, 0, kColorBlack);
  pd->graphics->drawEllipse(150, 150, 50, 50, 2, 0, 0, kColorBlack);

  x += dx;
  y += dy;

  if (x < 0 || x > LCD_COLUMNS - TEXT_WIDTH)
    dx = -dx;

  if (y < 0 || y > LCD_ROWS - TEXT_HEIGHT)
    dy = -dy;

  pd->system->drawFPS(0, 0);

  return 1;
}
