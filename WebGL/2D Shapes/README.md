# 2D Graphics with WebGL

This program renders and animates a shape with five vertices. The user can interact with the shape with mouse clicks, key presses, and HTML buttons.  

## How it works
Lab2.html sets ups the shaders with a 3x3 matrix. It also sets up a variety of buttons.  

Lab2.js initializes the canvas, a shape with 5 vertices, and its rotation and movement.  
It calls render to begin the animation.  

## Lessons learned
- HTML syntax  
- JavaScript syntax
- 2D shape creation
- 2D animations
- HTML buttons
- HTML canvas interactions through mouse presses and key presses

## Usage
The shape begins by moving to the right and not rotating.  

Buttons: 
- Start Rotate   : makes the shape start rotating  
- Stop Rotate    : makes the shape stop rotating  
- Start Moving   : makes the shape start moving again; moves in the direction it was last moving  
- Stop Moving    : makes the shape stop moving  
- Increase Speed : increases the shape's movement speed in whatever direction it was moving  
- Decrease Speed : decreases the shape's speed, unless the speed is 0  
- Reset All      : resets all of the shape's characteristics to the beginning (speed, direction, rotation) 

Mouse clicks and key presses:  
- W : Makes the shape move straight up on the canvas  
- A : Makes the shape move straight left on the canvas  
- S : Makes the shape move straight down on the canvas  
- D : Makes the shape move straight right on the canvas  
- Mouse Click: makes the shape appear where the mouse is on the canvas and continues moving in the same direction at the same speed as before  

**Demo Link:**  
https://rawcdn.githack.com/grakehurst/ClassProjects/ac348cfe11165633756f741280301402e2c6020f/WebGL/2D%20Shapes/Code/2DShapes.html  