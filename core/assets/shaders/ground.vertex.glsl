attribute vec3 a_position;
uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;

uniform vec4 u_cameraPosition;

attribute vec2 a_texCoord0;
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;

varying float v_fog;

void main() {
        
	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);

    vec3 flen = u_cameraPosition.xyz - pos.xyz;
    float fog = dot(flen, flen) * u_cameraPosition.w;
    v_fog = min(fog, 1.0);

	gl_Position = u_projViewTrans * pos;
}