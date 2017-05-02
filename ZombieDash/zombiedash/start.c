#include <avr/io.h> 
#include <util/delay.h> 
#include <graphics.h>
#include "friendly_ports.h"
#include <stdint.h>
#include <avr/interrupt.h>
#include <stdlib.h>
#include <stdio.h>
#include "start.h"


void start_screen(void){
	draw_string("Benjamin McColm-", 0, 2);
	draw_string("Pickford", 0, 12);
	draw_string("n8847762", 0, 25);
	draw_string("Press any button", 0, 40);
	refresh();
}
void counter(void){
	draw_string("- 3 -", LCD_X / 2 - 7, LCD_Y / 2 - 8);
	refresh();
	_delay_ms(500);
	clear();
	draw_string("- 2 -", LCD_X / 2 - 7, LCD_Y / 2- 8);
	refresh();
	_delay_ms(500);
	clear();
	draw_string("- 1 -", LCD_X / 2- 7, LCD_Y / 2- 8);
	refresh();
	_delay_ms(500);
	clear();
}

void setup_device(void){
	//set_clock_speed( CPU_16MHz );
	DDRB = ( LED0 | LED1 ) & ~( SW0 | SW1 );
	DDRD = LED2; 
	PORTB = 0x00;
	PORTD = 0x00;

	LCDInitialise( LCD_DEFAULT_CONTRAST );
	
	clear();
	start_screen();
	turn_on(LED1);
	int seed = 0;
	while(!pressed(SW1)){
		seed++;
	}
	srand(seed);
	clear();
	refresh();
	turn_off(LED1);
	counter();
}
void win_screen(void){
	draw_string("Congratulations!", 0, 10);
	draw_string("You killed all", 0, 25);
	draw_string("the zombies!", 10, 35);
	refresh();
	turn_on(LED0);
	_delay_ms(1000);
}
void game_over(void){
	draw_string("You lost all your", 0, 10);
	draw_string("lives!", 10, 20);
	draw_string("- GAME OVER -", 10, 35);
	refresh();
	turn_on(LED0);
	_delay_ms(1000);
}

void PinChangeInit(void)
{
	//Enable PCINT0 and PCINT1 (both buttons) in the PCMSK0 register
	PCMSK0 |= (1<<PCINT0);
	PCMSK0 |= (1<<PCINT1);

	//Enable Pin change interrupts in the Pin Change Interrupt Control Register
	PCICR |= (1<<PCIE0);
	
	//Set the PCINT0 and PCINT1 interrupts to trigger on rising edge
	EICRA |= ((1<<ISC00)&&(1<<ISC01)&&(1<<ISC10)&&(1<<ISC11));
		
	//Ensure to enable global interrupts as well.
	sei();
}