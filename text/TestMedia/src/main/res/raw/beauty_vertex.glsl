attribute vec4 aPosition;
attribute vec2 aCoordinate;

varying vec2 vCoordinate;
varying vec2 vBlurCoord1s[14];

const highp float mWidth=720.0;
const highp float mHeight=1280.0;

uniform mat4 uMatrix;
uniform mat4 uSTMatrix;

void main( )
{
    gl_Position = uMatrix*aPosition;
    vCoordinate=aCoordinate;

    highp float mul_x = 2.0 / mWidth;
    highp float mul_y = 2.0 / mHeight;

    vBlurCoord1s[0] = vCoordinate + vec2( 0.0 * mul_x, -10.0 * mul_y );
    vBlurCoord1s[1] = vCoordinate + vec2( 8.0 * mul_x, -5.0 * mul_y );
    vBlurCoord1s[2] = vCoordinate + vec2( 8.0 * mul_x, 5.0 * mul_y );
    vBlurCoord1s[3] = aCoordinate.xy + vec2( 0.0 * mul_x, 10.0 * mul_y );
    vBlurCoord1s[4] = aCoordinate.xy + vec2( -8.0 * mul_x, 5.0 * mul_y );
    vBlurCoord1s[5] = aCoordinate.xy + vec2( -8.0 * mul_x, -5.0 * mul_y );

    mul_x = 1.2 / mWidth;
    mul_y = 1.2 / mHeight;

    vBlurCoord1s[6] = aCoordinate.xy + vec2( 0.0 * mul_x, -6.0 * mul_y );
    vBlurCoord1s[7] = aCoordinate.xy + vec2( -4.0 * mul_x, -4.0 * mul_y );
    vBlurCoord1s[8] = aCoordinate.xy + vec2( -6.0 * mul_x, 0.0 * mul_y );
    vBlurCoord1s[9] = aCoordinate.xy + vec2( -4.0 * mul_x, 4.0 * mul_y );
    vBlurCoord1s[10] = aCoordinate.xy + vec2( 0.0 * mul_x, 6.0 * mul_y );
    vBlurCoord1s[11] = aCoordinate.xy + vec2( 4.0 * mul_x, 4.0 * mul_y );
    vBlurCoord1s[12] = aCoordinate.xy + vec2( 6.0 * mul_x, 0.0 * mul_y );
    vBlurCoord1s[13] = aCoordinate.xy + vec2( 4.0 * mul_x, -4.0 * mul_y );
}