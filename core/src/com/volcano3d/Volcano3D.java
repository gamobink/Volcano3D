package com.volcano3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.volcano3d.vcore.VMain;

public class Volcano3D extends ApplicationAdapter {
	protected VMain volcano = null;
	@Override
	public void create () {		
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
