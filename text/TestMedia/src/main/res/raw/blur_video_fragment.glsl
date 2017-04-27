#extension GL_OES_EGL_image_external : require

precision mediump float;
precision mediump int;

uniform samplerExternalOES uTexture;

const lowp int GAUSSIAN_SAMPLES = 9;

varying highp vec2 vCoordinate; 
varying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES]; 
                     
void main() 
{ 
    lowp vec3 sum = vec3(0.0); 
    
    vec4 fragColor=texture2D(uTexture,vCoordinate);
    
    sum += texture2D(uTexture, blurCoordinates[0]).rgb * 0.16;
    sum += texture2D(uTexture, blurCoordinates[1]).rgb * 0.09;
    sum += texture2D(uTexture, blurCoordinates[2]).rgb * 0.12;
    sum += texture2D(uTexture, blurCoordinates[3]).rgb * 0.09;
    sum += texture2D(uTexture, blurCoordinates[4]).rgb * 0.12;
    sum += texture2D(uTexture, blurCoordinates[5]).rgb * 0.12;
    sum += texture2D(uTexture, blurCoordinates[6]).rgb * 0.09;
    sum += texture2D(uTexture, blurCoordinates[7]).rgb * 0.12;
    sum += texture2D(uTexture, blurCoordinates[8]).rgb * 0.09;
                   
    gl_FragColor = vec4(sum,fragColor.a); 
}