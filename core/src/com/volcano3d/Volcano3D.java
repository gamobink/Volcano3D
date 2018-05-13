package com.volcano3d;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import com.badlogic.gdx.ApplicationAdapter;
import com.volcano3d.vcore.VConfig;
import com.volcano3d.vcore.VMain;

/**
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
		psys2.init("smokeIsland.pfx");		
		
		
	}
	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL30.GL_TEXTURE_2D);


        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        
        psys1.render();
        psys2.render();
        
	}
	@Override
	public void dispose() {
		
	}
}

/**/
public class Volcano3D extends ApplicationAdapter {
	public VMain volcano = null;
	@Override
	public void create () {	
		
		/**
		class Cfg{
			public final boolean developmentMode = true; 
			public final Vector2 resolution = new Vector2(490f, 800f);
			public final boolean fullScreen = false;
			public final Vector2 touchDragForceMult = new Vector2(10f, 5f);	
			public final float[] introTextLabelShownInterval = {4,6,9,6,6,5};
			public final float 	introTextTitleLabelVisibleTime = 8;
		}		
		Cfg cfg = new Cfg();
		
		Json json = new Json();
		//json.setOutputType(OutputType.minimal);
		String text = json.toJson(cfg, Cfg.class);
		
		System.out.println(text);
		
		/**/
		
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
		
		if(VConfig.get().developmentMode == false){
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);
			String filename = "./logs/"+year+""+(month+1)+""+day+""+hour+""+minute+""+second+".log";			
			try{
				System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(filename, true)), true));
			}catch(FileNotFoundException e){	}		   
			System.out.printf("===========Volcano3D %4d/%02d/%02d %02d:%02d:%02d==========\n", year, month+1, day, hour, minute, second);
		}
		
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