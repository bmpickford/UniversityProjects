To compile:<br/>  
	Using either the linux terminal or an emulator such as Cygwin: <br/>  
	Go to the ZDK04 folder then execute:
	`make`<br/>  
	This should compile the Zombie library (not written by me)
	Then navigate to the 'Arkapong Skeleton' directory and again, execute
	`make`<br/>  
	After this, type
	`./Arkapong`<br/>  
	Which should execute the file
	
To play:
	At first it should play as a standard pong game using the up and down arrows to move your paddle. From here, if you 		press a number key, this will initiate a certain effect.
	The key presses actions are:<br/>  
	1: Makes left paddle move as in AI <br/>  
	2: Changes the paddles so they are "curved" (not visibly) so the bounce angle will change depending on where on the 		   paddle it <br/>  
	3: Introduces point block (points go up when you hit them)<br/>  
	4: Introduces a block that increases the length of the paddle the ball last hit<br/>  
	5: Introduces a block that decreases the length of the paddle the ball last hit<br/>  
	6: N/A<br/>  
	7: Introduces a wind block that influences the speed of the ball in a certain direction depending on the wind direction<br/>  
	8: Creates a monster that moves around the screen and will cause you to lose a life it eats the ball<br/>  
	The muncher will "eat" point block as well as the ball to give you extra points,
	but it won't affect the increase/decrease/wind blocks.

Known bugs:<br/>  
	- AI doesn't go completely to the top, because for some reason it reduces to a paddle
	 size of 4 if I make the left paddle go all the way to the top. But it doesn't 
	 really have any affect on gameplay i.e. never had a ball fit through that little gap <br/>  
	- Sometimes the decrease action causes one char(219) block to sit near the bottom right
	 after the players paddle has decreased in size <br/>  
	- On occassion, the increase paddle will increase the player paddle by 0, if it
	doesn't work the first go, it usually will increase the second time it hits it. <br/>  
	-Although this isn't really a bug, when the wind is on and makes the ball travel slower,
	 it looks like the ball bounces one away from the walls or paddles. This is just how it
	 looks though because if it is increased, it enters into the paddle instead of bouncing
	 off the edge.


