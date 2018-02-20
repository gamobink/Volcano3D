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

uniform float u_opacity;
 
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

vec4 boxBlur (sampler2D source, vec2 uv) {

	vec2 texOffset = vec2(0.008, 0.008);
	
	vec2 tc0 = uv.st + vec2(-texOffset.s, -texOffset.t);
	vec2 tc1 = uv.st + vec2(         0.0, -texOffset.t);
	vec2 tc2 = uv.st + vec2(+texOffset.s, -texOffset.t);
	vec2 tc3 = uv.st + vec2(-texOffset.s,          0.0);
	vec2 tc4 = uv.st + vec2(         0.0,          0.0);
	vec2 tc5 = uv.st + vec2(+texOffset.s,          0.0);
	vec2 tc6 = uv.st + vec2(-texOffset.s, +texOffset.t);
	vec2 tc7 = uv.st + vec2(         0.0, +texOffset.t);
	vec2 tc8 = uv.st + vec2(+texOffset.s, +texOffset.t);
	
	vec4 col0 = texture2D(source, tc0);
	vec4 col1 = texture2D(source, tc1);
	vec4 col2 = texture2D(source, tc2);
	vec4 col3 = texture2D(source, tc3);
	vec4 col4 = texture2D(source, tc4);
	vec4 col5 = texture2D(source, tc5);
	vec4 col6 = texture2D(source, tc6);
	vec4 col7 = texture2D(source, tc7);
	vec4 col8 = texture2D(source, tc8);

	vec4 sum = (1.0 * col0 + 2.0 * col1 + 1.0 * col2 + 
	            2.0 * col3 + 4.0 * col4 + 2.0 * col5 +
	            1.0 * col6 + 2.0 * col7 + 1.0 * col8) / 16.0; 
	            
	return vec4(sum.rgb, 1.0);	            
}

vec4 blur13(sampler2D image, vec2 uv, vec2 direction) {
	vec2 resolution = vec2(1024,1024);		
  vec4 color = vec4(0.0);
  vec2 off1 = vec2(1.411764705882353) * direction;
  vec2 off2 = vec2(3.2941176470588234) * direction;
  vec2 off3 = vec2(5.176470588235294) * direction;
  color += texture2D(image, uv) * 0.1964825501511404;
  color += texture2D(image, uv + (off1 / resolution)) * 0.2969069646728344;
  color += texture2D(image, uv - (off1 / resolution)) * 0.2969069646728344;
  color += texture2D(image, uv + (off2 / resolution)) * 0.09447039785044732;
  color += texture2D(image, uv - (off2 / resolution)) * 0.09447039785044732;
  color += texture2D(image, uv + (off3 / resolution)) * 0.010381362401148057;
  color += texture2D(image, uv - (off3 / resolution)) * 0.010381362401148057;
  return color;
}
 
vec4 blur9(sampler2D image, vec2 uv, vec2 direction) {
	vec2 resolution = vec2(1024,1024);		
  vec4 color = vec4(0.0);
  vec2 off1 = vec2(1.3846153846) * direction;
  vec2 off2 = vec2(3.2307692308) * direction;
  color += texture2D(image, uv) * 0.2270270270;
  color += texture2D(image, uv + (off1 / resolution)) * 0.3162162162;
  color += texture2D(image, uv - (off1 / resolution)) * 0.3162162162;
  color += texture2D(image, uv + (off2 / resolution)) * 0.0702702703;
  color += texture2D(image, uv - (off2 / resolution)) * 0.0702702703;
  return color;
} 
 
vec4 blur5(sampler2D image, vec2 uv, vec2 direction) {	
	vec2 resolution = vec2(1024,1024);		
	vec4 color = vec4(0.0);
	vec2 off1 = vec2(1.3333333333333333) * direction;
	color += texture2D(image, uv) * 0.29411764705882354;
	color += texture2D(image, uv + (off1 / resolution)) * 0.35294117647058826;
	color += texture2D(image, uv - (off1 / resolution)) * 0.35294117647058826;
	return color; 
} 
 
void main() {

	float fog = clamp(pow(v_fog, 8), 0, 1);

	//Projection UVs for reflection and refraction
	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);
	vec2 reflectionUV = vec2(ndc.x, 1.0-ndc.y);

		
	//Sample texture maps
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	
//	gl_FragColor = vec4(diffuse.r, diffuse.r, diffuse.r, 1);
//	return;
	
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
	normal = vec4(normalize(mix(vec3(0,1,0), normal.xyz, diffuse.r)).xyz, 1);
	
	//DuDv map sample - displacement of reflection texture				
	vec2 dispalce1 = (texture2D(u_emissiveTexture, uv1).rg * 2.0 - 1.0) * 0.02 * diffuse.r;
	
	//Displace reflection UVs
	reflectionUV += dispalce1;
	refractionUV += dispalce1;
	
	//Clamp to edges	
	reflectionUV.x = clamp(reflectionUV.x, 0.001, 0.999);
	reflectionUV.y = clamp(reflectionUV.y, 0.001, 0.999);
	
	refractionUV.x = clamp(refractionUV.x, 0.001, 0.999);
	refractionUV.y = clamp(refractionUV.y, 0.001, 0.999);

//	vec4 refraction = texture2D(u_specularTexture, refractionUV);
	vec4 rer1 = blur13(u_specularTexture, refractionUV, vec2(0,1));
	vec4 rer2 = blur13(u_specularTexture, refractionUV, vec2(0,2));
	vec4 refraction = (rer1 + rer2) * 0.5; 

	vec4 ref1 = blur13(u_reflectionTexture, reflectionUV, vec2(0,1));
	vec4 ref2 = blur13(u_reflectionTexture, reflectionUV, vec2(0,2));	
//	vec4 ref3 = blur13(u_reflectionTexture, reflectionUV, vec2(0,4));	
//	vec4 ref4 = blur13(u_reflectionTexture, reflectionUV, vec2(0,8));	
//	vec4 ref5 = blur13(u_reflectionTexture, reflectionUV, vec2(0,10));	
//	vec4 ref6 = blur13(u_reflectionTexture, reflectionUV, vec2(0,12));		
//	vec4 reflection = (ref1 + ref2 + ref3 + ref4 + ref5 + ref6) / 6;
	vec4 reflection = (ref1 + ref2) * 0.5;
	
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
	vec4 refractionFactor = refraction * vec4(0.7, 0.7, 0.7, 1);
//	vec4 refractionFactor = relfMix * vec4(0.2, 0.2, 0.2, 1);
	
	d = diffuse.r * (1.0 - d);
			
	//diffuse.r = 0 krasts, 1 dzilums
	//d = 1 - transparent, 0 - reflective
	
				
	
	//gl_FragColor = vec4(d,d,d,1);return;
				
	gl_FragColor = mix(refractionFactor, relfMix, d) + vec4(specHilight, 0.0);	
	
	gl_FragColor.w = u_opacity;
}


