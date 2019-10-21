attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projModelView;

varying vec4 v_col;
varying vec3 v_position;
varying vec2 v_texCoord0;

void main() {
	v_col = a_color;
	v_position = a_position.xyz;
	v_texCoord0 = a_texCoord0;
	gl_Position = u_projModelView * a_position;
}
