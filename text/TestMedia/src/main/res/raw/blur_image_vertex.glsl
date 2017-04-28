attribute vec4 aPosition;
attribute vec2 aCoordinate;

uniform mat4 uMatrix;
uniform mat4 uSTMatrix;

varying vec2 vCoordinate;

void main()
{
    gl_Position=uMatrix*aPosition;

//    vCoordinate=aCoordinate;

    vCoordinate = (uMatrix*aPosition).xy / 2.0 + 0.5;
}