varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
void main() {
	gl_FragColor = diffuse;	// * vec4(1.0, 1.0, 1.0, 1.0);
}
