
#include <avr/io.h> 
#include <util/delay.h> 
#include <graphics.h>

#include "friendly_ports.h"
#include "sprite.h"

#include <stdlib.h>
#include <stdio.h>
#include "graphics.h"
#include "start.h"
#include <stdbool.h>
#include "extras.h"

int turn_counter = 0;



int setup_p(Sprite *sprite, byte *bitmap, Sprite *grenade, byte *g_bitmap){
	init_sprite(sprite,LCD_X / 2 - 1 , LCD_Y / 2 - 1, 3, 3, bitmap);
	sprite->dx = 0;
	sprite->dy = -1;
	sprite->is_visible = 1;
	draw_sprite(sprite);
	
	int g_xrndm = 1 + (rand() % (LCD_X - 3));
	int g_yrndm = 1 + (rand() % (LCD_Y - 3));
	init_sprite(grenade, g_xrndm, g_yrndm, 3, 3, g_bitmap);
	draw_sprite(grenade);
	
	refresh();
	return 1;
}

void turn(Sprite *player, int dir){
	if (dir == 0){
		turn_counter--;
	}
	else if (dir == 1){
		turn_counter++;
	}
	
	if (turn_counter > 3){
		turn_counter = 0;
	}
	if (turn_counter < 0){
		turn_counter = 3;
	}
	switch (turn_counter){
		case 0 :
			player->dx = 0;
			player->dy = -1;
			//player->x = player->x + 1;
			//turn_off(LED0);
			break;
		case 1 :
			player->dx = 1;
			player->dy = 0;
			//player->y = player->y + 1;
			//turn_on(LED1);
			break;
		case 2 :
			player->dx = 0;
			player->dy = 1;
			//player->x = player->x - 1;
			//turn_on(LED0);
			break;
		case 3 :
			player->dx = -1;
			player->dy = 0;
			//player->y = player->y - 1;
			//turn_off(LED1);
			break;
	}
}
bool play_turn(Sprite *player, bool has_grenade, Sprite *grenade){
	bool blow = false;
	if(pressed(SW0) || pressed(SW1)){
		if (pressed(SW0) && pressed(SW1) && has_grenade == true){
			place_grenade(player, grenade);
			blow = true;
		}
		else if (pressed(SW1)){
			turn(player, 1);
		}
		else if(pressed(SW0)){
			turn(player, 0);
		}
	}
	if (player->y < 2 && player->dy == -1){
		return blow;
	}
	if (player->x < 2 && player->dx == -1){
		return blow;
	}
	if (player->x > (LCD_X - 5) && player->dx == 1){
		return blow;
	}
	if (player->y > (LCD_Y - 5) && player->dy == 1){
		return blow;
	}
	player->x += player->dx;
	player->y += player->dy;
	
	return blow;
}