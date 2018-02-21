varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_ambientTexture;
void main() {
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	vec4 depth = texture2D(u_ambientTexture, v_diffuseUV);
	
	gl_FragColor = vec4(diffuse.rgb, depth.x);// * vec4(1.0, 0.0, 1.0, 1.0);
}
