package com.volcano3d;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.badlogic.gdx.ApplicationAdapter;
import com.volcano3d.vcore.VMain;

public class Volcano3D extends ApplicationAdapter {
	protected VMain volcano = null;
	@Override
	public void create () {		
		
//		try{
//			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
//		}catch(FileNotFoundException e){	}		
		
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
