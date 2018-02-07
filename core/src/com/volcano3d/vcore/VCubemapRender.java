package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.FrameBufferCubemap;
import com.badlogic.gdx.math.Vector3;

public class VCubemapRender {

	protected VMain volcano = null;
	
	protected PerspectiveCamera camFb;
	protected FrameBufferCubemap fb;
	protected Cubemap cubemap;
	protected Model cubeMesh;
	protected ModelInstance cubeInstance;
	protected ModelBatch cubeBatch;	
	float yaw, pitch, roll;
	protected PerspectiveCamera camCube;
	
	public VCubemapRender(VMain v){
		volcano = v;

		camFb = new PerspectiveCamera(90, 800, 800);
		camFb.position.set(0f, 200f, 0f);
		camFb.lookAt(0, 1, 0);
		camFb.near = 0.1f;
		camFb.far = 4000;
		camFb.update();

		fb = new FrameBufferCubemap(Format.RGBA8888, 800, 800, true);
		cubemap = fb.getColorBufferTexture();
				
		//Test object
		ObjLoader objLoader = new ObjLoader();
		cubeMesh = objLoader.loadModel(Gdx.files.internal("cube.obj"));
		cubeInstance = new ModelInstance(cubeMesh);		
		cubeBatch = new ModelBatch(Gdx.files.internal("shaders/water.vertex.glsl"),
				 Gdx.files.internal("shaders/water.fragment.glsl"));

		cubeInstance.materials.get(0).set(new CubemapAttribute(CubemapAttribute.EnvironmentMap, cubemap));

		camCube = new PerspectiveCamera(67, Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.5f);
		camCube.position.set(0f, 2f, 2f);
		camCube.lookAt(0, 0, 0);
		camCube.near = 1f;
		camCube.far = 300f;
		camCube.update();
	}	
	
	public void renderCubemap(){
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Gdx.gl.glDisable(GL30.GL_SCISSOR_TEST);
		
		// Render scene to cubemap
		camFb.position.set(volcano.camera.get().position);
//		camFb.near = cam.near;
//		camFb.far = cam.far;
		fb.begin();
		while( fb.nextSide() ) {
			fb.getSide().getUp(camFb.up);
			fb.getSide().getDirection(camFb.direction);
			camFb.update();
			
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

		//	volcano.renderCubemapScene(camFb);
		}
		fb.end();
	}
	
	public void renderTest(){
		
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
		
		pitch += 0.1f * Gdx.graphics.getDeltaTime();
		yaw += 1 * Gdx.graphics.getDeltaTime();
		cubeInstance.transform.setFromEulerAngles(yaw, pitch, roll);
		cubeBatch.begin(camCube);
		cubeBatch.render(cubeInstance);
		cubeBatch.end();
	}
	
}
