#include <avr/io.h> 
#include <util/delay.h> 
#include <graphics.h>
#include <stdbool.h>

#include "cpu_speed.h"
#include "friendly_ports.h"
#include "sprite.h"

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "graphics.h"

#include "start.h"
#include "zombie.h"
#include "player.h"
#include "main.h"

int num_zombies = 8; 

void position_sword(Sprite *player, Sprite *sword){
	if (player->dx == -1 && player->dy == 0){ //LEFT
		sword->x = player->x - 3;
		sword->y = player->y;
	}
	else if (player->dx == 1 && player->dy == 0){ //RIGHT
		sword->x = player->x + 3;
		sword->y = player->y;
	}
	else if (player->dx == 0 && player->dy == -1){ //UP
		sword->x = player->x;
		sword->y = player->y - 3;
	}
		else if (player->dx == 0 && player->dy == 1){ //DOWN
		sword->x = player->x;
		sword->y = player->y + 3;
	}
}
bool check_sword(Sprite *sword, Sprite *player, bool has_sword){
	bool has_sword_1 = false;
	if (has_sword == false){
		if ((abs(player->x - sword->x) < 3) && ((abs(player->y - sword->y)) < 3)){
				has_sword_1 = true;
				increase_score(3);
		}
	}
	else {
		has_sword_1 = true;
	}
	return has_sword_1;
}
bool check_grenade(Sprite *grenade, Sprite *player, bool has_grenade){
	bool has_gren = false;
	if (has_grenade == false){
		if ((abs(player->x - grenade->x) < 3) && ((abs(player->y - grenade->y)) < 3)){
				has_gren = true;
		}
	}
	else {
		has_gren = true;
	}
	return has_gren;
}
void check_z_death(Sprite *sword, Sprite *zombie[num_zombies]){
	for (int i=0; i < 8; i++){
		if ((abs(sword->x - zombie[i]->x) < 3) && ((abs(sword->y - zombie[i]->y)) < 3)){
			zombie_death(zombie[i]);
		}
	}
}

void reset_sword(Sprite *sword, Sprite *player){
	int xrndm = rand() % (LCD_X - 3);
	int yrndm = rand() % (LCD_Y - 3);
	while (xrndm > player->x - 10 && xrndm < player->x + 10 && yrndm > player->y - 10 && yrndm < player->y + 10){
		xrndm = rand() % (LCD_X - 3);
		yrndm = rand() % (LCD_Y - 3);
	}
	sword->x = xrndm;
	sword->y = yrndm;
	sword->is_visible = 1;
	draw_sprite(sword);
}

void sword_indicator(void){
	byte indicate_sword[] = {
		BYTE(01000000),
		BYTE(01000000),
		BYTE(11100000),
	};
	Sprite sword_indicator;
	
	init_sprite(&sword_indicator, LCD_X + 2, 39, 3, 3, &indicate_sword);
	draw_sprite(&sword_indicator);
}
void grenade_indicator(void){
	byte indicate_gren[] = {
		BYTE(11100000),
		BYTE(10100000),
		BYTE(11100000),
	};
	Sprite grenade_indicator;
	
	init_sprite(&grenade_indicator, LCD_X + 7, 39, 3, 3, &indicate_gren);
	draw_sprite(&grenade_indicator);
}

void place_grenade(Sprite *player, Sprite *grenade){
	grenade->x = player->x;
	grenade->y = player->y;
	grenade->is_visible = 1;

}
