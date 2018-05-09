varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
void main() {
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	gl_FragColor = vec4(vec3(0.1, 0.5, 0.4), diffuse.a);	
}
