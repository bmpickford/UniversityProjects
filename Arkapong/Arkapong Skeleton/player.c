#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include "cab202_timer.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#define PI (3.141592653589793)
#include "player.h"



// INSERT GLOBAL VARIABLES HERE



char pong = 219;
//char npc_pong[] = {219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219
//,219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219, 219};
char ball_character = 'o';


sprite_id player[64];
sprite_id npc[64];
sprite_id ball[10];

timer_id ball_timer;
timer_id wind1;
timer_id wind2;

#define PLAYER_WIDTH 1
#define PLAYER_HEIGHT 5

#define NPC_W 1

#define SMALL_NPC_H 5
#define MED_NPC_H 15

//-----------------------------------------------------------------------------
//  Function declarations.
//-----------------------------------------------------------------------------

// INSERT DECLARATIONS FOR HELPER FUNCTIONS HERE


//-----------------------------------------------------------------------------
//  Function implementations.
//-----------------------------------------------------------------------------


void setup_player() {
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height; i++){
		player[i] = create_sprite(-10, -10, pong,0);
	}
	for (int i = 0; i < 5; i++){
		move_sprite_to(player[i], screen.width - 5,(screen.height/2) - 3+i);
	}
	wind1 = create_timer(30);
	wind2 = create_timer(80);

}

void setup_npc(){
	screen_dimensions screen = get_screen_size();
	
	for (int i = 0; i < screen.height - 3; i++){
		npc[i] = create_sprite(5, 3+i, pong, 0);
	}
}

void setup_ball(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < 10; i++){
		ball[i] = create_sprite(-5, -8, ball_character,0);
	}
	move_sprite_to(ball[0], screen.width/2,(screen.height/2) + 3);
	
	int startingDirection = 155 + rand() % 50;
	set_sprite_angle(ball[0], startingDirection);
	ball_timer = create_timer( 40 );
}

 bool move_ball(int option){

	screen_dimensions screen = get_screen_size();
	
	int bottom = screen.height - 1;
	int top = 3;

	if (option == 0 || option == 1){
		mvprintw(0, screen.width / 2 - 4, "              ");
		if (option == 1){
			mvprintw(0, screen.width / 2 - 4, "Wind: Nil");
		}
		if ( ! timer_expired( ball_timer ) ) {
			return false;
		}

		
		screen_position ball_pos = get_sprite_screen_loc( ball[0] );
		if (ball_pos.y > top && ball_pos.y < bottom){
			for (int i = 0; i < 64; i++){
				screen_position npc_location = get_sprite_screen_loc( npc[i] );
				if (ball_pos.x == npc_location.x + 1 && ball_pos.y == npc_location.y){
					return true;
				}
			}
			for (int i=0; i < 64; i++){
				screen_position player_location = get_sprite_screen_loc( player[i] );
				if (ball_pos.x == player_location.x - 1 && ball_pos.y == player_location.y){
					return true;
				}
			}			
			move_sprite_forward(ball[0]);
			return false;
		}
		return true;
	}
	else {
		screen_position ball_pos = get_sprite_screen_loc( ball[0] );
		if (ball_pos.y > top && ball_pos.y < bottom){
			for (int i = 0; i < 64; i++){
				screen_position npc_location = get_sprite_screen_loc( npc[i] );
				if (ball_pos.x == npc_location.x + 1 && ball_pos.y == npc_location.y){
					return true;
				}
			}
			for (int i=0; i < 64; i++){
				screen_position player_location = get_sprite_screen_loc( player[i] );
				if (ball_pos.x == player_location.x - 1&& ball_pos.y == player_location.y){
					return true;
				}
			}		
			simple_sprite_t ball_sprite = get_sprite(ball[0]);
			screen_position ball_pos1 = get_sprite_screen_loc( ball[0] );
			if (ball_pos1.y > top && ball_pos1.y < bottom && ball_pos1.x > 5 && ball_pos1.x < screen.width - 5){
				if (option == 2){
					mvprintw(0, screen.width / 2 - 4, "Wind: <- ");
					if (ball_sprite.direction.dx < 0){
						if ( ! timer_expired( wind1 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
					else if (ball_sprite.direction.dx > 0){
						if ( ! timer_expired( wind2 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
				}
				else if (option == 3){
					mvprintw(0, screen.width / 2 - 4, "Wind: ->");
					if (ball_sprite.direction.dx > 0){
						if ( ! timer_expired( wind1 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
					else if (ball_sprite.direction.dx < 0){
						if ( ! timer_expired( wind2 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
				}
				else if (option == 4){
					mvprintw(0, screen.width / 2 - 4, "Wind: ^ ");
					if (ball_sprite.direction.dy < 0){
						if ( ! timer_expired( wind1 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
					else if (ball_sprite.direction.dy > 0){
						if ( ! timer_expired( wind2 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
				}
				else if (option == 5){
					mvprintw(0, screen.width / 2 - 4, "Wind: v ");
					if (ball_sprite.direction.dy > 0){
						if ( ! timer_expired( wind1 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
					else if (ball_sprite.direction.dy < 0){
						if ( ! timer_expired( wind2 ) ) {
							return false;
						}
						move_sprite_forward(ball[0]);
					}
				}
			}
		}
		return true;
	}
	return true;
	
	return false;	
}

bool update_ball(){

	screen_dimensions screen = get_screen_size();
	int balls = 10;
	bool ball_off_screen = false;
	for (int i = 0; i < 10; i++){
		screen_position ball_position = get_sprite_screen_loc(ball[i]);
		if (ball_position.x < 0 || ball_position.x > screen.width){
			balls--;
		}
	}

	if (balls == 0){
		ball_off_screen = true;
	}
	return ball_off_screen;
}

int ball_bounce(){

	screen_dimensions screen = get_screen_size();
	int score = 0;
	
	for (int i = 0; i < 10; i++){
		screen_position ball_position = get_sprite_screen_loc( ball[i] );
		if (ball_position.x > 0 && ball_position.y > 0){
			if (ball_position.x < 7 || ball_position.x > screen.width - 8){
				move_sprite_backward( ball[i] );
				bounce_sprite_vertical( ball[i] );
				score = 1;
			}
		
			else if (ball_position.y < 4 || ball_position.y > screen.height - 2){
				move_sprite_backward( ball[i] );
				bounce_sprite_horizontal( ball[i] );
				score = 0;
			}
		}
	
	}
return score;

}
int curve_bounce(){
	screen_dimensions screen = get_screen_size();
	int score = 0;

	
	int player_size = 0;
	int npc_size = 0;

	double angle;
	
	for (int i = 0; i < screen.height; i++){
		screen_position player_pos = get_sprite_screen_loc(player[i]);
		if (player_pos.y > 3 && player_pos.y < screen.height){
			player_size++;
		}
	}
	for (int i = 0; i < screen.height; i++){
		screen_position npc_pos = get_sprite_screen_loc(npc[i]);
		if (npc_pos.y > 3 && npc_pos.y < screen.height){
			npc_size++;
		}
	}	

	for (int i = 0; i < 10; i++){
		screen_position ball_position = get_sprite_screen_loc( ball[i] );

		if (ball_position.x > 0 && ball_position.y > 0){
			
			if (ball_position.x < 10){
				double npc_size_2 = npc_size / 2;
				double angle_2 = 10 / npc_size_2;
		
				for (int ii = npc_size_2; ii > 0; ii--){
					screen_position npc_p1 = get_sprite_screen_loc(npc[ii]);

					if (ball_position.x == npc_p1.x + 1 && ball_position.y == npc_p1.y){
						angle = (angle_2 * ii);
						
						move_sprite_backward( ball[0] );
						bounce_sprite_vertical(ball[0]);
						simple_sprite_t ball_sprite = get_sprite(ball[0]);
						if (ball_sprite.direction.dy < 0){
							angle = angle * -1;
						}
						turn_sprite(ball[0], angle);
						
						score = 1;
					}
				}
				for (int iii = npc_size_2; iii <= npc_size; iii++){
					screen_position npc_p2 = get_sprite_screen_loc(npc[iii]);

					if (ball_position.x == npc_p2.x + 1 && ball_position.y == npc_p2.y){
						angle = (angle_2 * (iii - npc_size_2) * -1);
						
						move_sprite_backward( ball[0] );
						bounce_sprite_vertical(ball[0]);
						simple_sprite_t ball_sprite = get_sprite(ball[0]);
						if (ball_sprite.direction.dy > 0){
							angle = angle * -1;
						}
						turn_sprite(ball[0], angle);
						
						score = 1;
					}
				}
			}
			else {
				double padd_size_2 = player_size / 2;
				double angle_2_2 = 10 / padd_size_2;
		
				for (int ii = 0; ii < padd_size_2; ii++){
					screen_position padd_p1 = get_sprite_screen_loc(player[ii]);

					if (ball_position.x == padd_p1.x - 1 && ball_position.y == padd_p1.y){
						angle = (angle_2_2 * ii);
						
						move_sprite_backward( ball[i] );
						bounce_sprite_vertical(ball[i]);

						turn_sprite(ball[i], angle);
						
						score = 1;
					}
				}
				for (int iii = padd_size_2; iii < screen.height; iii++){
					screen_position padd_p2 = get_sprite_screen_loc(player[iii]);

					if (ball_position.x == padd_p2.x - 1 && ball_position.y == padd_p2.y){
						angle = (angle_2_2 * (iii - padd_size_2) * -1);
						
						move_sprite_backward( ball[i] );
						bounce_sprite_vertical(ball[i]);
						turn_sprite(ball[i], angle);
						
						score = 1;
					}
				}
			}
			if (ball_position.y < 4 || ball_position.y > screen.height - 2){
				move_sprite_backward( ball[i] );
				bounce_sprite_horizontal( ball[i] );
				score = 0;
			}
		}
	
	}
return score;

}



void reset_player() {
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < 5; i++){
		move_sprite_to(player[i], screen.width - 5, (screen.height / 2) + i);
	}
}
void reset_ball(){
	screen_dimensions scr = get_screen_size();
	move_sprite_to( ball[0], scr.width / 2 ,  scr.height / 2 );
		int startingDirection = 155 + rand() % 50;
	set_sprite_angle(ball[0], startingDirection);
}
void reset_npc(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height - 3; i++){
		move_sprite_to(npc[i], 5, 3+i);
	}
}



bool update_player( int key_code ) { //Based on player.c form wk04-Impl
	bool player_moved = FALSE;
	
	screen_position player_location_top = get_sprite_screen_loc( player[0] );
	screen_dimensions screen = get_screen_size();
	int player_bottom;
	int record;
	for (int i = 0; i < screen.height; i++){
		screen_position paddle_pos = get_sprite_screen_loc(player[i]);
		if (paddle_pos.y < 3 || paddle_pos.y > screen.height){
			screen_position player_position_bottom = get_sprite_screen_loc(player[i-1]);
			player_bottom = player_position_bottom.y;
			record = i;
			break;
		}
	}
	int top = 3;
	int bottom = screen.height - 1;

	if ( key_code == KEY_UP && player_location_top.y > top ) {
		for (int i = 0; i < record; i++){
			move_sprite( player[i], 0, -1 );
			player_moved = true;
		}

	}
	else if ( key_code == KEY_DOWN && player_bottom < bottom ) {
		for (int i = 0; i < record; i++){
			move_sprite( player[i], 0, 1 );
			player_moved=true;
		}
	}
	
	return player_moved;
}

void print_border(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i <= screen.width; i++){
		mvaddch(2, i, '_');
	}
}
void print_scores(){
	
	mvprintw(1, 0, "1: OFF");
	mvprintw(1, 8, "2: OFF");
	mvprintw(1, 16, "3: OFF");
	mvprintw(1, 24, "4: OFF");
	mvprintw(1, 32, "5: OFF");
	mvprintw(1, 40, "6: OFF");
	mvprintw(1, 48, "7: OFF");
	mvprintw(1, 56, "8: OFF");
	mvprintw(1, 64, "9: OFF");
}

bool update_keys(int score, int lives, int numbers[], char keys[], int key_code){
	int x = 3;
	bool changed = false;
	screen_dimensions screen = get_screen_size();
	mvprintw(0, 0, "Score: %d", score);
	mvprintw(0, screen.width - 9, "Lives: %d", lives);
	for (int i = 0; i < 9; i++){
		if (key_code == keys[i]){
			changed = true;
			numbers[i]++;
			if (numbers[i] % 2 == 0){
				mvprintw(1, x, "OFF");
			}
			else{
				mvprintw(1, x, "ON ");
			}	
		}
	x = x + 8;
	}	
	return changed;
}


void game_over(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height; i++){
		move_sprite_to(npc[i], 0, -3);
	}
	for (int i = 0; i < screen.height; i++){
		move_sprite_to(player[i], -10, -10);
	}
	move_sprite_to(ball[0], 0, -1);
	while (getch() != 'r'){
		mvprintw(screen.height / 2, screen.width / 2 - 20, "GAME OVER: Press 'r' to restart");
	}
}

void activate_right(){
	screen_dimensions screen = get_screen_size();
	
	for (int i = 0; i < screen.height; i++){
		move_sprite_to(player[i], screen.width - 5, 3 + i);
	}
}

void reset_right(){
	screen_dimensions screen = get_screen_size();
	for (int i = 0; i < screen.height; i++){
		move_sprite_to(player[i], -10, -15);
	}
	for (int ii = 0; ii < 5; ii++){
		move_sprite_to(player[ii], screen.width - 5, 3 + ii);
	}
}
// INSERT HELPER FUNCIONS HERE
