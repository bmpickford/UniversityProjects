
#include <avr/io.h> 
#include <util/delay.h> 
#include <graphics.h>

#include "friendly_ports.h"
#include "sprite.h"

#include <stdlib.h>
#include <stdio.h>
#include "graphics.h"
#include "start.h"
#include "main.h"

int z_turn_counter = 0;
int NUM_ZOMBIES = 8;

void zombie_death(Sprite *zombie){
	zombie->x = 0;
	zombie->y = 50;
	zombie->is_visible = 0;
	increase_score(2);
	NUM_ZOMBIES--;
}
int getZombie(void){
	return NUM_ZOMBIES;
}
void resetZombies(void){
	NUM_ZOMBIES = 8;
}
void setup_z(Sprite zombie[8], byte *bitmap, Sprite *player, Sprite *sword, byte *s_bitmap){
	int xrndm;
	int yrndm;
	int xrndm1, xrndm2, yrndm1, yrndm2;
	for (int i = 0; i < 8; i++){
		xrndm1 = 1 + (rand() % (LCD_X / 2 - 10));
		yrndm1 = 1 + (rand() % (LCD_Y / 2 - 10));
		xrndm2 = (LCD_X / 2 + 10) + (rand() % ((LCD_X - 3) - (LCD_X / 2 + 12)));
		yrndm2 = (LCD_Y / 2 + 10) + (rand() % ((LCD_Y - 3) - (LCD_Y / 2 + 12)));
		if (xrndm1 % 2 == 0){
			xrndm = xrndm1;
		}
		else {
			xrndm = xrndm2;
		}
		if (yrndm1 % 2 == 0){
			yrndm = yrndm1;
		}
		else {
			yrndm = yrndm2;
		}
		init_sprite(&zombie[i], xrndm, yrndm, 3, 3, bitmap);
		zombie[i].is_visible = 1;
		draw_sprite(&zombie[i]);
	}
	int s_xrndm = 1 + (rand() % (LCD_X - 3));
	int s_yrndm = 1 + (rand() % (LCD_Y - 3));
	init_sprite(sword, s_xrndm, s_yrndm, 3, 3, s_bitmap);

	draw_sprite(sword);
	refresh();
	//turn_on(LED1);
}
void z_turn(Sprite *zombie, int dir){
	if (dir == 0){
		z_turn_counter--;
	}
	else if (dir == 1){
		z_turn_counter++;
	}
	
	if (z_turn_counter > 3){
		z_turn_counter = 0;
	}
	if (z_turn_counter < 0){
		z_turn_counter = 3;
	}
	switch (z_turn_counter){
		case 0 :
			zombie->dx = 0;
			zombie->dy = -1;
			//zombie->x = zombie->x + 1;
			//turn_off(LED0);
			break;
		case 1 :
			zombie->dx = 1;
			zombie->dy = 0;
			//zombie->y = zombie->y + 1;
			//turn_on(LED1);
			break;
		case 2 :
			zombie->dx = 0;
			zombie->dy = 1;
			//zombie->x = zombie->x - 1;
			//turn_on(LED0);
			break;
		case 3 :
			zombie->dx = -1;
			zombie->dy = 0;
			//zombie->y = zombie->y - 1;
			//turn_off(LED1);
			break;
	}
}
void z_play_turn(Sprite *zombie[8]){
 	for (int i = 0; i < 8; i++){
		int rand1 = rand() % 100;
		if (rand1 < 5){
			//turn_on(LED0);
			z_turn(zombie[i], 0);
		}
		else if (rand1 < 10){
			//turn_on(LED1);
			z_turn(zombie[i], 1);
		}
/* 		if (zombie[i] > LCD_Y){
			continue;
		} */
		if (zombie[i]->x > LCD_X - 4 && zombie[i]->dx == 1){
			continue;
		}
		else if (zombie[i]->x < 2 && zombie[i]->dx == -1){
			continue;
		}
		else if (zombie[i]->y > LCD_Y - 5 && zombie[i]->dy == 1){
			continue;
		}
		else if (zombie[i]->y < 2 && zombie[i]->dy == -1){
			continue;
		}			
		zombie[i]->x += zombie[i]->dx;
		zombie[i]->y +=zombie[i]->dy;
	}
}

void draw_z(Sprite zombie[8], Sprite *sword, Sprite *grenade){
	for (int i = 0; i < 8; i++){
		draw_sprite(&zombie[i]);
	}
	draw_sprite(sword);
	draw_sprite(grenade);
}
