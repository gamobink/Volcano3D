package com.volcano3d;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.badlogic.gdx.ApplicationAdapter;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VStaticAssets;

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
