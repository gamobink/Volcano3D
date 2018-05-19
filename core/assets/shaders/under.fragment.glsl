
varying vec2 v_diffuseUV;

uniform sampler2D u_diffuseTexture;

varying vec3 v_lightDir; 

uniform vec4 u_diffuseColor;

varying vec3 v_normal;

void main() {

	float dot = abs(dot(normalize(v_lightDir), v_normal));
	
	dot = pow(dot, 50) * 0.2;
	
	vec4 diffc = u_diffuseColor;
		
	//If diffuse color r == 0 then use diffuse texture	
	if(u_diffuseColor.x == 0){
		diffc = texture2D(u_diffuseTexture, v_diffuseUV);
	}
		
	vec3 diff = clamp(diffc.rgb + vec3(dot,dot,dot*0.5), 0, 1);
	
	gl_FragColor = vec4(diff, 1);
}
