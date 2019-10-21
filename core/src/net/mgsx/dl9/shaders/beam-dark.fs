#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

varying vec4 v_col;
varying vec3 v_position;
varying vec2 v_texCoord0;

uniform float u_time;

uniform sampler2D u_sampler0;

/**
 * TRAPEZE ramp from [0-1] to [0-1] with force (1=>triangle, inf=>square, 2=> half, ..)
 */
#define TRAPEZE(t,f) clamp( (f) * (1.0 - abs( 1.0 - 2.0 * (t) )), 0.0, 1.0)

void main() {
	
	float tra = TRAPEZE(v_texCoord0.x, 2.0);
	// float tra2 = TRAPEZE(v_col.y, 2.0);
	float signal1 = sin(v_texCoord0.y * 2.0 + u_time * 3.387) * 0.25;
	float signal2 = sin(v_texCoord0.y * 3.12 - u_time * 43.456) * 0.25;
	float signal = (signal1 * signal2 * 4.0);
	float l = 1.0 - clamp(abs(signal - v_texCoord0.x + 0.5), 0.0, 1.0);
	if(l > 0.95) l = 2.0;
	else if(l < 0.6) l = 0.0;
	else l = pow(l, 3.0);
	float fl = tra * l;
	float r = l - 1.0;
	vec4 color = vec4(vec3(1.0 - r, 0.0, 0.0), fl);
	// if (gl_FragColor.a <= v_alphaTest) discard;
	// gl_FragColor = color;
	
	
	vec4 tex = texture2D(u_sampler0, v_texCoord0);
	
	// gl_FragColor = vec4(v_texCoord0.x, v_texCoord0.y, tex.r, 1.0);
	gl_FragColor = color * v_col + 0.001 * vec4(tex.rgb, 0.0);
}
