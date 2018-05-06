varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;


#define M_PI 3.1415926535897932384626433832795
#define M_PI2 M_PI*2

uniform float u_shininess;

void main() {

//	gl_FragColor = vec4(vec3(1), diffuse.a * 0.5);
	
	vec2 uv = v_diffuseUV;
	
	float offsety = (sin(((uv.x * 5) + u_shininess) * (M_PI*2)) * 0.04);
	
	uv.y = uv.y + offsety;
	
	vec4 diffuse = texture2D(u_diffuseTexture, uv);
	
	gl_FragColor = vec4(vec3(0.9, 0.9, 1), diffuse.a * 0.7);
	
}
