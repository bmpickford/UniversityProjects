#include "game.h"
#include "player.h"
#include "wind.h"
#include "splitter.h"
#include "incr_paddle.h"
#include <unistd.h>
#include "ai.h"
#include "decr_paddle.h"
#include "point_block.h"
#include "curved_paddle.h"
#include "muncher.h"
//#include "player_ai.h"

int key_numbers [9];
char key_codes[] = "123456789";
int scores;
int lives = 3;
int hits = 0;
//int splitter = 1;


void setup_game() {
	auto_refresh_sprites = FALSE;
	auto_sprite_delay = FALSE;
	auto_wrap_sprites = FALSE;

	setup_screen();
	
	// INSERT_CODE_HERE
	setup_player();
	setup_wind_sprite();
	setup_muncher();
	setup_incr_paddle();
	setup_point_block();
	setup_decr_paddle();
	setup_splitter();
	setup_ball();
	setup_npc();
	print_scores();
	
	print_border();

	
	refresh();
}


void reset_game() {
	hits = 0;
	reset_right();
	for (int i =0; i < 8; i++){
		if (key_numbers[i] % 2 != 0){
			key_numbers[i]++;
		}
	}

	reset_screen();
	clear();
	ai_off();
	reset_wind();
	deactivate_splitter();
	deactivate_point_block();

	// INSERT_CODE_HERE
	reset_player();
	reset_ball();
	reset_npc();
	print_border();
	print_scores();
	deactivate_decrease();
	deactivate_increase();
	reset_muncher();
	refresh();
}

int play_turn() {
	
	
	if ( update_screen() ) {
		reset_game();
		return TURN_OK;
	}

	/* If we have a key, process it immediately. */
	int key_code = getch();

	if ( key_code == 'q' || key_code == 'Q' ) {
		return TURN_GAME_OVER;
	}
	else if ( key_code == 'r' || key_code == 'R' ) {
		reset_game();
		return TURN_OK;
	}
	
	if ( update_player( key_code ) ) {
		return TURN_SCREEN_CHANGED;
	}
	

	//CHANGES FROM KEY PRESS

	bool change = update_keys(scores, lives, key_numbers, key_codes, key_code);
	
	//START 1 AI
	if (key_numbers[0] % 2 != 0 && change == true){
		ai_on();
	}
	else if (key_numbers[0] % 2 != 0){
		ai();
	}
	else{
		ai_off();
	}
	//END 1
	

	
	
	//START 3 - POINT BLOCKS
	if (key_numbers[2] % 2 != 0 && change == true){
		activate_point_block();
	}
	else if (key_numbers[2] % 2 != 0){
		if (point_block_hit() == 2){
			scores++;
		}
	}
	else{
		deactivate_point_block();
	}
	//END 3
	
	//START 4 - INCREASE PADDLE
	if (key_numbers[3] % 2 != 0 && change == true){
		activate_increase();
	}
	else if (key_numbers[3] % 2 != 0){
		if (incr_paddle_hit() == 2){
			scores++;
			increase_paddle(scores);
		}
	}
	else{
		deactivate_increase();
	}
	//END 4
	
	//START 5 - DECREASE PADDLE
	if (key_numbers[4] % 2 != 0 && change == true){
		activate_decrease();
	}
	else if (key_numbers[4] % 2 != 0){
		if (decr_paddle_hit() == 2){
			scores++;
			decrease_paddle(scores);
		}
	}
	else{
		deactivate_decrease();
	}
	//END 5
	
	//START 6 - SPLITTER
/* 	if (key_numbers[5] % 2 != 0 && change == true){	
		activate_splitter();	
	}
	else if (key_numbers[5] % 2 != 0){
				
		if (splitter_hit() == 2){
			split_ball();
			splitter++;
		}
		if (splitter < 10){
			return 0;
		}

	}
	else{
		deactivate_splitter();
	} */
	//END 6
	
	
	//START 7 - WIND
	if (key_numbers[6] % 2 != 0 && change == true){	
		activate_wind_sprite();
		hits = 1;
	}
	else if (key_numbers[6] % 2 != 0){

		if (block() == 2){
			scores++;
			hits++;
			if (hits > 5){
				hits = 1;
			}
		}
		//wind_effect(hits);
		
	}
	else{
		hits = 0;
		reset_wind();
	}
	//END 7
	
	//START 8 - BALL MUNCHER
	if (key_numbers[7] % 2 != 0 && change == true){	
		activate_muncher();
	}
	if (key_numbers[7] % 2 != 0){
		int move_munch = move_muncher();
		if (move_munch >= 1){
			if (move_munch == 1){
			lives--;
			reset_game();
			}
			else if (move_munch == 3){
				scores++;
			}
		}
	}
	else {
		reset_muncher();
	}
	//END 8
	
	if (key_numbers[8] % 2 != 0 && change == true){	
		activate_right();
	}
	else if (change == true){
		reset_right();
	}
	
	if ( move_ball(hits) ){
		int score;
		if (key_numbers[1] % 2 != 0){
			score = curve_bounce();
		}
		else{
			score = ball_bounce();
		}
		if (score >= 1){
			scores++;
		}
	}
	
	
	
	if (update_ball() ){
		reset_game();
		lives--;
		return TURN_OK;
	}
	
	if (lives == 0){
		game_over();
		
		lives = 3;
		scores = 0;
		reset_game();
	}

	return TURN_OK;
}
