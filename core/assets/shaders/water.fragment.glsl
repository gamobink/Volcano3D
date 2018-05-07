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


vec4 dofBlur(sampler2D source, vec2 uv, float aspect, float depth){

	float blurclamp = 3.0;  	// max blur amount
	float bias = 0.6; 			//aperture - bigger values for shallower depth of field

    vec2 aspectcorrect = vec2(1.0, aspect);
          
    vec2 dofblur = vec2 (clamp( depth * bias, -blurclamp, blurclamp ));
        
    vec4 col = vec4(0.0);
   
    col += texture2D(source, uv.xy);
    col += texture2D(source, uv.xy + (vec2( 0.0,0.4 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( 0.15,0.37 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( 0.29,0.29 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( -0.37,0.15 )*aspectcorrect) * dofblur);       
    col += texture2D(source, uv.xy + (vec2( 0.4,0.0 )*aspectcorrect) * dofblur);   
    col += texture2D(source, uv.xy + (vec2( 0.37,-0.15 )*aspectcorrect) * dofblur);       
    col += texture2D(source, uv.xy + (vec2( 0.29,-0.29 )*aspectcorrect) * dofblur);       
    col += texture2D(source, uv.xy + (vec2( -0.15,-0.37 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( 0.0,-0.4 )*aspectcorrect) * dofblur); 
    col += texture2D(source, uv.xy + (vec2( -0.15,0.37 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( -0.29,0.29 )*aspectcorrect) * dofblur);
    col += texture2D(source, uv.xy + (vec2( 0.37,0.15 )*aspectcorrect) * dofblur); 
    col += texture2D(source, uv.xy + (vec2( -0.4,0.0 )*aspectcorrect) * dofblur); 
    col += texture2D(source, uv.xy + (vec2( -0.37,-0.15 )*aspectcorrect) * dofblur);       
    col += texture2D(source, uv.xy + (vec2( -0.29,-0.29 )*aspectcorrect) * dofblur);       
    col += texture2D(source, uv.xy + (vec2( 0.15,-0.37 )*aspectcorrect) * dofblur);
   
    col += texture2D(source, uv.xy + (vec2( 0.15,0.37 )*aspectcorrect) * dofblur*0.9);
    col += texture2D(source, uv.xy + (vec2( -0.37,0.15 )*aspectcorrect) * dofblur*0.9);           
    col += texture2D(source, uv.xy + (vec2( 0.37,-0.15 )*aspectcorrect) * dofblur*0.9);           
    col += texture2D(source, uv.xy + (vec2( -0.15,-0.37 )*aspectcorrect) * dofblur*0.9);
    col += texture2D(source, uv.xy + (vec2( -0.15,0.37 )*aspectcorrect) * dofblur*0.9);
    col += texture2D(source, uv.xy + (vec2( 0.37,0.15 )*aspectcorrect) * dofblur*0.9);            
    col += texture2D(source, uv.xy + (vec2( -0.37,-0.15 )*aspectcorrect) * dofblur*0.9);   
    col += texture2D(source, uv.xy + (vec2( 0.15,-0.37 )*aspectcorrect) * dofblur*0.9);   
   
    col += texture2D(source, uv.xy + (vec2( 0.29,0.29 )*aspectcorrect) * dofblur*0.7);
    col += texture2D(source, uv.xy + (vec2( 0.4,0.0 )*aspectcorrect) * dofblur*0.7);       
    col += texture2D(source, uv.xy + (vec2( 0.29,-0.29 )*aspectcorrect) * dofblur*0.7);   
    col += texture2D(source, uv.xy + (vec2( 0.0,-0.4 )*aspectcorrect) * dofblur*0.7);     
    col += texture2D(source, uv.xy + (vec2( -0.29,0.29 )*aspectcorrect) * dofblur*0.7);
    col += texture2D(source, uv.xy + (vec2( -0.4,0.0 )*aspectcorrect) * dofblur*0.7);     
    col += texture2D(source, uv.xy + (vec2( -0.29,-0.29 )*aspectcorrect) * dofblur*0.7);   
    col += texture2D(source, uv.xy + (vec2( 0.0,0.4 )*aspectcorrect) * dofblur*0.7);
                     
    col += texture2D(source, uv.xy + (vec2( 0.29,0.29 )*aspectcorrect) * dofblur*0.4);
    col += texture2D(source, uv.xy + (vec2( 0.4,0.0 )*aspectcorrect) * dofblur*0.4);       
    col += texture2D(source, uv.xy + (vec2( 0.29,-0.29 )*aspectcorrect) * dofblur*0.4);   
    col += texture2D(source, uv.xy + (vec2( 0.0,-0.4 )*aspectcorrect) * dofblur*0.4);     
    col += texture2D(source, uv.xy + (vec2( -0.29,0.29 )*aspectcorrect) * dofblur*0.4);
    col += texture2D(source, uv.xy + (vec2( -0.4,0.0 )*aspectcorrect) * dofblur*0.4);     
    col += texture2D(source, uv.xy + (vec2( -0.29,-0.29 )*aspectcorrect) * dofblur*0.4);   
    col += texture2D(source, uv.xy + (vec2( 0.0,0.4 )*aspectcorrect) * dofblur*0.4);       
                   
    col = col/41.0;
    col.a = 1.0;
    
    return col;
}



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
	
	//vec4 sp = texture2D(u_specularTexture, refractionUV);	
	//gl_FragColor = vec4(sp.a, sp.a, sp.a, 1);	return;
		
	//Sample texture maps
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	
	//Animate DuDv map translation		
	vec2 uv1 = v_dudvUV;		
	float n1 = sin((uv1.x * 0.5) + (u_shininess * (M_PI*2))) * 0.5 * fog;
	float n2 = sin((uv1.x * 2) + (u_shininess * (M_PI*2))) * 0.1 * fog;	
	uv1.y -= (n1 + n2) * 0.5;
	uv1.x -= n2;
	//Specular multiplier for large waves effect
	float wavesSpecularMul = pow((n1 + n2 + 2) * 0.5, 5);
		
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
	//refractionUV += dispalce1;		//!!!!!!!
	
	//Clamp to edges	
	reflectionUV.x = clamp(reflectionUV.x, 0.001, 0.999);
	reflectionUV.y = clamp(reflectionUV.y, 0.001, 0.999);
	
	refractionUV.x = clamp(refractionUV.x, 0.001, 0.999);
	refractionUV.y = clamp(refractionUV.y, 0.001, 0.999);

	//Depth variable blur	
	vec4 rer = texture2D(u_specularTexture, refractionUV);
	gl_FragColor = rer; gl_FragColor.w = 1.0; return;			//!!!!!!!
	
	float deptha1 = pow((1.0 - rer.a), 5.0);
	vec4 refraction = dofBlur(u_specularTexture, refractionUV, 1,  deptha1 * 0.2);

	vec4 ref1 = blur13(u_reflectionTexture, reflectionUV, vec2(0,1));
	vec4 ref2 = blur13(u_reflectionTexture, reflectionUV, vec2(0,2));	
	vec4 reflection = (ref1 + ref2) * 0.5;
//	vec4 ref3 = blur13(u_reflectionTexture, reflectionUV, vec2(0,4));	
//	vec4 ref4 = blur13(u_reflectionTexture, reflectionUV, vec2(0,8));	
//	vec4 ref5 = blur13(u_reflectionTexture, reflectionUV, vec2(0,10));	
//	vec4 ref6 = blur13(u_reflectionTexture, reflectionUV, vec2(0,12));		
//	vec4 reflection = (ref1 + ref2 + ref3 + ref4 + ref5 + ref6) / 6;
	
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

	float depthFactor = clamp(pow(refraction.a, 0.2), 0,1);
	vec4 refractionFactor = vec4(refraction.rgb, 1) * depthFactor * vec4(depthFactor,depthFactor,1, 1);

	//a) refraction.a = 1 - deep, 0 - shallow
	//b) d = 1 - transparent, 0 - reflective
	//c) output = 1 - refl, 0 - underwater
	
	d = (1 - refraction.r) * (1.0 - d);
				
	gl_FragColor = mix(refractionFactor, relfMix, d) + vec4(specHilight, 0.0);	
	gl_FragColor.w = u_opacity;
}


