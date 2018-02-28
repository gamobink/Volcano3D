varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

varying vec3 v_lightDir; 

uniform vec4 u_diffuseColor;

varying vec3 v_normal;

void main() {

	float dot = dot(normalize(v_lightDir), v_normal);
//	gl_FragColor = vec4( normalize(v_lightDir), 1);

	dot = pow(dot, 50) * 0.2;
	vec4 diffc = u_diffuseColor;
	
	vec3 diff = clamp(diffc.rgb + vec3(dot,dot,dot*0.5), 0, 1);
	
	gl_FragColor = vec4(diff, 1);
}
