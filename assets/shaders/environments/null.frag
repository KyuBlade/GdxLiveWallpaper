#ifdef GL_ES
precision highp float;
#endif

varying vec2 v_texCoord;

uniform sampler2D from, to;
uniform float progress;


void main() {
    gl_FragColor = texture2D(from, v_texCoord);
}