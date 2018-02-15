package com.volcano3d.vshaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class VMinimalistShader  extends BaseShader{
	
	public static class Inputs {
		public final static Uniform projTrans = new Uniform("u_projTrans");
		public final static Uniform viewTrans = new Uniform("u_viewTrans");
		public final static Uniform projViewTrans = new Uniform("u_projViewTrans");
		public final static Uniform cameraPosition = new Uniform("u_cameraPosition");
		public final static Uniform cameraDirection = new Uniform("u_cameraDirection");
		public final static Uniform cameraUp = new Uniform("u_cameraUp");
		public final static Uniform cameraNearFar = new Uniform("u_cameraNearFar");

		public final static Uniform worldTrans = new Uniform("u_worldTrans");
		public final static Uniform viewWorldTrans = new Uniform("u_viewWorldTrans");
		public final static Uniform projViewWorldTrans = new Uniform("u_projViewWorldTrans");
		
		public final static Uniform diffuseTexture = new Uniform("u_diffuseTexture", TextureAttribute.Diffuse);
		public final static Uniform diffuseUVTransform = new Uniform("u_diffuseUVTransform", TextureAttribute.Diffuse);		
	}
	public static class Setters {
		public final static Setter projTrans = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.projection);
			}
		};
		public final static Setter viewTrans = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.view);
			}
		};
		public final static Setter projViewTrans = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.combined);
			}
		};
		public final static Setter cameraPosition = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.position.x, shader.camera.position.y, shader.camera.position.z,
					1.1881f / (shader.camera.far * shader.camera.far));
			}
		};
		public final static Setter cameraDirection = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.direction);
			}
		};
		public final static Setter cameraUp = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.up);
			}
		};
		public final static Setter cameraNearFar = new GlobalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, shader.camera.near, shader.camera.far);
			}
		};
		public final static Setter worldTrans = new LocalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, renderable.worldTransform);
			}
		};
		public final static Setter viewWorldTrans = new LocalSetter() {
			final Matrix4 temp = new Matrix4();

			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, temp.set(shader.camera.view).mul(renderable.worldTransform));
			}
		};
		public final static Setter projViewWorldTrans = new LocalSetter() {
			final Matrix4 temp = new Matrix4();

			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				shader.set(inputID, temp.set(shader.camera.combined).mul(renderable.worldTransform));
			}
		};
		public final static Setter diffuseTexture = new LocalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				final int unit = shader.context.textureBinder.bind(((TextureAttribute)(combinedAttributes
					.get(TextureAttribute.Diffuse))).textureDescription);
				shader.set(inputID, unit);
			}
		};
		public final static Setter diffuseUVTransform = new LocalSetter() {
			@Override
			public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
				final TextureAttribute ta = (TextureAttribute)(combinedAttributes.get(TextureAttribute.Diffuse));
				shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
			}
		};		
	}
	// Global uniforms
	public final int u_projTrans;
	public final int u_viewTrans;
	public final int u_projViewTrans;
	public final int u_cameraPosition;
	public final int u_cameraDirection;
	public final int u_cameraUp;
	public final int u_cameraNearFar;
	public final int u_time;
	// Object uniforms
	public final int u_worldTrans;
	public final int u_viewWorldTrans;
	public final int u_projViewWorldTrans;
	private Renderable renderable;	
	private float time = 0;
	
	public final int u_diffuseTexture;
	public final int u_diffuseUVTransform;	
	
	public VMinimalistShader(Renderable renderable, ShaderProgram shaderProgram) {
		
		this.program = shaderProgram;
		this.renderable = renderable;
		
		// Global uniforms
		u_projTrans = register(Inputs.projTrans, Setters.projTrans);
		u_viewTrans = register(Inputs.viewTrans, Setters.viewTrans);
		u_projViewTrans = register(Inputs.projViewTrans, Setters.projViewTrans);
		u_cameraPosition = register(Inputs.cameraPosition, Setters.cameraPosition);
		u_cameraDirection = register(Inputs.cameraDirection, Setters.cameraDirection);
		u_cameraUp = register(Inputs.cameraUp, Setters.cameraUp);
		u_cameraNearFar = register(Inputs.cameraNearFar, Setters.cameraNearFar);
		u_time = register(new Uniform("u_time"));
		// Object uniforms
		u_worldTrans = register(Inputs.worldTrans, Setters.worldTrans);
		u_viewWorldTrans = register(Inputs.viewWorldTrans, Setters.viewWorldTrans);
		u_projViewWorldTrans = register(Inputs.projViewWorldTrans, Setters.projViewWorldTrans);		
		//Materials
		u_diffuseTexture = register(Inputs.diffuseTexture, Setters.diffuseTexture);
		u_diffuseUVTransform = register(Inputs.diffuseUVTransform, Setters.diffuseUVTransform);
	}

	public VMinimalistShader(Renderable renderable) {
		this(renderable, null);
	}

	@Override
	public void init() {
		final ShaderProgram program = this.program;
		this.program = null;
		init(program, renderable);
		renderable = null;
	}

	@Override
	public int compareTo(Shader other) {
		if (other == null) return -1;
		if (other == this) return 0;
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return true;
	}
	@Override
	public void begin (final Camera camera, final RenderContext context) {
		super.begin(camera, context);
		if (has(u_time)) set(u_time, time += Gdx.graphics.getDeltaTime());
	}

	@Override
	public void render (Renderable renderable, Attributes combinedAttributes) {
		if (!combinedAttributes.has(BlendingAttribute.Type))
			context.setBlending(false, GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

//		bindMaterial(combinedAttributes);
		
		context.setDepthTest(GL30.GL_LEQUAL, 0.0f, 1.0f);
		
		super.render(renderable, combinedAttributes);
	}

	@Override
	public void end () {
		super.end();
	}
}
