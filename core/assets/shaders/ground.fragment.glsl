varying vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_specularTexture;
vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
vec4 fog = texture2D(u_specularTexture, v_diffuseUV);
varying float v_fog;
uniform float u_opacity;

const float shineDamper = 12.0;
const float reflectivity = 1;  
 
varying vec3 v_lightDir;

const vec3 lightColor = vec3(1,1,0.5);

varying vec3 v_normal;

varying vec3 v_viewDirection; 

void main() {
	
	//Specular 
	vec3 incidenceVector = normalize(-v_lightDir); 						//a unit vector
	vec3 reflectionVector = reflect(incidenceVector, normalize(v_normal)); 	//also a unit vector
	vec3 surfaceToCamera = normalize(v_viewDirection); 					//also a unit vector
	float cosAngle = max(0.0, dot(surfaceToCamera, reflectionVector));

	float gray = (diffuse.r + diffuse.g + diffuse.b) / 3.0;
	vec3 colorShade = mix(vec3(0.3,0.3,1), vec3(1,1,0.5), gray+gray+gray);

	float specularCoefficientHighlight = pow(cosAngle, shineDamper);
	vec3 specHilight = colorShade * specularCoefficientHighlight * reflectivity;

	diffuse = diffuse + (vec4(specHilight, 1) * diffuse);

	gl_FragColor = diffuse;
	gl_FragColor.rgb = mix(gl_FragColor.rgb, fog.rgb, v_fog);
	gl_FragColor.w = u_opacity;	
	
	//gl_FragColor = vec4(vec3(colorShade), 1);	
}
