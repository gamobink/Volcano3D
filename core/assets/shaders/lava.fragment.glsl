varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_specularTexture;

vec4 brightness = texture2D(u_specularTexture, v_diffuseUV);

uniform float u_shininess;

varying vec3 v_lightDir; 
varying vec3 v_normal;

void main() {

	float dot = abs(dot(normalize(v_lightDir), v_normal));
	
	dot = pow(dot, 20) * 0.5;
	
	vec3 diff = clamp(vec3(dot,dot,dot*0.5), 0, 1);	
	
	vec2 uvDiff1 = v_diffuseUV * 5;
	uvDiff1.y = uvDiff1.y + u_shininess;

	vec2 uvDiff2 = v_diffuseUV * 20;
	uvDiff2.y = uvDiff2.y + u_shininess + 0.3;
	
	vec4 diffuse1 = texture2D(u_diffuseTexture, uvDiff1);
	vec4 diffuse2 = texture2D(u_diffuseTexture, uvDiff2);	
	
	vec4 color = mix((diffuse1 * diffuse2), (diffuse1 + diffuse2), brightness.x);
	color.w = 1;
	
	color.rgb = color.rgb + ((diff) * color.x);
	
	gl_FragColor = color;

}
