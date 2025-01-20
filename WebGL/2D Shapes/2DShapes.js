//Gavin Akehurst
//CEG4500 Computer Graphics Lab 1
//7 October 2024

var gl;
var shaderProgramSquare;

var MArray;
var thetaJS;

var sxJS;
var syJS;
var txJS;
var tyJS;
var MJS;

var MUniform;

var keepMoving;
var keepRotating;
var xDirection;
var yDirection;
var speedMultiplier;


function init() {
    // Set up the canvas
    var canvas=document.getElementById("gl-canvas");
    gl=WebGLUtils.setupWebGL(canvas);
    if (!gl) { alert( "WebGL is not available" ); }
    // Set up the viewport
    gl.viewport( 0, 0, 750, 750 );   // x, y, width, height
    // Set up the background color
    gl.clearColor( 1.0, 0.0, 0.0, 1.0 );
    
    shaderProgramSquare = initShaders( gl, "vertex-shader-square",
                                      "fragment-shader-square" );
    gl.useProgram( shaderProgramSquare );
    
    // Force the WebGL context to clear the color buffer
    gl.clear( gl.COLOR_BUFFER_BIT );


    //ROTATION:
    thetaJS = 0.0;
    keepRotating = 0.0; // do not start rotating at beginning of program

    //rotation 3x3 matrix
    MArray = [1.0,
        0.0,
        0.0,

        0.0,
        1.0,
        0.0,

        0.0,
        0.0,
        1.0];
    
    //
    
    //MOVEMENT:
    txJS = 0.0;
    tyJS = 0.0;

    // boolean for movement or no movement
    keepMoving = 1.0;

    // direction of the movement
    // default to moving right
    xDirection = 0.005;
    yDirection = 0.0;

    // speed of the movement
    // default to 1
    speedMultiplier = 1.0;
    
    // 3 by 3 for movement
    //3x3: by column
    MJS = [1.0,
        0.0,
        0.0,

        0.0,
        1.0,
        0.0,

        0.0,
        0.0,
        1.0];

    //matrices set up
    MUniform = gl.getUniformLocation( shaderProgramSquare, "M" );
    //MUniform2 = gl.getUniformLocation( shaderProgramSquare, "M" );
    gl.uniformMatrix3fv( MUniform, false, MArray ); //rotation
    gl.uniformMatrix3fv( MUniform, false, MJS ); //movement
    
    setupShape();
    
    requestAnimationFrame( render );
}

// create the shape 
function setupShape() {
    
    // Enter array set up code here
    var p0 = vec2(  0.17,  0.17 );
    var p1 = vec2(  0.0,  0.17 );
    var p2 = vec2( -0.17,  0.0 );
    var p3 = vec2( -0.17,  0.17 );
    var p4 = vec2(  0.0,  0.17 );
    var p5 = vec2(  0.17,  0.0 );
    var arrayOfPoints = [p0, p1, p2, p3, p4, p5];
    
    // Create a buffer on the graphics card,
    // and send array to the buffer for use
    // in the shaders
    var bufferId = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, bufferId );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(arrayOfPoints), gl.STATIC_DRAW );
    
    // Create a pointer that iterates over the
    // array of points in the shader code
    var myPositionAttribute = gl.getAttribLocation( shaderProgramSquare, "myPosition" );
    gl.vertexAttribPointer( myPositionAttribute, 2, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( myPositionAttribute );    
}

// start and stop rotating buttons
function stopRotate(){
    keepRotating = 0;
}
function startRotate(){
    keepRotating = 1;
}


// start and stop movement buttons
function stopMoving(){
    keepMoving = 0;
}
function startMoving(){
    keepMoving = 1;
}

// set w a s d key presses to move the shape
// event passed is a key press
function moveShapeKeys(event){

    //get the key code passed in through the event
    var keyCode = event.keyCode;

    //use the key code to determine how to move
    //only move for wasd
    //use the setDirection helper method to set x and y direction global vairbales
    if(keyCode == 65)        // if a, move left
        setDirection(-0.005, 0.0);
    else if(keyCode == 68)   // if d move right
        setDirection(0.005, 0.0);
    else if(keyCode == 83)   // if s move down
        setDirection(0.0, -0.005);
    else if(keyCode == 87)   // if w move up
        setDirection(0.0, 0.005);


    gl.uniform2f(mouseUniform, clipX, clipY);

}


// function to set the direction of of the shape's movement 
// @param the x and y directions
//        positive x or y means right or up
//        negative x or y means left or down
function setDirection(x, y){
    xDirection = x;
    yDirection = y;
}

// functions to change the speed of the shape's movement
function increaseSpeed(){
    speedMultiplier+=0.75;
}
function decreaseSpeed(){
    //check if going to go negative
    //otherwise stop moving
    if(speedMultiplier > 0.74)
        speedMultiplier-=0.75;
    else
        speedMultiplier = 0;
}

// move the shape with mouse clicks
// event passed is a mouse click on the canvas
function moveShapeMouse(event) {
    var canvasX = event.clientX;
    var canvasY = event.clientY;

    txJS =   2.0 * (canvasX/750.0) - 1.0;
    tyJS = -(2.0 * (canvasY/750.0) - 1.0);
    gl.uniform2f( mouseUniform, clipX, clipY );
}


// button to reset all shape attributes to default
function resetAll(){
    
    //ROTATION:
    thetaJS = 0.0;
    keepRotating = 0.0; // do not start rotating at beginning of program

    //MOVEMENT:
    txJS = 0.0;
    tyJS = 0.0;

    // boolean for movement or no movement
    keepMoving = 1.0;

    // direction of the movement
    // default to moving right
    xDirection = 0.005;
    yDirection = 0.0;

    // speed of the movement
    // default to 1
    speedMultiplier = 1.0;

    //bug: 
    //init() sped up the shape each time reset button  was hit
    //everything else was reset
    //even resetting speedMultiplier or direction didnt help
    //init();
}

function render() {
        
    // update theta to keep rotating at set speed
    thetaJS = thetaJS + (0.05 * keepRotating);

    // update x and y for shape movement, direction, and speed
    txJS = txJS + (xDirection * speedMultiplier * keepMoving);
    tyJS = tyJS + (yDirection * speedMultiplier * keepMoving);

    //MJS = [1 0 tx] and [ cos(theta)  sin(theta)  0]
    //      [0 1 ty]     [ -sin(theta) cos(theta)  0]
    //      [0 0 1 ]     [     0           0       1]

    // both matrices together
    MJS = 
        [Math.cos(thetaJS),
        -Math.sin(thetaJS),
        0.0,
    
        Math.sin(thetaJS),
        Math.cos(thetaJS),
        0.0,

        txJS,
        tyJS,
        1.0];
        
    //send the matrix to gl
    gl.uniformMatrix3fv( MUniform, false, MJS );
    

    //gl.uniform1f( thetaUniform, thetaJS );
    gl.clear( gl.COLOR_BUFFER_BIT );
    gl.drawArrays( gl.TRIANGLE_FAN, 0, 6 );

    requestAnimationFrame( render );
    
}
