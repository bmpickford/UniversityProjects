#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#define PI (3.141592653589793)
#include "player.h"
#include "cab202_timer.h"
#include "game.h"
#include "point_block.h"

sprite_id muncher;
//sprite_id muncher1;
timer_id munch_timer;


int muncher_state = 1;
int record = 1;

char first_state[] =
" $$$$$$$$ "
"$ O $$ O $"
"$$$$$$$$$$"
"$$$    $$$"
"$$$____$$$"
" $$$$$$$$ ";

char second_state[] =
" $$$$$$$$ "
"$ o $$ o $"
"$$$$$$$$$$"
"$$$    $$$"
"$$$$$$$$$$"
" $$$$$$$$ ";

char third_state[] =
" $$$$$$$$ "
"$ - $$ - $"
"$$$$$$$$$$"
"$$$$$$$$$$"
"$$$$$$$$$$"
" $$$$$$$$ ";



int muncher_height = 6;
int muncher_width = 10;

void setup_muncher() {
	muncher = create_big_sprite(-20, -20, muncher_width, muncher_height, first_state);
	//muncher1 = create_big_sprite(-20, -20, muncher_width, muncher_height, final_state3);
	munch_timer = create_timer( 150 );

}

void reset_muncher(){
	move_sprite_to(muncher, -40, -40);
	set_sprite_angle(muncher, 160);
	muncher_state = 1;
}

void activate_muncher(){
	screen_dimensions screen = get_screen_size();

	int x_coord = rand() % (screen.width - muncher_width - 5);
	int y_coord = rand() % (screen.height - muncher_height);
	
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
	
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	
	move_sprite_to(muncher, x_coord, y_coord);
	set_sprite_angle(muncher, 160);
}


int move_muncher(){
	int hit=0;
	screen_dimensions screen = get_screen_size();
	screen_position munch_loc = get_sprite_screen_loc(muncher);
	screen_position ball_loc = get_sprite_screen_loc(ball[0]);
	if (munch_loc.x < 0 && record < 900){
		activate_muncher();
	}
	else if (munch_loc.x > 0 && record >= 900){
		reset_muncher();
	}
	else if (record >=1000){
		record = 1;
	}
	else{
		record++;
		if ( ! timer_expired( munch_timer ) ) {
			return hit;
		}
		
		int l_mouth = 1;
		int r_mouth = 9;
		int t_mouth = 2;
		int b_mouth = 6;
		for (int i = 0; i < 10; i++){
			screen_position point_loc = get_sprite_screen_loc(point[i]);
			if (point_loc.x > munch_loc.x + l_mouth && point_loc.x < munch_loc.x + r_mouth){
				if (point_loc.y > munch_loc.y + t_mouth && point_loc.y < munch_loc.y + b_mouth){
					activate_single_point_block(i);
					hit = 3;
					return hit;
				}
			}
		}
		
		if (ball_loc.x > munch_loc.x + l_mouth && ball_loc.x < munch_loc.x + r_mouth){
			if (ball_loc.y > munch_loc.y + t_mouth && ball_loc.y < munch_loc.y + b_mouth){
				hit = 1;
			}
		}
		
		if (hit == 1){
			sleep(1);
			return hit;
		}
		
		if (muncher_state <= 12){
			if (muncher_state <= 4){
				set_sprite_image(muncher, muncher_width, muncher_height, first_state);
			}
			else if(muncher_state <= 8){
				set_sprite_image(muncher, muncher_width, muncher_height, second_state);
			}
			else {
				set_sprite_image(muncher, muncher_width, muncher_height, third_state);
			}
			muncher_state++;
		}
		else {
			muncher_state = 1;
		}
		

		if (munch_loc.x > 6 && munch_loc.x < screen.width - 6 - muncher_width && munch_loc.y > 3 && munch_loc.y < screen.height - muncher_height){
			move_sprite_forward(muncher);
		}
		else {
			if (munch_loc.x < 8 || munch_loc.x > screen.width - 8 - muncher_width){
				move_sprite_backward(muncher);
				bounce_sprite_vertical(muncher);
			}
			else{
				move_sprite_backward(muncher);
				bounce_sprite_horizontal(muncher);
			}
		}
	}
	
	return hit;
}


