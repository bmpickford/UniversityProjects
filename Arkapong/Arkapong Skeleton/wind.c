#include "wind.h"
#include "player.h"
#include "cab202_simple_sprite.h"
#include "cab202_screen.h"
#include <ncurses.h>
#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "math.h"
#define PI (3.141592653589793)



char wind_block[] =
"< - - + - - >";

int wind_block_height = 1;
int wind_block_width = 13;
sprite_id wind;

void setup_wind_sprite(){
	wind = create_big_sprite(-10, -10, wind_block_width, wind_block_height, wind_block);
}

void activate_wind_sprite(){
	screen_dimensions screen = get_screen_size();

	int x_coord = rand() % (screen.width - wind_block_width - 5);
	int y_coord = rand() % (screen.height - wind_block_height);
	
	if (x_coord < 5){
		x_coord = x_coord + 8;
	}
	
	if (y_coord < 3){
		y_coord = y_coord + 8;
	}
	
	move_sprite_to(wind, x_coord, y_coord);
}

void reset_wind(){
	screen_dimensions screen = get_screen_size();
	move_sprite_to(wind, -10, -10);
	mvprintw(0, screen.width / 2 - 2, "      ");
	for (int i = 0; i < 7; i++){
		mvaddch(screen.height / 2 - 2 + i, 1, ' ');
	}
}

 void wind_effect(int option){
	screen_dimensions screen = get_screen_size();
	int top = 3;
	int left = 0;
	int right = screen.width;
	int bottom = screen.height;
	for (int i=0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);
		if (ball_location.x < right && ball_location.x > left && ball_location.y < bottom && ball_location.y > top){
			if (option == 1){
				mvprintw(0, screen.width / 2 - 2,"No Wind");
				for (int i = 0; i < 7; i++){
					mvaddch(screen.height / 2 - 2 + i, 1, ' '); //Removes side directions
				}
				return;
			}
			else if (option == 2){
				mvprintw(0, screen.width / 2 - 2, "<----- ");
				int counter1;
				if (counter1 % 2 == 0){
					move_sprite(ball[i], -1, 0);
					mvaddch(23, 58, ' ');
				}
				counter1++;
			}
			else if (option == 3){
				mvprintw(0, screen.width / 2 - 2, "----->");
				int counter2;
				if (counter2 % 2 == 0){
					move_sprite(ball[i], 1, 0);
					
				}
				counter2++;
			}
			else if (option == 4){
				mvprintw(screen.height / 2 - 2, 1, "^");
				for (int i = 0; i < 5; i++){
					mvaddch(screen.height / 2 - 1 + i, 1, '|');
				}
				mvprintw(0, screen.width / 2 - 2, "      "); //Removes top directions
				int counter3;
				if (counter3 % 3 == 0){
					move_sprite(ball[i], 0, -1);
				}
				counter3++;
			}
			else if (option == 5){
				for (int i = 0; i < 5; i++){
					mvaddch(screen.height / 2 - 2 + i, 1, '|');
				}
				mvprintw(screen.height / 2 + 4, 1, "v");
				int counter4;
				if (counter4 % 3 == 0){
					move_sprite(ball[i], 0, 1);

				}
				counter4++;;
			}
		}
	}

	
	

}

int block(){
	int hit = 1;
	screen_position wind_block_loc = get_sprite_screen_loc(wind);
	int wind_top = wind_block_loc.y - 1;
	int wind_left = wind_block_loc.x - 1;
	int wind_bottom = wind_block_loc.y + wind_block_height;
	int wind_right = wind_block_loc.x + wind_block_width;
	
	for (int i = 0; i < 10; i++){
		screen_position ball_location = get_sprite_screen_loc(ball[i]);	
		if (ball_location.x > wind_left && ball_location.x < wind_right && ball_location.y > wind_top && ball_location.y < wind_bottom){
			activate_wind_sprite();
			move_sprite_backward(ball[i]);
			bounce_sprite_horizontal(ball[i]);
			hit = 2;
		}
	}
	return hit;
}
