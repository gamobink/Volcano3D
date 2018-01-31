package com.volcano3d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.volcano3d.Volcano3D;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
//		config.width = 1200;config.height = 800;
		config.width = 562;config.height = 1000;		
		
		config.title = "Volcano3D";
		config.samples = 6;
		config.depth = 24;
		config.vSyncEnabled = false; // Setting to false disables vertical sync
		
		new LwjglApplication(new Volcano3D(), config);
	}
}
