precision mediump float;

uniform sampler2D vTexture;
uniform vec3 vChangeColor;

varying vec2 aCoordinate;

void main(){
    vec4 nColor=texture2D(vTexture,aCoordinate);
    float c=nColor.r*vChangeColor.r+nColor.g*vChangeColor.g+nColor.b*vChangeColor.b;
    gl_FragColor=vec4(c,c,c,nColor.a);
}





