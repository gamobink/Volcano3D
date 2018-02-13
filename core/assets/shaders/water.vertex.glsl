uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;

uniform vec4 u_cameraPosition;

attribute vec2 a_texCoord0;
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;

attribute vec3 a_normal;
uniform mat3 u_normalMatrix;
varying vec3 v_normal;

attribute vec3 a_position;

varying vec4 v_position; 
varying vec3 v_viewDirection;
varying vec4 v_projectedPos;
varying vec2 v_dudvUV; 

varying float v_fog; 

void main() {
	v_position = vec4(a_position, 1.0);

	v_projectedPos = u_projViewTrans * u_worldTrans *  v_position;
	
	gl_Position = v_projectedPos;
	
	v_normal = normalize(u_normalMatrix * a_normal);

	vec3 camp = u_cameraPosition.xyz;
	
	v_viewDirection = (u_worldTrans * v_position).xyz - camp;
 	 
 	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;              

	v_dudvUV = vec2(v_position.x / 150, v_position.y / 150);
	
    vec3 flen = u_cameraPosition.xyz - v_position.xyz;

    float fog = dot(flen, flen) * u_cameraPosition.w;

	v_fog = 1.0 - min(fog, 1.0);
	
}