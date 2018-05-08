package com.volcano3d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.volcano3d.Volcano3D;
import com.volcano3d.vcore.VConfig;
import com.volcano3d.vcore.VStaticAssets;

public class Volcano3DDesktop {
	public static void main (String[] arg) {
		
		VConfig.loadConfig("config.json");
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Volcano3D";
		config.samples = 6;
		config.depth = 24;
		config.vSyncEnabled = true;			//false; // Setting to false disables vertical sync
		config.width = (int)VConfig.get().resolution.x;
		config.height = (int)VConfig.get().resolution.y;			
		config.fullscreen = VConfig.get().fullScreen;
		
		new LwjglApplication(new Volcano3D(), config);
	}
}
