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

uniform vec2 u_id;

#ifdef OUT_DEPTH
uniform vec4 u_cameraSettings;
varying vec3 v_position;
#endif

void main(){
#ifdef OUT_DEPTH
	float depth = clamp(length(v_position - u_cameraSettings.xyz) * u_cameraSettings.w, 0.0, 1.0);
	// vec4(depth, fract(depth * 255.0)
	gl_FragColor = vec4(u_id.x, u_id.y, depth, 0.0);
#else
	gl_FragColor = vec4(u_id.x, u_id.y, 0.0, 0.0);
#endif

}
