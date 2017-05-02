#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "player.h"

sprite_id decr_paddle;

char decr_block[] = 
"- - - - - -";

int decr_block_height = 1;
int decr_block_width = 11;

void setup_decr_paddle(){
	decr_paddle = create_big_sprite(-10, -10, decr_block_width, decr_block_height, decr_block);
}

void activate_decrease(){
	screen_dimensions screen = get_screen_size();

	int x_coord = rand() % (screen.width - decr_block_width - 5);
	int y_coord = rand() % (screen.height - decr_block_height);
	
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
	
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	
	move_sprite_to(decr_paddle, x_coord, y_coord);
}

void deactivate_decrease(){
	move_sprite_to(decr_paddle, -10, -10);
}

int decr_paddle_hit(){
	int hit = 1;
	screen_position decr_paddle_loc = get_sprite_screen_loc(decr_paddle);

	int decr_paddle_top = decr_paddle_loc.y -1;
	int decr_paddle_left = decr_paddle_loc.x - 1;
	int decr_paddle_bottom = decr_paddle_loc.y + decr_block_height;
	int decr_paddle_right = decr_paddle_loc.x + decr_block_width;

	for (int i = 0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);	
		if (ball_location.x > decr_paddle_left && ball_location.x < decr_paddle_right && ball_location.y > decr_paddle_top && ball_location.y < decr_paddle_bottom){
			activate_decrease();
			move_sprite_backward( ball[i] );
			bounce_sprite_horizontal( ball[i] );
			hit = 2;			
		}
	}
	return hit;
}

void decrease_paddle(int score){

	screen_dimensions screen = get_screen_size();
	int player_size = 0;
	int npc_size = 0;
	if (score == 0){
		score++;
	}

	if (score % 2 != 0){
		for (int i = 0; i < screen.height; i++){
			screen_position player_pos = get_sprite_screen_loc(player[i]);
			if (player_pos.y > 3 && player_pos.y < screen.height && player_pos.x > screen.width / 2){
				player_size++;
			}
		}	
		int range  = player_size - 3;
		int paddle_height = 3 + rand() % range;
		while (paddle_height % 2 == 0){
			paddle_height = 3 + rand() % range;
		}

		for (int i = 0; i < paddle_height; i++){
			move_sprite_to(player[i], screen.width - 5, 3 + i);
		}
		for (int i = paddle_height; i < player_size; i++){
			move_sprite_to(player[i], -10, -10);
		}

	}
	else {
		for (int i = 0; i < screen.height; i++){
		screen_position npc_pos = get_sprite_screen_loc(npc[i]);
			if (npc_pos.y > 3 && npc_pos.y < screen.height && npc_pos.x < 8){
				npc_size++;
			}
		}
		if (npc_size >= screen.height - 4){
			return;
		}

		int npc_range  = npc_size - 3;
		int npc_height = 3 + rand() % npc_range;
		while (npc_height % 2 == 0){
			npc_height = 3 + rand() % npc_range;
		}
		for (int i = 0; i < npc_height; i++){
			move_sprite_to(npc[i], 5, 3 + i);				
		}
		for (int i = npc_height; i < npc_size; i++){
			move_sprite_to(npc[i], -10, -10);
		}
	} 
}


