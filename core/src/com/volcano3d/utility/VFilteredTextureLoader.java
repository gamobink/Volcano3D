package com.volcano3d.utility;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;

/** {@link AssetLoader} for {@link Texture} instances. The pixel data is loaded asynchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 * @author mzechner */
public class VFilteredTextureLoader extends AsynchronousAssetLoader<VFilteredTexture, VFilteredTextureLoader.FilteredTextureParameter> {
	static public class TextureLoaderInfo {
		String filename;
		TextureData data;
		VFilteredTexture texture;
	};

	TextureLoaderInfo info = new TextureLoaderInfo();

	public VFilteredTextureLoader (FileHandleResolver resolver) {
		super(resolver);
	}

	public void loadAsync (AssetManager manager, String fileName, FileHandle file, FilteredTextureParameter parameter) {
		info.filename = fileName;
		if (parameter == null || parameter.textureData == null) {
			Format format = null;
			boolean genMipMaps = true;
			info.texture = null;

			if (parameter != null) {
				format = parameter.format;
				genMipMaps = parameter.genMipMaps;
				info.texture = parameter.texture;
			}

			info.data = TextureData.Factory.loadFromFile(file, format, genMipMaps);
		} else {
			info.data = parameter.textureData;
			info.texture = parameter.texture;
		}
		if (!info.data.isPrepared()) info.data.prepare();
	}

	public VFilteredTexture loadSync (AssetManager manager, String fileName, FileHandle file, FilteredTextureParameter parameter) {
		if (info == null) return null;
		VFilteredTexture texture = info.texture;
		if (texture != null) {
			texture.load(info.data);
		} else {
			texture = new VFilteredTexture(info.data);
		}
		if (parameter != null) {
			texture.setFilter(parameter.minFilter, parameter.magFilter);
			texture.setWrap(parameter.wrapU, parameter.wrapV);
		}
		return texture;
	}

	@SuppressWarnings("rawtypes")
	public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, FilteredTextureParameter parameter) {
		return null;
	}

	static public class FilteredTextureParameter extends AssetLoaderParameters<VFilteredTexture> {
		/** the format of the final Texture. Uses the source images format if null **/
		public Format format = null;
		/** whether to generate mipmaps **/
		public boolean genMipMaps = true;
		/** The texture to put the {@link TextureData} in, optional. **/
		public VFilteredTexture texture = null;
		/** TextureData for textures created on the fly, optional. When set, all format and genMipMaps are ignored */
		public TextureData textureData = null;
		public TextureFilter minFilter = TextureFilter.Nearest;
		public TextureFilter magFilter = TextureFilter.Nearest;
		public TextureWrap wrapU = TextureWrap.ClampToEdge;
		public TextureWrap wrapV = TextureWrap.ClampToEdge;
	}


}
