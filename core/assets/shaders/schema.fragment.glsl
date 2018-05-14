varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

varying vec3 v_normal;
varying vec3 v_viewDirection;

void main() {
	float dot = abs(dot(normalize(v_viewDirection), v_normal));
	dot = pow(dot, 4);
	gl_FragColor = vec4(diffuse.xyz, dot * diffuse.w);
}
