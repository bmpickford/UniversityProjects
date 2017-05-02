#ifndef __PLAYER_H__
#define __PLAYER_H__

#include "curses.h"
#include "cab202_simple_sprite.h"

// INSERT DECLARATIONS OF ANY NEW FUNCTIONS OR GLOBAL VARIABLES HERE

/*
 *	setup_player:
 *
 *	Initialises the player-related aspects of the game,
 *	creating the icon for the player and initialising other state related 
 *	to the player.
 */
extern sprite_id player[64];
extern sprite_id npc[64];
extern sprite_id ball[10];

void create_player_sprite();
void setup_player();

/*
 *	reset_player:
 *
 *	Restores the player to its original position and appearance.
 */
void setup_ball();
void setup_npc();
bool move_ball();

int ball_bounce();
 
void reset_player();
void reset_ball();
void reset_npc();

bool update_ball();

void print_border();
void print_scores();
void update_scoreboard();
bool update_keys();
int curve_bounce();
void game_over();

void activate_right();
void reset_right();


/*
 *	Interprets the key code and updates the position of the player sprite 
 *	accordingly. 
 *
 *	Input:
 *		key_code:	an integer that represents a key code.
 *
 *	Output:
 *		Returns TRUE if and only if the player moved, indicating that the screen
 *		needs to be refreshed.
 */

bool update_player( int key_code );

#endif
