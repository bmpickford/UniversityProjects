#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "player.h"

 void ai_on(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height - 3; i++){
		move_sprite_to(npc[i], -11, -11);
	}
	for (int i = 0; i < 5; i++){
		move_sprite_to(npc[i], 5, (screen.height - 5) / 2 + i);
	}
}

void ai_off(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height - 3; i++){
		move_sprite_to(npc[i], 5, 3 + i);
	}
}

void ai(){
	int npc_size = 0;
	screen_dimensions screen = get_screen_size();
		
	for (int i = 0; i < screen.height; i++){ //FIND NPC PADDLE SIZE
		screen_position npc_pos = get_sprite_screen_loc(npc[i]);
		if (npc_pos.y > 3 && npc_pos.y < screen.height && npc_pos.x < 8){
			npc_size++;
		}
	}
	
	int med = npc_size/2;
	int bottom = npc_size - 1;
	
	screen_position npc_pos_1 = get_sprite_screen_loc(npc[med]);
	screen_position npc_pos_2 = get_sprite_screen_loc(npc[0]);
	screen_position npc_pos_3 = get_sprite_screen_loc(npc[bottom]);
		
	//CHANGE TO GO THROUGH BALLS / OR FIND CLOSEST BALL
	screen_position ball_pos = get_sprite_screen_loc(ball[0]);
	//END CHANGE	
	
	if (npc_pos_1.y > ball_pos.y && npc_pos_2.y > 4){
		for (int i = 0; i < npc_size; i++){
			move_sprite(npc[i], 0, -1);
		}	
	}
	else if (npc_pos_1.y < ball_pos.y && npc_pos_3.y < screen.height - 1){
		for (int i = 0; i < npc_size; i++){
			move_sprite(npc[i], 0, 1);
		}
	}
}