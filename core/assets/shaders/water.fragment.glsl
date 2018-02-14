#ifdef GL_ES 
precision mediump float;
#endif

#define M_PI 3.1415926535897932384626433832795
#define M_PI2 M_PI*2

uniform float u_shininess;

varying vec2 v_diffuseUV;

uniform sampler2D u_diffuseTexture;		//Color material, alpha - water depth
uniform sampler2D u_reflectionTexture;	//Reflection
uniform sampler2D u_ambientTexture;		//Reflection of stretched skybox
uniform sampler2D u_specularTexture;	//Refraction - underwater part
uniform sampler2D u_emissiveTexture;	//DuDv map
uniform sampler2D u_normalTexture;		//Waves normals map
 
varying vec4 v_position;
varying vec3 v_viewDirection; 
varying vec3 v_normal;
varying vec4 v_projectedPos;
varying vec2 v_dudvUV;
varying float v_fog; 
 
const float shineDamper = 60.0;
const float reflectivity = 0.4;  
 
varying vec3 v_lightDir;

const vec3 lightColor = vec3(1,1,0.5);
 
void main() {

	float fog = clamp(pow(v_fog, 8), 0, 1);

	//Projection UVs for reflection and refraction
	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);
	vec2 reflectionUV = vec2(ndc.x, 1.0-ndc.y);
	
	//Animate DuDv map translation		
	vec2 uv1 = v_dudvUV;		
	float n1 = sin((uv1.x * 0.5) + (u_shininess * (M_PI*2))) * 0.5 * fog;
	float n2 = sin((uv1.x * 2) + (u_shininess * (M_PI*2))) * 0.1 * fog;	
	uv1.y -= (n1 + n2) * 0.5;
	uv1.x -= n2;
	//Specular multiplier for large waves effect
	float wavesSpecularMul = pow((n1 + n2 + 2) * 0.5, 5);

	//gl_FragColor = vec4(waves, waves, waves, 1);	return;	
	
//	gl_FragColor = vec4(fog, fog, fog, 1);	return;		
		
	//Normal map sample - normal
	vec4 normalSample = texture2D(u_normalTexture, uv1);		
	vec4 normal = vec4(normalize(vec3(
										-(normalSample.x * 2.0 - 1.0), 
										normalSample.z, 
										-(normalSample.y * 2.0 - 1.0))),
										1.0);
	
	//DuDv map sample - displacement of reflection texture				
	vec2 dispalce1 = (texture2D(u_emissiveTexture, uv1).rg * 2.0 - 1.0) * 0.02;
	
	//Displace reflection UVs
	reflectionUV += dispalce1;
	
	//Clamp to edges	
	reflectionUV.x = clamp(reflectionUV.x, 0.001, 0.999);
	reflectionUV.y = clamp(reflectionUV.y, 0.001, 0.999);
	
	//Sample texture maps
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	vec4 reflection = texture2D(u_reflectionTexture, reflectionUV) * vec4(0.8, 0.8, 0.7, 1);	
	vec4 reflectionStretch = texture2D(u_ambientTexture, reflectionUV);
	
	//View dot Normal
	float d = pow(dot(normalize(-v_viewDirection).xyz, normal.xyz), 1);
	
	//Specular 
	vec3 incidenceVector = normalize(-v_lightDir); //a unit vector
	vec3 reflectionVector = reflect(incidenceVector, normal.xyz); //also a unit vector
	vec3 surfaceToCamera = normalize(v_viewDirection); //also a unit vector
	float cosAngle = max(0.0, dot(surfaceToCamera, reflectionVector));
	float specularCoefficientWide = pow(cosAngle, 8);	
	
	float specularCoefficientHighlight = pow(cosAngle, shineDamper);
	vec3 specHilight = lightColor * specularCoefficientHighlight * reflectivity * wavesSpecularMul;
		
	//Mix reflections by specular highlight
	vec4 relfMix = mix(reflection, reflectionStretch, specularCoefficientWide);

		//vec4(0.2, 0.2, 0.2, 1)
	gl_FragColor = mix(relfMix, relfMix * vec4(0.2, 0.2, 0.2, 1), d) + vec4(specHilight, 0.0);	
	
	//gl_FragColor = vec4(fog,fog,fog, 1);
}


