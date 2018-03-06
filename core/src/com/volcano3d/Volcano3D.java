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

/**/
public class Volcano3D extends ApplicationAdapter {
	
	public class ParticleSys{
		
	    public OrthographicCamera cam;
	    public ModelBatch modelBatch;
	    private ParticleEffect currentEffects;
	    private ParticleSystem particleSystem;

		public void create (String fname, AssetManager assets) {		
	        modelBatch = new ModelBatch();
//	        
//	        String fname = "volcanoFire.pfx";
//	        String fname2 = "smokeIsland.pfx";
	        
	        cam = new OrthographicCamera(200.0f, 200.0f);
	
	        particleSystem = new ParticleSystem();//.get();
	        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
	        pointSpriteBatch.setCamera(cam);
	        particleSystem = ParticleSystem.get();
	
	        particleSystem.add(pointSpriteBatch);
	        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());

	        assets.load(fname, ParticleEffect.class, loadParam);
	        // halt the main thread until assets are loaded.
	        // this is bad for actual games, but okay for demonstration purposes.
		}
		public void init(String fname){
	
	        currentEffects = assets.get(fname,ParticleEffect.class).copy();
	        currentEffects.init();
	        particleSystem.add(currentEffects);
	
		}
		public void render () {
	        modelBatch.begin(cam);
	        particleSystem.update();
	        particleSystem.begin();
	       
	        particleSystem.draw();
	        particleSystem.end();
	        modelBatch.render(particleSystem);
	        modelBatch.end();
		}	
	}
	
    public AssetManager assets = new AssetManager();
	public ParticleSys psys1 = new ParticleSys();
	public ParticleSys psys2 = new ParticleSys();

	
	@Override
	public void create () {		

		ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assets.setLoader(ParticleEffect.class, loader);
		
		psys1.create("volcanoFire.pfx", assets);
		psys2.create("smokeIsland.pfx", assets);
		assets.finishLoading(); 
		psys1.init("volcanoFire.pfx");
		//psys2.init("smokeIsland.pfx");		
		
		
	}
	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL30.GL_TEXTURE_2D);


        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        
        psys1.render();
      //  psys2.render();
        
	}
	@Override
	public void dispose() {
		
	}
}

/**
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