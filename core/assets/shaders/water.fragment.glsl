#ifdef GL_ES 
precision mediump float;
#endif

varying vec2 v_diffuseUV;

uniform sampler2D u_diffuseTexture;
 
uniform sampler2D u_ambientTexture;
 
varying vec4 v_position;
 
varying vec3 v_viewDirection; 

varying vec3 v_normal;

varying vec4 v_projectedPos;
 
void main() {
	
	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);
	vec2 reflectionUV = vec2(ndc.x, 1.0-ndc.y);
		
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);	
	vec4 ambient = texture2D(u_ambientTexture, reflectionUV);

	float d = pow(dot(normalize(-v_viewDirection).xyz, normalize(v_normal).xyz), 0.5);	
	
	gl_FragColor = mix(ambient,diffuse, d);
}