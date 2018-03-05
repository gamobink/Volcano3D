package com.volcano3d;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VStaticAssets;

/**
public class Volcano3D extends ApplicationAdapter {

    public OrthographicCamera cam;
    public ModelBatch modelBatch;
    public AssetManager assets;
    private ParticleEffect currentEffects;
    private ParticleSystem particleSystem;

	@Override
	public void create () {		
        modelBatch = new ModelBatch();
        
        String fname = "point3.pfx";
        
        cam = new OrthographicCamera(18.0f, 18.0f);
        assets = new AssetManager();

        particleSystem = ParticleSystem.get();
        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
        pointSpriteBatch.setCamera(cam);
        particleSystem = ParticleSystem.get();

        particleSystem.add(pointSpriteBatch);
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assets.setLoader(ParticleEffect.class, loader);
        assets.load(fname, ParticleEffect.class, loadParam);
        // halt the main thread until assets are loaded.
        // this is bad for actual games, but okay for demonstration purposes.
        assets.finishLoading(); 

        currentEffects = assets.get(fname,ParticleEffect.class).copy();
        currentEffects.init();
        particleSystem.add(currentEffects);

	}
	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL30.GL_TEXTURE_2D);


        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        
        modelBatch.begin(cam);
        particleSystem.update();
        particleSystem.begin();
       
        particleSystem.draw();
        particleSystem.end();
        modelBatch.render(particleSystem);
        modelBatch.end();
	}	
	@Override
	public void dispose() {


	}
}

/**/
public class Volcano3D extends ApplicationAdapter {
	protected VMain volcano = null;
	@Override
	public void create () {		
		
//		try{
//			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
//		}catch(FileNotFoundException e){	}		
		
		
		
		
//		float offsetx = VStaticAssets.ActorActionsPaths.pathView8ButtonMoveIn[0][0];	
//		
//		for(int i = 0; i < VStaticAssets.ActorActionsPaths.pathView8ButtonMoveIn.length; i++){
//			
//			float px = VStaticAssets.ActorActionsPaths.pathView8ButtonMoveIn[i][0];
//			float py = VStaticAssets.ActorActionsPaths.pathView8ButtonMoveIn[i][1];			
//			
//			float ppx = (-(px - offsetx)) + offsetx;
//
//			System.out.format("{%ff, %ff},", ppx, py);System.out.println("");	
//		}
		
		
		
		volcano = new VMain();
	}
	@Override
	public void render () {
		volcano.render();
	}	
	@Override
	public void dispose() {
		volcano.dispose();
	}
}
/**/