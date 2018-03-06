package com.volcano3d.vparticles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader.ParticleType;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.volcano3d.vcore.VMain;

public class VParticleEffect {

	protected VMain volcano;

	protected String effectName = "";
	public PointSpriteParticleBatch pointSpriteBatch  = null;
    public OrthographicCamera cam;
    public ModelBatch modelBatch;
    public AssetManager assets;
    private ParticleEffect currentEffects;
    private ParticleSystem particleSystem;	
	 
    public Matrix4 transform = new Matrix4();
    
    public VParticleEffect(VMain o, String filename){
    	volcano = o;
    	effectName = filename;
    	particleSystem = new ParticleSystem();

    	ParticleShader.Config shaderConf = new  ParticleShader.Config();
    	shaderConf.type = ParticleType.Point;
//    	shaderConf.vertexShader
//    	shaderConf.fragmentShader
           	
        pointSpriteBatch = new PointSpriteParticleBatch(100000);
        pointSpriteBatch.setCamera(new OrthographicCamera(18.0f, 18.0f));
        particleSystem.add(pointSpriteBatch);
        
    	ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());    	

    	volcano.assetsManager.load(effectName, ParticleEffect.class, loadParam);
    }
    public void onLoad(){        

        modelBatch = new ModelBatch();
        if(volcano.assetsManager.isLoaded(effectName)) {
        	ParticleEffect effect = volcano.assetsManager.get(effectName, ParticleEffect.class);
        	currentEffects = effect.copy();
        	currentEffects.init();
        	particleSystem.add(currentEffects);
        	pointSpriteBatch.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }else{
            System.out.println("Particle effect asset not loaded "+effectName);
        }       
    }
    public void setPosition(float x, float y, float z){
    	transform.setToTranslation(x, y, z);
    }
	public void render(PerspectiveCamera cam){
		currentEffects.setTransform(transform);
		modelBatch.begin(cam);
        particleSystem.update();
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        modelBatch.render(particleSystem);
        modelBatch.end();        
	}
    
    
}
