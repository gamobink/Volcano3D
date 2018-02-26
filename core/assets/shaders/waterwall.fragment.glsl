varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

uniform sampler2D u_specularTexture;	//Refraction - underwater part

varying vec3 v_lightDir; 
varying vec4 v_projectedPos;

uniform vec4 u_diffuseColor;

varying vec3 v_normal;

vec4 boxBlur (sampler2D source, vec2 uv) {

	vec2 texOffset = vec2(0.004, 0.004);
	
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

void main() {

	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);

	float dot = dot(normalize(v_lightDir), v_normal);

	dot = pow(dot, 5);
	dot = clamp(dot, 0,1);
	
	
	vec4 ref = blur13(u_specularTexture, refractionUV, vec2(0,1));
	ref += blur13(u_specularTexture, refractionUV, vec2(0,2));
	ref += blur13(u_specularTexture, refractionUV, vec2(0,4));
	ref += blur13(u_specularTexture, refractionUV, vec2(0,5));
	ref += blur13(u_specularTexture, refractionUV, vec2(0,6));
	ref += blur13(u_specularTexture, refractionUV, vec2(0,7));	
	vec4 refraction = ref / 6;	
	
	//vec4 refraction = boxBlur(u_specularTexture, refractionUV); //texture2D(u_specularTexture, refractionUV);
	//refraction.w = 1;
	
	vec4 depthColor = vec4(0.6, 0.6, 1, 1) * clamp(pow(refraction.w, 0.5), 0,1);
		
	//gl_FragColor = depthColor; 
	//gl_FragColor.w = 1;
	//return;
	
	gl_FragColor = (refraction * depthColor) + vec4(dot,dot,dot*0.5, 1);
	gl_FragColor.w = 1.0f;
}
