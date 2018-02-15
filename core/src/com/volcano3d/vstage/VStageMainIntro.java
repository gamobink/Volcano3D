package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
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
	protected float[] labelShownInterval = {4,6,9,6,6,5};
	private Timer.Task labelShownTimerTask = null;
	protected int currentLabelIndex = 0;
	protected Label titleLabel = null;
	protected float titleLabelYTop = 0;
	protected float titleLabelYInitial = 0;
	protected float titleLabelVisibleDelay = 8;
	
	protected float labelSwipeDuration = 0.8f;
	protected float titleMoveYDuration = 4f;
	
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
	        l.setPosition(0, -160);
	        l.setVisible(false);
	        introGroup.addActor(l);
	        textLabelsArray.add(l);
		}
		
		titleLabelYInitial = main.mainStage.getHeight() * 0.55f;
		titleLabelYTop = main.mainStage.getHeight() - 100;		
		
		//TODO Title label as image
		titleLabel = new Label(VStaticAssets.Text.titleText, new Label.LabelStyle(VStaticAssets.Fonts.calibriLightFont, Color.WHITE));
		titleLabel.setWrap(true);
        titleLabel.setWidth(stageWidth);
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition(0, titleLabelYInitial);        
        //titleLabel.setVisible(false);		
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
			   l.setPosition(-stageWidth, l.getY());
		} 
		titleLabel.setPosition(0, titleLabelYInitial);
	}
	public void startIntroSequence(){
		
		Runnable r = new Runnable(){    
							@Override
						    public void run() {
								startLabelShownTimer(0);
							}};
		
		Label l = textLabelsArray.get(0);
		
		l.setPosition(labelOffset, l.getY());
		l.setColor(1, 1, 1, 0);					
		l.addAction(Actions.sequence(
									Actions.fadeIn(0.7f),
									Actions.run(r)
									));
	}
	
	public void showIntro(){

		reset();
		introGroup.setVisible(true);
		
		introGroup.addAction(Actions.fadeIn(1));
				
		Runnable r = new Runnable(){
			@Override
		    public void run() {
				startIntroSequence();
			}};	
			
		titleLabel.addAction(Actions.sequence(
				Actions.fadeIn(1),
				Actions.delay(titleLabelVisibleDelay),
				Actions.moveTo(titleLabel.getX(), titleLabelYTop, titleMoveYDuration, Interpolation.smooth2),
				Actions.delay(1),
				Actions.run(r)
				));		
		
	}

	public void hideIntro(){
		
		if(labelShownTimerTask != null)labelShownTimerTask.cancel();
		labelShownTimerTask = null;
		
		titleLabel.clearActions();
		for(int i=0; i<textLabelsArray.size; i++){  
			textLabelsArray.get(i).clearActions();			   
		}
		introGroup.addAction(Actions.sequence(Actions.fadeOut(1)));	
	}	
	
	private void transitionToNext(){

		Label l = textLabelsArray.get(currentLabelIndex);
		if(currentLabelIndex < (textLabelsArray.size - 1)){
			l.addAction(Actions.parallel(
										Actions.moveTo(-stageWidth, l.getY(), labelSwipeDuration, Interpolation.swingIn),
										Actions.fadeOut(1.4f)
										));
		}else{
			l.addAction(Actions.sequence(Actions.fadeOut(1)));
		}
		currentLabelIndex++;	
		
		if(currentLabelIndex < textLabelsArray.size){
			Runnable r = new Runnable(){
				@Override
			    public void run() {
					startLabelShownTimer(currentLabelIndex);
				}};			
			l = textLabelsArray.get(currentLabelIndex);
			l.setPosition(stageWidth, l.getY());
			l.addAction(Actions.sequence(
										Actions.moveTo(labelOffset, l.getY(), labelSwipeDuration, Interpolation.swingIn),
										Actions.run(r)
										));
		}else{
			Runnable r = new Runnable(){
				@Override
			    public void run() {
					showIntro();	//start over
				}};	
				
			titleLabel.addAction(Actions.sequence(
					Actions.delay(1.4f),
					Actions.moveTo(titleLabel.getX(), titleLabelYInitial, titleMoveYDuration, Interpolation.smooth2),
					Actions.run(r)
					));				
			
		}
	}
	private void startLabelShownTimer(int labelIndex){
		if(labelIndex < 0 || labelIndex > (textLabelsArray.size-1))return;
		
		if(labelShownTimerTask != null)labelShownTimerTask.cancel();
		
		labelShownTimerTask = Timer.schedule(new Timer.Task() {
	        @Override
	        public void run(){
	        	transitionToNext();
	        }}, labelShownInterval[labelIndex]);
	}
}
