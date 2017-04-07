#extension GL_OES_EGL_image_external : require

precision mediump float;

uniform samplerExternalOES uTexture;
uniform vec3 uChangeColor;

varying vec2 vCoordinate;

void main(){
    vec4 nColor=texture2D(uTexture,vCoordinate);
    float c=nColor.r*uChangeColor.r+nColor.g*uChangeColor.g+nColor.b*uChangeColor.b;
    gl_FragColor=vec4(c,c,c,nColor.a);
}





