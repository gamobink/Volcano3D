//varying vec2 v_diffuseUV;
//uniform sampler2D u_diffuseTexture;
//vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

uniform vec4 u_diffuseColor;

uniform sampler2D u_specularTexture;	//Refraction - underwater part

varying vec3 v_lightDir; 
varying vec4 v_projectedPos;
varying vec3 v_normal;
varying float v_edgeGradient;

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

void main() {

	vec2 ndc = (v_projectedPos.xy / v_projectedPos.w)/2.0 + 0.5;
	vec2 refractionUV = vec2(ndc.x, ndc.y);

	float dot = dot(normalize(v_lightDir), v_normal);

	dot = pow(dot, 5);
	dot = clamp(dot, 0,1);
	
	float depth = texture2D(u_specularTexture, refractionUV).a;
	vec4 refraction = dofBlur(u_specularTexture, refractionUV, 1.42, depth / 15);

	float ed = v_edgeGradient;
	ed = clamp((-ed * 0.02) + 0.4, 0,1);
	
	float depthFactor = clamp(pow(depth, 0.5), 0,1);

	gl_FragColor = (refraction * depthFactor) * vec4(0.8 * ed, 0.8 * ed, 1, 1) + vec4(dot,dot,dot, 1);
	gl_FragColor.w = 1.0f;
}
