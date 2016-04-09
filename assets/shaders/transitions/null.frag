#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D from, to;
uniform float progress;

varying vec2 v_texCoord0;
varying vec2 v_texCoord1;

const vec4 emptyColor = vec4(0, 0, 0, 0);

void main() {
    // Enforce uniform
    vec4 toColor = texture2D(to, v_texCoord1) * emptyColor * progress;
    gl_FragColor = texture2D(from, v_texCoord0) + toColor;
}