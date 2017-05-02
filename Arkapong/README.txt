Basic: Y
1: Y
2: Y
3: Y
4: Y
5: Y
6: N
7: Y
8: Y

Comments:
Known bugs:
	- AI doesn't go completely to the top, because for some reason it reduces to a paddle
	 size of 4 if I make the left paddle go all the way to the top. But it doesn't 
	 really have any affect on gameplay i.e. never had a ball fit through that little gap
	- Sometimes the decrease action causes one char(219) block to sit near the bottom right
	 after the players paddle has decreased in size
	- On occassion, the increase paddle will increase the player paddle by 0, if it
	doesn't work the first go, it usually will increase the second time it hits it.
	-Although this isn't really a bug, when the wind is on and makes the ball travel slower,
	 it looks like the ball bounces one away from the walls or paddles. This is just how it
	 looks though because if it is increased, it enters into the paddle instead of bouncing
	 off the edge.
	 
Although I didn't include the splitter, pressing '6' will still reset all the additional
components

The muncher will "eat" point block as well as the ball to give you extra points,
but it won't affect the increase/decrease/wind blocks.

The ball/point block will also only be eaten if it enters the mouth area. It will just
pass through the top half (this was on purpose)

I added a '9' button which makes the right paddle the height of the window because it made
it easier to test. I decided to keep it in there in case it helps, but it doesn't work
too well with the increase/decrease functions.
