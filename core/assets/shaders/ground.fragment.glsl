varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_specularTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
vec4 fog = texture2D(u_specularTexture, v_diffuseUV);

varying float v_fog;

void main() {
	
	gl_FragColor = diffuse;
	
	gl_FragColor.rgb = mix(gl_FragColor.rgb, fog.rgb, v_fog);
	
//	gl_FragColor.a = 0.5;
}
