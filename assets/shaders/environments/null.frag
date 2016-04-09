#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;

uniform sampler2D from, to;
uniform float progress;

const vec4 emptyColor = vec4(0, 0, 0, 0);

void main() {
 // Enforce uniform
 vec4 toColor = texture2D(to, v_texCoord) * emptyColor * progress;
 gl_FragColor = texture2D(from, v_texCoord) + toColor;
}