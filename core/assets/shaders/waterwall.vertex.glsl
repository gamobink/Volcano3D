attribute vec3 a_position;
uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;

attribute vec2 a_texCoord0;
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;

uniform vec4 u_cameraPosition;
varying vec3 v_lightDir; 

attribute vec3 a_normal;
uniform mat3 u_normalMatrix;
varying vec3 v_normal;

varying vec4 v_projectedPos;

void main() {
	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	
	v_projectedPos = u_projViewTrans * u_worldTrans *  vec4(a_position, 1.0);
	
	gl_Position = u_projViewTrans * pos;
	
	v_lightDir = u_cameraPosition.xyz - a_position.xyz;
	
	v_normal = normalize(u_normalMatrix * a_normal);
}
