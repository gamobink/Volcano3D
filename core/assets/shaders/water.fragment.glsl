#ifdef GL_ES 
precision mediump float;
#endif

varying vec2 v_diffuseUV;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_ambientTexture;
uniform sampler2D u_emissiveTexture;
uniform sampler2D u_normalTexture;
 
varying vec4 v_position;
varying vec3 v_viewDirection; 
varying vec3 v_normal;
varying vec4 v_projectedPos;
varying vec2 v_dudvUV;
 
void main() {
	
	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);
	vec2 reflectionUV = vec2(ndc.x, 1.0-ndc.y);
		
	vec2 uv1 = v_dudvUV;		

	vec4 normalSample = texture2D(u_normalTexture, uv1);
	
	vec4 normal = vec4(normalize(vec3(
										-(normalSample.x * 2.0 - 1.0), 
										normalSample.z, 
										-(normalSample.y * 2.0 - 1.0))), 
										1.0);
				
	vec2 dispalce1 = (texture2D(u_emissiveTexture, uv1).rg * 2.0 - 1.0) * 0.02;
	
	reflectionUV += dispalce1;
		
	reflectionUV.x = clamp(reflectionUV.x, 0.001, 0.999);
	reflectionUV.y = clamp(reflectionUV.y, 0.001, 0.999);
	
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	vec4 ambient = texture2D(u_ambientTexture, reflectionUV);

	//float d = pow(dot(normalize(-v_viewDirection).xyz, normalize(normal).xyz), 0.8);	//normal
	float d = dot(normalize(-v_viewDirection).xyz, normalize(normal).xyz);
	
	gl_FragColor = vec4(d,d,d,1);
	//gl_FragColor = normal;
	//gl_FragColor = vec4(0,1,0, 1);
		
	gl_FragColor = mix(ambient,diffuse, d);
}