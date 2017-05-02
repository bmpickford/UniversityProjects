#include "splitter.h"
#include "player.h"
#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "math.h"
#define PI (3.141592653589793)

char splitter_char[] = 
"|-|-|-|-|-|-|";


int split_width = 13;
int split_height = 1;

sprite_id splitter;


void setup_splitter(){
	splitter = create_big_sprite(-10, -10, split_width, split_height, splitter_char);

}

void activate_splitter(){
	screen_dimensions screen = get_screen_size();

	int x_coord = rand() % (screen.width - split_width - 5);
	int y_coord = rand() % (screen.height - split_height);
	
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
	
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	
	move_sprite_to(splitter, x_coord, y_coord);
}

void deactivate_splitter(){
	move_sprite_to(splitter, -10, -10);
	for (int i = 1; i < 10; i++){
		move_sprite_to(-10, -10, ball[i]);
	}
}

int splitter_hit(){
	int hit = 1;
	screen_position splitter_loc = get_sprite_screen_loc(splitter);

	int splitter_top = splitter_loc.y -1;
	int splitter_left = splitter_loc.x - 1;
	int splitter_bottom = splitter_loc.y + split_height;
	int splitter_right = splitter_loc.x + split_width;

	for (int i = 0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);	
		if (ball_location.x > splitter_left && ball_location.x < splitter_right && ball_location.y > splitter_top && ball_location.y < splitter_bottom){
			activate_splitter();
			move_sprite_backward(ball[i]);
			bounce_sprite_horizontal(ball[i]);
			hit = 2;
		}
	}
	return hit;
}

void split_ball(){
	screen_position ball_location = get_sprite_screen_loc(ball[0]);
	simple_sprite_t ball_sprite = get_sprite(ball[0]);
	move_sprite_to(ball[1], ball_location.x, ball_location.y);
	set_sprite_direction(ball[1], ball_sprite.direction.dx, ball_sprite.direction.dy);
	bounce_sprite_horizontal(ball[1]);
/* 	screen_dimensions screen = get_screen_size();
	int top = 3;
	int left = 0;
	int right = screen.width;
	int bottom = screen.height;
	for (int i=0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);
		screen_position prev_ball_location = get_sprite_screen_loc(ball[i -1]);
		if (ball_location.x < right && ball_location.x > left && ball_location.y < bottom && ball_location.y > top){
			break;
		}
		else{
			move_sprite_to(ball[i], prev_ball_location.x, prev_ball_location.y);
			move_sprite_backward(ball[i]);
		}
	} */
}


