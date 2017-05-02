#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "player.h"

sprite_id incr_paddle;

char incr_block[] = 
"+ + + + + +";

int block_height = 1;
int block_width = 11;

void setup_incr_paddle(){
	incr_paddle = create_big_sprite(-10, -10, block_width, block_height, incr_block);
}

void activate_increase(){
	screen_dimensions screen = get_screen_size();

	int x_coord = rand() % (screen.width - block_width - 5);
	int y_coord = rand() % (screen.height - block_height);
	
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
	
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	
	move_sprite_to(incr_paddle, x_coord, y_coord);
}

void deactivate_increase(){
	move_sprite_to(incr_paddle, -10, -10);
}

int incr_paddle_hit(){
	int hit = 1;
	screen_position incr_paddle_loc = get_sprite_screen_loc(incr_paddle);

	int incr_paddle_top = incr_paddle_loc.y -1;
	int incr_paddle_left = incr_paddle_loc.x - 1;
	int incr_paddle_bottom = incr_paddle_loc.y + block_height;
	int incr_paddle_right = incr_paddle_loc.x + block_width;

	for (int i = 0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);	
		if (ball_location.x > incr_paddle_left && ball_location.x < incr_paddle_right && ball_location.y > incr_paddle_top && ball_location.y < incr_paddle_bottom){
			activate_increase();
			move_sprite_backward( ball[i] );
			bounce_sprite_horizontal( ball[i] );
			hit = 2;
			
		}
	}
	return hit;
}

void increase_paddle(int score){
	screen_dimensions screen = get_screen_size();
	int player_size = 0;
	int npc_size = 0;

	if (score % 2 != 0){
		for (int i = 0; i < screen.height; i++){
			screen_position player_pos = get_sprite_screen_loc(player[i]);
			if (player_pos.y > 3 && player_pos.y < screen.height){
				player_size++;
			}
		}	
		for (int i = 0; i < player_size; i++){
			move_sprite_to(player[i], screen.width - 5, 3 + i);
		}
		int paddle_height = rand() % screen.height - 3 - player_size;
		while (paddle_height % 2 == 0){
			paddle_height = rand() % screen.height - 3 - player_size;
		}
		for (int i = player_size; i < paddle_height + player_size; i++){
			move_sprite_to(player[i], screen.width - 5, 3 + i);
		}
	}
	else {
		for (int i = 0; i < screen.height; i++){
		screen_position npc_pos = get_sprite_screen_loc(npc[i]);
			if (npc_pos.y > 3 && npc_pos.y < screen.height && npc_pos.x < 8){
				npc_size++;
			}
		}
		if (npc_size > screen.height - 4){
			return;
		}
		for (int i = 0; i < npc_size; i++){
			move_sprite_to(npc[i], 5, 3 + i);
		}
		int npc_height = rand() % screen.height - 3 - npc_size;
		while (npc_height % 2 == 0){
			npc_height = rand() % screen.height - 3 - npc_size;
		}
		for (int i = npc_size; i < npc_height + npc_size; i++){
			move_sprite_to(npc[i], 5, 3 + i);
		}
	}
}


