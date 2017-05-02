#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "player.h"
#define PI (3.141592653589793)

void get_sprite_angle(){
	simple_sprite_t ball_sprite = get_sprite(ball[0]);
	double dx = ball_sprite.direction.dx;
	double dy = ball_sprite.direction.dy;
	
	double dir = atan2(dy, dx) * (180/PI);
	
	mvprintw(14, 55, "%d", dir);
	
}