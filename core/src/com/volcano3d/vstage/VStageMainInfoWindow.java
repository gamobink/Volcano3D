package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.volcano3d.vcore.VStaticAssets;

public class VStageMainInfoWindow {

    public Label	infoText1 = null;

	VStageMainInfoWindow(VStageMain main){
		
        float infoTextWwidth = main.mainStage.getWidth() * 0.9f;
        infoText1 = new Label(VStaticAssets.Text.methamorphicProcessText, new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        infoText1.setWrap(true);
        infoText1.setWidth(infoTextWwidth);
        infoText1.setHeight(main.mainStage.getHeight());
        infoText1.setAlignment(Align.top);
        infoText1.setPosition((main.mainStage.getWidth() - infoTextWwidth) / 2, 0);
        
        infoText1.setVisible(false);
        
        main.mainStage.addActor(infoText1);
	}
	
}
