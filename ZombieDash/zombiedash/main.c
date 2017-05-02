#include <avr/io.h> 
#include <util/delay.h> 
#include <graphics.h>
#include <stdbool.h>

#define F_CPU 8000000UL
#define CPU_PRESCALE(n) (CLKPR = 0x80, CLKPR = (n))
#define SCORE_COUNT 150
#define BOMB_COUNT 90

#include "cpu_speed.h"
#include "friendly_ports.h"
#include "sprite.h"

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <stdint.h>
#include <avr/interrupt.h>
#include "graphics.h"

#include "extras.h"
#include "start.h"
#include "zombie.h"
#include "player.h"

int score = 0;
int score_2 = 0;
int lives = 3;
int num_z = 8;
int z_counter = 0;
int zombie_count = 8;
volatile int clock = 0;
bool blow_up = false;
bool has_grenade = false;
Sprite gren;


void TimerInit(void) {
	TCCR0A &= ~((1<<WGM01)|(WGM00));
	TCCR0B &= ~((1<<WGM02));
	TCCR0B |= (1<<CS02)|(1<<CS00);
	TCCR0B &= ~((1<<CS01));	
	TIMSK0 |= (1<<TOIE0);
	sei();
}

ISR(TIMER0_OVF_vect) {
	TIFR0 |= (1<<TOV0);
	clock++;

	if(clock >= SCORE_COUNT) {
		clock = 0;
		score++;
		if (score % 10 == 0){
			score = 0;
			score_2++;
		}
		else if (score > 10){
			score_2++;
			score = score % 10;
		}
		refresh();
	}
	if (clock >= BOMB_COUNT && blow_up == true){
		blow_up = false;
		has_grenade = false;
	}
}
void increase_score(int increase){
	score += increase;
}
bool check_death(Sprite *zombie[num_z], Sprite *player){
	bool dead = false;
	
	for (int i=0; i < num_z; i++){
			if ((abs(player->x - zombie[i]->x) < 3) && ((abs(player->y - zombie[i]->y)) < 3)){
				dead = true;
				lives--;
		}
	}
	
	return dead;
}

void border(void){
	draw_y_line(0, 0, LCD_Y);
	draw_y_line(LCD_X, 0, LCD_Y);
	draw_x_line(0, 0, LCD_X);
	draw_x_line(LCD_Y - 1, 0, LCD_X);
	
	draw_string("L:", LCD_X + 1, 0);
	draw_character('0'+lives,LCD_X + 2, 10);
	draw_string("S:", LCD_X + 1, 20);

	draw_character('0'+score_2,LCD_X + 1, 30);
	draw_character('0'+score,LCD_X + 7, 30);
}

void reset(Sprite *zombie[num_z], Sprite *player, int reset){
	player->x = LCD_X / 2 + 3;
	player->y = LCD_Y / 2 + 3;
	int xrndm, xrndm1, xrndm2;
	int yrndm, yrndm1, yrndm2;
	if (reset == 0){
		for (int i = 0; i < zombie_count; i++){
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
			zombie[i]->x = xrndm;
			zombie[i]->y = yrndm;
		}
		for (int ii = zombie_count; ii < num_z; ii++){
			zombie[ii]->x = -1;
			zombie[ii]->y = 50;
			zombie[ii]->dx = 0;
			zombie[ii]->dy = 0;
		}
	}
	else {
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
			zombie[i]->x = xrndm;
			zombie[i]->y = yrndm;
			zombie[i]->is_visible = 1;
		}

	}
	
}

void game(void){
	clear();

	Sprite one;
	Sprite zombie[num_z];
	Sprite sword;

	byte p_bitmap[] = {
		BYTE(10100000),
		BYTE(11100000),
		BYTE(10100000),
	};
	
	byte z_bitmap[] = {
		BYTE(01000000),
		BYTE(11100000),
		BYTE(01000000),
	};
	
	byte sword_bitmap[] = {
		BYTE(01000000),
		BYTE(01000000),
		BYTE(11100000),
	};
	
	byte grenade_bit[] = {
		BYTE(11100000),
		BYTE(10100000),
		BYTE(11100000),
	};
	
	Sprite *grenade = &gren;
	Sprite *player = &one;
	byte *player_bitm = &p_bitmap;
	Sprite *zombies[num_z];
	Sprite *sword_p = &sword;
	byte *sword_p_bitmap = &sword_bitmap;
	
	for (int i = 0; i < num_z; i++){
		int randx = rand() % 100;
		int randy = rand() % 100;
 		if (randx % 3 == 0){
			zombie[i].dx = 0;
		}
		else if (randx % 2 == 0){
			zombie[i].dx = -1;
		}
		else {
			zombie[i].dx = 0;
		}
		if (zombie[i].dx == 0){
			if (randy % 2 == 0){
				zombie[i].dy = -1;
			}
			else {
				zombie[i].dy = 1;
			}
		}
		else {
			zombie[i].dy = 0;
		}
		zombies[i] = &zombie[i];
	}
	

	setup_z(zombie, &z_bitmap, player, sword_p, sword_p_bitmap);
	setup_p(player, player_bitm, grenade, &grenade_bit);
	//_delay_ms(1000);
	
	bool has_sword = false;
	gren.is_visible = 1;
  	while(1){
		zombie_count = getZombie();
		if (zombie_count < 1){
			zombie_count = 8;
			clear();
			resetZombies();
			reset(zombies, player, 1);
			has_sword = false;
			reset_sword(sword_p, player);
			win_screen();
			setup_device();
			lives = 3;
			score = 0;
			score_2 = 0;
		}
		z_counter++;
		border();
		check_z_death(sword_p, zombies);
		blow_up = play_turn(player, has_grenade, grenade);
		if (has_sword == true){
			position_sword(player, sword_p);
			sword_indicator();
		}
		if (has_grenade == true){
			grenade_indicator();
			gren.is_visible = 0;
		}
		else if (blow_up == true){
			gren.is_visible = 1;
		}
		else {
			has_grenade = check_grenade(grenade, player, has_grenade);
		}
		if (z_counter % 10 == 0){
			z_play_turn(zombies);
		}
		draw_sprite(player);
		draw_z(zombie, sword_p, grenade);
		has_sword = check_sword(sword_p, player, has_sword);
		bool dead = check_death(zombies, player);
		
 		if (dead == true){
			reset(zombies, player, 0);
			has_sword = false;
			reset_sword(sword_p, player);
		}
		if (lives != 3 && lives != 2 && lives != 1){
			clear();
			zombie_count = 8;
			resetZombies();
			reset(zombies, player, 1);
			has_sword = false;
			reset_sword(sword_p, player);
			game_over();
			setup_device();
			lives = 3;
			score = 0;
			score_2 = 0;
		}
		refresh();
		_delay_ms(100);
		turn_off(LED1);
		turn_off(LED0);
		clear();
	}
}

int main( void ) {

	CPU_PRESCALE(CPU_8MHz);

	setup_device();
	TimerInit();

	game();

	
	return 0;
}
