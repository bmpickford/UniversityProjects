#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "player.h"

sprite_id point[10];

char point_block[] = 
"##"
"##";

int point_block_height = 2;
int point_block_width = 2;

void setup_point_block(){
	for (int i = 0; i < 10; i++){
		point[i] = create_big_sprite(-10, -10, point_block_width, point_block_height, point_block);
	}	
}

void activate_point_block(){
	screen_dimensions screen = get_screen_size();

	for (int i = 0; i < 10; i++){
		int x_coord = rand() % (screen.width - point_block_width - 5);
		int y_coord = rand() % (screen.height - point_block_height);
		
		if (x_coord < 5){
			x_coord = x_coord + 8;
		}
		
		if (y_coord < 3){
			y_coord = y_coord + 8;
		}
		move_sprite_to(point[i], x_coord, y_coord);
	}

}

void deactivate_point_block(){
	for (int i = 0; i < 10; i++){
		move_sprite_to(point[i], -10, -10);
	}
}

void activate_single_point_block(int index){
	screen_dimensions screen = get_screen_size();
	int x_coord = rand() % (screen.width - point_block_width - 5);
	int y_coord = rand() % (screen.height - point_block_height);
		
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
		
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	move_sprite_to(point[index], x_coord, y_coord);
}

int point_block_hit(){
	int hit = 1;
	int block_top[10];
	int block_left[10];
	int block_bottom[10];
	int block_right[10];
	
	for (int i = 0; i < 10; i++){
		screen_position block_loc = get_sprite_screen_loc(point[i]);
		block_top[i] = block_loc.y -1;
		block_left[i] = block_loc.x - 1;
		block_bottom[i] = block_loc.y + 2;
		block_right[i] = block_loc.x + 2;
	}
	for (int i = 0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);	
		for (int ii = 0; ii < 10; ii++){
			if (ball_location.x > block_left[ii] && ball_location.x < block_right[ii] && ball_location.y > block_top[ii] && ball_location.y < block_bottom[ii]){
				activate_single_point_block(ii);
				move_sprite_backward( ball[i] );
				bounce_sprite_vertical( ball[i] );
				hit = 2;
			}
		}

	}
	return hit;
}

