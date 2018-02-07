#ifdef GL_ES 
precision mediump float;
#endif

varying vec2 v_diffuseUV;

uniform sampler2D u_diffuseTexture;
 
uniform samplerCube u_environmentCubemap;
 
varying vec4 v_position;
 
varying vec3 v_viewDirection; 

varying vec3 v_normal;
 
void main() {
	
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	
	vec3 view = v_viewDirection;
	
  	vec3 reflectedDir = reflect(view.xyz, normalize(v_normal).xyz);
	
	gl_FragColor = (textureCube(u_environmentCubemap, reflectedDir) + diffuse) * 0.5;
}