varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

varying vec3 v_lightDir; 

uniform vec4 u_diffuseColor;

varying vec3 v_normal;

void main() {

	float dot = dot(normalize(v_lightDir), v_normal);
//	gl_FragColor = vec4( normalize(v_lightDir), 1);

	dot = pow(dot, 50);
	dot = clamp(dot, 0,1);
	
	
	
	gl_FragColor = u_diffuseColor + vec4(dot,dot,dot, 1);
}
