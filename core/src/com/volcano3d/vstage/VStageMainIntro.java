package com.volcano3d.vstage;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.volcano3d.vcore.VStaticAssets;

public class VStageMainIntro {

	protected Group introGroup;
	protected Array<Label> textLabelsArray = new Array<Label>(); 
	protected float stageWidth;
	protected float labelOffset;
	protected float[] labelShownInterval = {2,2,3,3,4,3};
	private Timer.Task labelShownTimerTask = null;
	protected int currentLabelIndex = 0;
	protected Label titleLabel = null;
	
	public VStageMainIntro(VStageMain main){
		
		stageWidth = main.mainStage.getWidth();
		float width = stageWidth * 0.9f;
		labelOffset = (stageWidth - width) / 2;
		
		introGroup = new Group();
		
		Label l;
		
		String texts[] = {
				VStaticAssets.Text.intro1Text,
				VStaticAssets.Text.intro2Text,
				VStaticAssets.Text.intro3Text,
				VStaticAssets.Text.intro4Text,
				VStaticAssets.Text.intro5Text,
				VStaticAssets.Text.intro6Text
		};
		for(int i=0; i<6; i++){
	        l = new Label(texts[i], new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
	        l.setWrap(true);
	        l.setWidth(width);
	        l.setHeight(main.mainStage.getHeight());
	        l.setAlignment(Align.top);
	        l.setVisible(false);		
	        introGroup.addActor(l);
	        textLabelsArray.add(l);
		}
		
		titleLabel = new Label(VStaticAssets.Text.titleText, new Label.LabelStyle(VStaticAssets.Fonts.calibriLightFont, Color.WHITE));
		titleLabel.setWrap(true);
        titleLabel.setWidth(stageWidth);
        titleLabel.setHeight(50);
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition(0, 400);        
        titleLabel.setVisible(false);		
        introGroup.addActor(titleLabel);
		
        main.mainStage.addActor(introGroup);
	}
	public void reset(){
		introGroup.setVisible(false);		
		currentLabelIndex = 0;
		for(int i=0; i<textLabelsArray.size; i++){  
			   Label l = textLabelsArray.get(i);
			   l.setVisible(true);
			   l.addAction(Actions.fadeIn(0));
			   l.setPosition(-stageWidth, 0);
		} 
	}
	public void startIntroSequence(){
		reset();
		introGroup.setVisible(true);
		
		Runnable r = new Runnable(){    
							@Override
						    public void run() {
								startLabelShownTimer(0);
							}};
		
		textLabelsArray.get(0).setPosition(labelOffset, 0);
							
		//Show first label with fadeIn
		textLabelsArray.get(0).addAction(Actions.sequence(Actions.fadeOut(0),
														Actions.show(),
														Actions.fadeIn(0.7f),
														Actions.run(r)));
	}
	public void transitionToNext(){

		textLabelsArray.get(currentLabelIndex).addAction(Actions.sequence(Actions.moveTo(-stageWidth, 0, 1)));

		currentLabelIndex++;
		if(currentLabelIndex < 6){
			Runnable r = new Runnable(){
				@Override
			    public void run() {
					startLabelShownTimer(currentLabelIndex);
				}};			
			textLabelsArray.get(currentLabelIndex).setPosition(stageWidth, 0);
			textLabelsArray.get(currentLabelIndex).addAction(Actions.sequence(Actions.moveTo(labelOffset, 0, 1),
																				Actions.run(r)));
		}else{
			textLabelsArray.get(currentLabelIndex-1).addAction(Actions.sequence(Actions.fadeOut(1)));			
			//TODO: Show title
			
		}
	}
	private void startLabelShownTimer(int labelIndex){
		if(labelIndex < 0 || labelIndex > 5)return;
		
		if(labelShownTimerTask != null)labelShownTimerTask.cancel();
		
		labelShownTimerTask = Timer.schedule(new Timer.Task() {
	        @Override
	        public void run(){
	        	transitionToNext();
	        }}, labelShownInterval[labelIndex]);
	}
}
