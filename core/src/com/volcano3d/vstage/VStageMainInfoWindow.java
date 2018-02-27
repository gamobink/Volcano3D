package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.volcano3d.vcore.VStaticAssets;

public class VStageMainInfoWindow {

	public Label 	title = null;
    public Label	text = null;

    //public Array<Image>	images = null;
    
	public VStageMainInfoWindow(VStageMain main){
		
        float infoTextWidth = main.mainStage.getWidth();	// * 0.9f;

        title = new Label("Informatīvais virsraksts", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        title.setWrap(true);
        title.setWidth(infoTextWidth);
        title.setHeight(main.mainStage.getHeight());
        title.setAlignment(Align.top);
        title.setPosition((main.mainStage.getWidth() - infoTextWidth) / 2, 0);      
        
      //  main.mainStage.addActor(title);
        
        text = new Label(VStaticAssets.Text.methamorphicProcessText, new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        text.setWrap(true);
        text.setWidth(infoTextWidth);
        text.setHeight(210);
        text.setAlignment(Align.top);
        text.setPosition((main.mainStage.getWidth() - infoTextWidth) / 2, 550);
        
  //      text.setVisible(false);
        
        Pixmap labelColor = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        labelColor.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        labelColor.fill();
        text.getStyle().background = new Image(new Texture(labelColor)).getDrawable();        
        
        labelColor.dispose();
        
    //    main.mainStage.addActor(text);
	}
	
	public void show(){
		
		
	}
	public void hide(){
		
		
	}
	
}
