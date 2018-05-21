package com.volcano3d.vstage;

import java.io.PrintStream;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.volcano3d.utility.VByteArrayRingBuffer;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VStaticAssets;

public class VStageConsoleOutput {
	
	public VMain volcano = null;
	public Stage mainStage = null;
	Label l = null;
//	ByteArrayOutputStream baos = null;
	VByteArrayRingBuffer baos = null;
	
	public VStageConsoleOutput(VMain s){
		volcano = s;
		/*
		mainStage = new Stage();
		
		l = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.consoleFont, Color.WHITE));	
        l.setWrap(true);
        l.setWidth(mainStage.getWidth());
        l.setHeight(mainStage.getHeight());
        l.setAlignment(Align.topLeft);
		
        mainStage.addActor(l);

        baos = new VByteArrayRingBuffer(1000);//ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);*/
	}
	
    public void render(){
    //	l.setText(baos.toString());    	
    //	mainStage.draw();
    }	
	
}
