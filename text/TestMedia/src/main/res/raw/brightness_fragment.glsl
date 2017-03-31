precision mediump float;

uniform sampler2D vTexture;
uniform lowp float brightness;

varying vec2 aCoordinate;

void main(){
    lowp vec4 textureColor = texture2D(vTexture,aCoordinate);
    gl_FragColor = vec4((textureColor.rgb + vec3(brightness)), textureColor.w);
}