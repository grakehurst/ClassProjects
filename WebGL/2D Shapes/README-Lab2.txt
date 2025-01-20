Gavin Akehurst
CEG4500 Computer Graphics Lab 2
7 October 2024


Lab2 expands on Lab1 material.

Lab2.html sets ups the shaders with a 3x3 matrix. It also sets up a variety of buttons.

Lab2.js initializes the canvas, a shape with 5 vertices, and its rotation and movement.
It calls render to begin the animation. 

The shape begins by moving to the right and not rotating.

----- BUTTONS -----
Start Rotate   : makes the shape start rotating
Stop Rotate    : makes the shape stop rotating
Start Moving   : makes the shape start moving again; moves in the direction it was last moving
Stop Moving    : makes the shape stop moving
Increase Speed : increases the shape's movement speed in whatever direction it was moving
Decrease Speed : decreases the shape's speed, unless the speed is 0
Reset All      : resets all of the shape's characteristics to the beginning
                 (speed, direction, rotation)

----- KEY/MOUSE PRESSES -----
W : Makes the shape move straight up on the canvas
A : Makes the shape move straight left on the canvas
S : Makes the shape move straight down on the canvas
D : Makes the shape move straight right on the canvas
Mouse Click: makes the shape appear where the mouse is on the canvas 
             and continues moving in the same direction at the same speed as before