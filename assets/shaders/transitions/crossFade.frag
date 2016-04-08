#ifdef GL_ES
precision highp float;
#endif
uniform sampler2D from, to;
uniform float progress;

varying vec2 v_texCoord0;
varying vec2 v_texCoord1;

void main() {
  gl_FragColor = mix(texture2D(from, v_texCoord0), texture2D(to, v_texCoord1), progress);
}