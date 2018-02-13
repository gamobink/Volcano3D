#ifdef GL_ES 
precision mediump float;
#endif

uniform float u_shininess;

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
varying float v_fog; 
 
//const float shineDamper = 15.0;
//const float reflectivity = 0.2; 
 
const float shineDamper = 60.0;
const float reflectivity = 0.4;  
 
varying vec3 v_lightDir; 

const vec3 lightColor = vec3(1,1,0.5); 
 
void main() {
	
	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);
	vec2 reflectionUV = vec2(ndc.x, 1.0-ndc.y);
		
	vec2 uv1 = v_dudvUV;		
	uv1.y -= u_shininess;
	
	vec4 normalSample = texture2D(u_normalTexture, uv1);
	
	vec4 normal = vec4(normalize(vec3(
										-(normalSample.x * 2.0 - 1.0), 
										normalSample.z, 
										-(normalSample.y * 2.0 - 1.0))), 
										1.0);
				
	vec2 dispalce1 = (texture2D(u_emissiveTexture, uv1).rg * 2.0 - 1.0) * 0.02 * (v_fog * v_fog);
	
	//vec2 uv2 = v_dudvUV;
	//uv1.y -= u_shininess;
	//uv1.x -= u_shininess;			
	//vec2 dispalce2 = (texture2D(u_emissiveTexture, uv2).rg * 2.0 - 1.0) * 0.01 * v_fog;
	
	reflectionUV += dispalce1; // + dispalce2;
		
	reflectionUV.x = clamp(reflectionUV.x, 0.001, 0.999);
	reflectionUV.y = clamp(reflectionUV.y, 0.001, 0.999);
	
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	vec4 ambient = texture2D(u_ambientTexture, reflectionUV) * vec4(0.8, 0.8, 0.7, 1);

	float d = dot(normalize(-v_viewDirection).xyz, normal.xyz);
	
	vec3 reflectedLight = reflect(normalize(-v_lightDir), normal);//normal
	float specular = max(dot(reflectedLight, normalize(v_viewDirection)), 0.0);
	specular = pow(specular, shineDamper);
	vec3 specHilight = lightColor * specular * reflectivity;
	
	//gl_FragColor = vec4(d,d,d,1);
	//gl_FragColor = normal;
	//gl_FragColor = vec4(0,1,0, 1);
		
	gl_FragColor = mix(ambient, diffuse, d) + vec4(specHilight, 0.0);	//*(v_fog * v_fog)
	
	//gl_FragColor = vec4(v_fog*v_fog, v_fog*v_fog, v_fog*v_fog, 1);		
	//gl_FragColor = vec4(normalize(v_lightDir), 1.0);	
}