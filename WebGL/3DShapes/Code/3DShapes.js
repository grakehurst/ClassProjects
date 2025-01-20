var gl;
var myShaderProgram;

// ratation around each axis
var alpha;
var beta;
var zeta;
var moveX;
var moveY;
var moveZ;

// movement on each axis
var MUniform;
var MJS;
var txJS;
var tyJS;
var tzJS;
var xDirection;
var yDirection;

//scaling
var sxJS;
var syJS;
var szJS;
var xScaling;
var yScaling;


function init() {
    var canvas=document.getElementById("gl-canvas");
    gl=WebGLUtils.setupWebGL(canvas);
    
    if (!gl) { alert( "WebGL is not available" ); }
    
    gl.viewport( 0, 0, 750, 750 );
    
    gl.clearColor( 0.0, 0.0, 0.0, 1.0 );
    
    
    myShaderProgram =
        initShaders( gl,"vertex-shader", "fragment-shader" );
    gl.useProgram( myShaderProgram );
    
    // will include depth test to render faces correctly!
    gl.enable( gl.DEPTH_TEST );

    alpha = 0.0;
    gl.uniform1f( gl.getUniformLocation( myShaderProgram, "alpha"), alpha );
    beta = 0.0;
    gl.uniform1f( gl.getUniformLocation( myShaderProgram, "beta"), beta );
    zeta = 0.0;
    gl.uniform1f( gl.getUniformLocation( myShaderProgram, "zeta"), zeta );

    // variable initializations
    //roation
    moveX = 0.0;
    moveY = 0.0;
    moveZ = 0.0;
    
    //movement
    txJS = 0.0;
    tyJS = 0.0;
    tzJS = 0.0;
    xDirection = 0.0;
    yDirection = 0.0;

    //scaling
    sxJS = 1.0;
    syJS = 1.0;
    szJS = 1.0;
    xScaling = 0.0;
    yScaling = 0.0;

    setupTetrahedron();
    
    render();

}

function setupTetrahedron() {
    
    // Vertices of Tetrahedron
    var vertices = [
                 vec4(  0.0,  0.2,   0.0,  1), // p0: top
                 vec4( -0.2, -0.2,  -0.2,  1), // p1: back left
                 vec4(  0.2, -0.2,  -0.2,  1), // p2: back right
                 vec4( -0.2, -0.2,   0.2,  1), // p3: front left
                 vec4(  0.2, -0.2,   0.2,  1), // p4: front right
                ]; 

    // Colors at Vertices of Cube
    var vertexColors = [
                        vec4( 0.0, 0.0, 1.0, 1.0), // p0
                        vec4( 0.0, 1.0, 0.0, 1.0), // p1
                        vec4( 1.0, 0.0, 0.0, 1.0), // p2
                        vec4( 1.0, 1.0, 0.0, 1.0), // p3
                        vec4( 1.0, 0.0, 1.0, 1.0), // p4
                        ]; 

    // Every face on the cube is divided into two triangles,
    // each triangle is described by three indices into
    // the array "vertices"
    var indexList = [
                     0, 1, 2,
                     0, 1, 3,
                     0, 3, 4,
                     0, 4, 2,
                     1, 3, 2,
                     2, 3, 4
                    ];
    
    // Code here to handle putting above lists into buffers
    var vertexBuffer = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, vertexBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(vertices), gl.STATIC_DRAW );
    
    var myPosition = gl.getAttribLocation( myShaderProgram, "myPosition" );
    gl.vertexAttribPointer( myPosition, 4, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( myPosition );
    
    var colorBuffer = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, colorBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(vertexColors), gl.STATIC_DRAW );
    
    var myColor = gl.getAttribLocation( myShaderProgram, "myColor" );
    gl.vertexAttribPointer( myColor, 4, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( myColor );
    
    // will populate to create buffer for indices
    var iBuffer = gl.createBuffer();
    gl.bindBuffer( gl.ELEMENT_ARRAY_BUFFER, iBuffer );
    gl.bufferData( gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexList), gl.STATIC_DRAW );

    
}

// move the shape based on key presses
function startRotateKeys(event){

    //get the key pressed
    var keyCode = event.keyCode;

    //based on the key, change rotation, translation, or scaling
    //if x is pressed, start rotating on x axis
    //if y is pressed, start rotating on y axis
    //if z is pressed, start rotating on z axis
    //if w or s are pressed, translate up or down on x axis
    //if a or d are pressed, translate left or right on y axis
    //if r is pressed, reset
    if(keyCode == 88)        // x
        moveX = 1.0;
    else if(keyCode == 89)   // y
        moveY = 1.0;
    else if(keyCode == 90)   // z
        moveZ = 1.0;
    else if(keyCode == 87)   // w
        yDirection = 1.0;
    else if(keyCode == 83)   // s
        yDirection = -1.0;
    else if(keyCode == 65)   // a
        xDirection = -1.0;
    else if(keyCode == 68)   // d
        xDirection = 1.0;
    else if(keyCode == 39)   // right
        xScaling = 1.0;
    else if(keyCode == 37)   // left
        xScaling = -1.0;
    else if(keyCode == 38)   // up
        yScaling = 1.0;
    else if(keyCode == 40)   // down
        yScaling = -1.0;
    else if(keyCode == 82)   // r
        reset();

}

//stop moving the shape when the key is released
function stopRotateKeys(event){

    //get the key pressed
    var keyCode = event.keyCode;

    //based on the key, stop action
    if(keyCode == 88)        // x
        moveX = 0.0;
    else if(keyCode == 89)   // y
        moveY = 0.0;
    else if(keyCode == 90)   // z
        moveZ = 0.0;
    else if(keyCode == 87)   // w
        yDirection = 0.0;
    else if(keyCode == 83)   // s
        yDirection = 0.0;
    else if(keyCode == 65)   // a
        xDirection = 0.0;
    else if(keyCode == 68)   // d
        xDirection = 0.0;
    else if(keyCode == 39)   // right
        xScaling = 0.0;
    else if(keyCode == 37)   // left
        xScaling = 0.0;
    else if(keyCode == 38)   // up
        yScaling = 0.0;
    else if(keyCode == 40)   // down
        yScaling = 0.0;

}

//function to reset the shape to beginning
function reset(){
    
    init();

    xDirection = 0.0;
    yDirection = 0.0;
    alpha = 0.0; 
    beta = 0.0;
    zeta = 0.0;
    
}

function render() {
    
    // determine if the shape should be moving
    // x axis
    alpha += (moveX * 0.025);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "alpha"), alpha);
    
    // y axis
    beta += (moveY * 0.025);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "beta"), beta);

    // z axis
    zeta += (moveZ * 0.025);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "zeta"), zeta);

    //movement
    //x axis
    txJS = txJS +(xDirection * 0.025);
    tyJS = tyJS +(yDirection * 0.025);
    tzJS = 0.0;
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "txJS"), txJS);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "tyJS"), tyJS);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "tzJS"), tzJS);

    //scaling
    sxJS = sxJS + (xScaling * 0.025);
    syJS = syJS + (yScaling * 0.025);
    szJS = 0.0;
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "sxJS"), sxJS);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "syJS"), syJS);
    gl.uniform1f(gl.getUniformLocation(myShaderProgram, "szJS"), szJS);
    
    
    gl.clear( gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT );
    
    // will populate to render the tetrahedron
    gl.drawElements( gl.TRIANGLES, 18, gl.UNSIGNED_SHORT, 0 );

    requestAnimationFrame( render );
}


