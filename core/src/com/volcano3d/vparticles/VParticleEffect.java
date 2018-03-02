package com.volcano3d.vparticles;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.volcano3d.vcore.VMain;

public class VParticleEffect {

	protected VMain volcano;

	protected String effectName = "";
	
	public ParticleSystem particleSystem = null;
	
	public PointSpriteParticleBatch pointSpriteBatch  = null;
	
	public ParticleEffect particleEffect = null;		
	
	public ModelBatch modelBatch = null;
	
    public VParticleEffect(VMain o, String filename){
    	volcano = o;
    	effectName = filename;
    	particleSystem = new ParticleSystem();
    	
    	ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
    	
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        volcano.assetsManager.setLoader(ParticleEffect.class, loader);
        
    	volcano.assetsManager.load(filename, ParticleEffect.class, loadParam);
    }
    public void onLoad(){        
    	
    	modelBatch = new ModelBatch();
    	pointSpriteBatch = new PointSpriteParticleBatch();
    	
        if(volcano.assetsManager.isLoaded(effectName)) {
        	ParticleEffect effect = volcano.assetsManager.get(effectName, ParticleEffect.class);
        	
        	OrthographicCamera cam = new OrthographicCamera(18.0f, 18.0f);
        	
        	pointSpriteBatch.setCamera(cam);
        	        	
        	particleSystem.add(pointSpriteBatch);
        	
        	particleEffect = effect.copy();
        
        	particleEffect.init();
        	//particleEffect.start();
        	
        	particleSystem.add(particleEffect);        	

        	
        }else{
            System.out.println("Particle effect asset not loaded "+effectName);
        }
    }
    
	public void render(PerspectiveCamera cam){
    	
		modelBatch.begin(cam);
    	
		//particleSystem.update();
		
		particleSystem.begin();
		
		particleSystem.draw();
		
		particleSystem.end();
		
        modelBatch.render(particleSystem);
        modelBatch.end();		
	}
    
    
}
