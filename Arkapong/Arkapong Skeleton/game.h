#ifndef __GAME_H__
#define __GAME_H__

#include "curses.h"

#include <stdlib.h>
#include <math.h>
#include <string.h>

#include "cab202_simple_sprite.h"
#include "cab202_timer.h"
#include "cab202_screen.h"

#include "player.h"
#include "wind.h"

// INSERT INCLUDES HERE

#define TURN_SCREEN_CHANGED 1
#define TURN_OK 0
#define TURN_GAME_OVER -1

/* 
 *	setup_game:
 *
 *	Initialises the game environment. 
 */


void setup_game();



/* 
 *	reset_game:
 *
 *	restores the game to its initial appearance and behaviour, as if starting over. 
 */

void reset_game();

/* 
 *	play_turn:
 *
 *	Carries out one game cycle:
 *	-	Test for input and timing events;
 *	-	Update the state of all modules.
 *	
 *	Returns:
 *	-	TURN_OK: if all is ok and play continues;
 *	-	TURN_SCREEN_CHANGED: if something happened which changes the view;
 *	-	TURN_GAME_OVER: if the game is over. 
 */

int play_turn();

#endif
