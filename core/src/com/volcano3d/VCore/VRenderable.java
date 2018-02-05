package com.volcano3d.VCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.volcano3d.SceneManager;

/**
 * Created by T510 on 8/6/2017.
 */

public class VRenderable {
	
	public class MyShaderProvider extends DefaultShaderProvider{
		
		private ShaderProgram	shaderProgram = null;
		
		public MyShaderProvider(){
			super();
//	        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
//	        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();	
//	        String prefix = DefaultShader.createPrefix(renderable, shaderConfig);
//	        shaderProgram = new ShaderProgram(prefix + vert, prefix + frag);
		}
		
		public Shader getShader (Renderable renderable) {
			
			Shader suggestedShader = renderable.shader;
			
			if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
			
			//System.out.println("get shader "+renderable);
			
		
	        DefaultShader.Config shaderConfig = new DefaultShader.Config();
	        
	        if(shaderProgram == null){
		        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
		        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();	
		        String prefix = DefaultShader.createPrefix(renderable, shaderConfig);
		        shaderProgram = new ShaderProgram(prefix + vert, prefix + frag);
	        }
	        
	        DefaultShader shaderC = new DefaultShader(renderable, shaderConfig, shaderProgram);
			shaderC.init();
			
	        return shaderC;
	        
			/*
			Shader suggestedShader = renderable.shader;
			
			//System.out.println(suggestedShader+" "+suggestedShader.canRender(renderable)+" / "+renderable);
			
			System.out.println(renderable+" / "+suggestedShader.canRender(renderable)+" / "+suggestedShader);
			
			if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
			for (Shader shader : shaders) {
				if (shader.canRender(renderable)) return shader;
			}
			final Shader shader = createShader(renderable);
			shader.init();
			shaders.add(shader);
			
			return shader;*/
		}
		
	}
	
	public class MyDefaultShader extends DefaultShader{
		public MyDefaultShader(Renderable renderable, Config config) {
			super(renderable, config, createPrefix(renderable, config));
		}
		
		public boolean canRender (final Renderable renderable) {
		//	return true;
			System.out.println("MyDefaultShader:canRender "+super.canRender(renderable));
			return super.canRender(renderable);
		}		
	}

	public class MyModelBatch extends ModelBatch{
		
		public MyModelBatch(ShaderProvider shaderProvider){
			super(shaderProvider);
		}
		/*
		public void render (final RenderableProvider renderableProvider, final Environment environment, final Shader shader) {

			final int offset = renderables.size;
			renderableProvider.getRenderables(renderables, renderablesPool);
			for (int i = offset; i < renderables.size; i++) {
				Renderable renderable = renderables.get(i);
				renderable.environment = environment;
				//renderable.shader = shader;				
				renderable.shader = shaderProvider.getShader(renderable);
//				System.out.println(renderable);

			}
		}		*/
		
		public void flush () {
			sorter.sort(camera, renderables);
			Shader currentShader = null;
			for (int i = 0; i < renderables.size; i++) {
				final Renderable renderable = renderables.get(i);
				if (currentShader != renderable.shader) {
					if (currentShader != null) currentShader.end();
					currentShader = renderable.shader;
					currentShader.begin(camera, context);
				}
				//System.out.println(currentShader);
				currentShader.render(renderable);
			}
			if (currentShader != null) currentShader.end();
			renderablesPool.flush();
			renderables.clear();
		}

	}
	
	protected SceneManager sceneManager;

	protected String modelName = "";

	protected MyModelBatch modelBatch = null;
	protected ModelInstance modelInstance = null;    
    
    public VShader vShader = null;
    
    protected BlendingAttribute blendingAttribute;
    protected Material material;
	
	protected boolean 	fadeOffAlpha = false;
	protected boolean 	fadeOnAlpha = false;
	protected float		fadeAlphaSpeeed = 0.5f;
	
	protected DefaultShader shader = null;
    
    public VRenderable(SceneManager o){
    	sceneManager = o;
    }
    public VRenderable(SceneManager o, String filename){
    	this(o, filename, null);
    }
    public VRenderable(SceneManager o, String filename, VShader shader){
    	sceneManager = o;
        modelName = filename;
        
        ModelLoader.ModelParameters modelParameters = new ModelLoader.ModelParameters();
        modelParameters.textureParameter.genMipMaps = true;
        modelParameters.textureParameter.minFilter = TextureFilter.MipMap;
        modelParameters.textureParameter.magFilter = TextureFilter.Linear;
        
    	sceneManager.assetsManager.load(modelName, Model.class, modelParameters);
    	vShader = shader;
    }

	protected static class RenderablePool extends FlushablePool<Renderable> {
		@Override
		protected Renderable newObject () {
			return new Renderable();
		}

		@Override
		public Renderable obtain () {
			Renderable renderable = super.obtain();
			renderable.environment = null;
			renderable.material = null;
			renderable.meshPart.set("", null, 0, 0, 0);
			renderable.shader = null;
			renderable.userData = null;
			return renderable;
		}
	}

	protected final RenderablePool renderablesPool = new RenderablePool();    
    
//    public void getNodesRec(Node node){
//    	
//    	System.out.println("Node: "+node);
//    	
//		for (Node child : node.getChildren()) {
//			getNodesRec(child);
//		}
//    }	
    
    public void init(){
        
        String vert = Gdx.files.internal("shaders/default.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/default.fragment.glsl").readString();

        modelBatch = new MyModelBatch(new MyShaderProvider());
        
        if(sceneManager.assetsManager.isLoaded(modelName)) {
            Model model = sceneManager.assetsManager.get(modelName, Model.class);
            modelInstance = new ModelInstance(model);
            
            material = modelInstance.materials.get(0);
            
            blendingAttribute = new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            
            if(vShader != null){
            	//modelBatch = new ModelBatch(vShader);
            	
            	
		        Renderable renderable = new Renderable();
		        renderable = modelInstance.getRenderable(renderable);

//				for (Node node : modelInstance.nodes) {
//					getNodesRec(node);
//				}
		        
		        //vShader.init(renderable);
		        
		        DefaultShader.Config shaderConfig = new DefaultShader.Config(vert, frag);
		        //shader = new DefaultShader(renderable, shaderConfig, "", vert, frag);
		        shader = new MyDefaultShader(renderable, shaderConfig);
		        
		        //shader = new DefaultShader(renderable, shaderConfig, new ShaderProgram(vert, frag));
				shader.init();						
				System.out.println("my shader "+shader);
				System.out.println("renderable "+renderable);	
				System.out.println("can render "+shader.canRender(renderable));				
/*
				
				Array<Renderable> renderablesTest = new Array<Renderable>();				
				modelInstance.getRenderables(renderablesTest, renderablesPool);
				for(int i=0; i<renderablesTest.size; i++){
					Renderable r = renderablesTest.get(i);
					//System.out.println(" rndrable: "+r+" / "+shader.canRender(r));
//					System.out.println("   opt  "+r.meshPart);		
//					System.out.println("   opt  "+r.material);
//					System.out.println("   opt  "+r.shader);		
				}
				
				
				*/
				
				//modelBatch.customShader = shader;
           }
        }else{
            System.out.println("Renderable:init asset not loaded "+modelName);
        }
    }

    public void translate(Vector3 pos){
        if(modelInstance != null) {
            modelInstance.transform.idt();
            modelInstance.transform.translate(pos);
        }
    }
	public void setFadeOff(){
		fadeOnAlpha = false;
		fadeOffAlpha = true;		
	}
	public void setFadeOn(){
		fadeOnAlpha = true;
		fadeOffAlpha = false;				
	}
    public void setColor(float r, float g, float b){
        if(modelInstance != null)modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(r,g,b,1));
    }
    public void setTransparency(float f){
    	blendingAttribute.opacity = f;
    	material.set(blendingAttribute);
    }
    
    public void render(PerspectiveCamera cam, Environment env){
    	float dt = Gdx.graphics.getDeltaTime();
    	if(fadeOffAlpha){
    		blendingAttribute.opacity -= dt * fadeAlphaSpeeed;
    		if(blendingAttribute.opacity <= 0)blendingAttribute.opacity = 0;
    		material.set(blendingAttribute);
    	}else if(fadeOnAlpha){
    		blendingAttribute.opacity += dt * fadeAlphaSpeeed;    		
    		if(blendingAttribute.opacity >= 1)blendingAttribute.opacity = 1;
    		material.set(blendingAttribute);
    	}  
    	if(blendingAttribute.opacity > 0){
	        modelBatch.begin(cam);
	        if(modelInstance != null){
	        	//modelBatch.render(modelInstance, env, shader);
	        	//if(vShader != null)modelBatch.render(modelInstance, env, vShader.shader);
	        	//else 
//	        	modelBatch.render(modelInstance, env, shader);
	        	
	        //	if(vShader != null)modelBatch.render(modelInstance, env, vShader.get());
	        //	else modelBatch.render(modelInstance, env);
	        	
//	        	modelBatch.render(modelInstance, env, shader);

	        	modelBatch.render(modelInstance, env);
	        }
	        else System.out.println("Renderable:render instance not created "+modelName);
	        modelBatch.end();       
    	}
    }

    public void dispose(){
        modelBatch.dispose();
    }

}
