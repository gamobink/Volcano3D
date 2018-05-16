package com.volcano3d.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

//Force texture mipmapping and filtering
public class FilteredTexture extends Texture {
	public FilteredTexture(String fileName) {
		super(Gdx.files.internal(fileName), null, true);
	}	
	public FilteredTexture(FileHandle file) {
		super(file, null, true);
	}
	public FilteredTexture(TextureData data) {
		super(data);
	}	
	public void bind () {
		super.bind();
		Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAX_ANISOTROPY_EXT, 16.0f);
		Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_LINEAR);
		Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_LINEAR_MIPMAP_LINEAR);	
	}	
}
