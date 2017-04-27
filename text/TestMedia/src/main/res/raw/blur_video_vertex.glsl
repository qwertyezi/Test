attribute vec4 aPosition;
attribute vec4 aCoordinate;

uniform mat4 uMatrix;
uniform mat4 uSTMatrix;
uniform float texelWidth;
uniform float texelHeight;

const int GAUSSIAN_SAMPLES = 9;

varying vec2 vCoordinate;
varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];

void main()
{
    gl_Position=uMatrix*aPosition;
    vCoordinate=(uSTMatrix*aCoordinate).xy;

    float blurSize=2.0;
//    vec2 singleStepOffset = vec2(blurSize/texelHeight, blurSize/texelWidth);
    vec2 singleStepOffset = vec2(2.0, 2.0);

    blurCoordinates[0]=vCoordinate;
    blurCoordinates[1]=vec2(vCoordinate.x-singleStepOffset.x,vCoordinate.y+singleStepOffset.y);
    blurCoordinates[2]=vec2(vCoordinate.x,vCoordinate.y+singleStepOffset.y);
    blurCoordinates[3]=vec2(vCoordinate.x+singleStepOffset.x,vCoordinate.y+singleStepOffset.y);
    blurCoordinates[4]=vec2(vCoordinate.x-singleStepOffset.x,vCoordinate.y);
    blurCoordinates[5]=vec2(vCoordinate.x+singleStepOffset.x,vCoordinate.y);
    blurCoordinates[6]=vec2(vCoordinate.x-singleStepOffset.x,vCoordinate.y-singleStepOffset.y);
    blurCoordinates[7]=vec2(vCoordinate.x,vCoordinate.y-singleStepOffset.y);
    blurCoordinates[8]=vec2(vCoordinate.x+singleStepOffset.x,vCoordinate.y-singleStepOffset.y);
}