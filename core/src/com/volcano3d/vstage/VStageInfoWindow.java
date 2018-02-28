package com.volcano3d.vstage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcore.VStaticAssets;

public class VStageInfoWindow extends Group{
	
	public VStageMain 		mainStage = null;
	
	public Label 	title = null;
    public Label	text = null;
    public Table 	imageSlider = null;
    
    public Vector2	dragLimits = new Vector2();
    public float	dragPos = 0;
    
    public Array<Image>	images = new Array<Image>();
    
    public boolean 	isDragging = false;
    public float	dragOffset = 0;
    
    public final float	imageMargin = 15; 
    
	public VStageInfoWindow(VStageMain main){
		
		mainStage = main;
		
        float infoContentWidth = main.mainStage.getWidth();	// * 0.9f;

        title = new Label("Informatīvais virsraksts", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        title.setWrap(true);
        title.setWidth(infoContentWidth);
        title.setHeight(main.mainStage.getHeight());
        title.setAlignment(Align.top);
        title.setPosition((main.mainStage.getWidth() - infoContentWidth) / 2, 0);
        
        this.addActor(title);
        
        text = new Label(VStaticAssets.Text.methamorphicProcessText, new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        text.setWrap(true);
        text.setWidth(infoContentWidth);
        text.setHeight(210);
        text.setAlignment(Align.top);
        text.setPosition((main.mainStage.getWidth() - infoContentWidth) / 2, 550);
        
        Pixmap labelColor = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        labelColor.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        labelColor.fill();
        text.getStyle().background = new Image(new Texture(labelColor)).getDrawable();        
        
        this.addActor(text);
        
        imageSlider = new Table();
        imageSlider.background(new Image(new Texture(labelColor)).getDrawable());
        imageSlider.setWidth(infoContentWidth);
        imageSlider.setHeight(100);
        imageSlider.setPosition(0, 100);
        
        this.addActor(imageSlider);
        
        labelColor.dispose();
                
        main.mainStage.addActor(this);
	}
	
	public void setTitle(String titleStr){
		title.setText(titleStr);
	}
	public void setText(String textStr, float height){
		text.setText(textStr);
		text.setHeight(height);
	}	
	public void addImage(String imageFileName){
			
		Texture texImage = new Texture(Gdx.files.internal(imageFileName));
		
		float aspect = (float)texImage.getWidth() / (float)texImage.getHeight();
		
        Image image = new Image(texImage);
        image.setName(imageFileName);
        
        float maxHeight = imageSlider.getHeight();
        
        image.setWidth((float)maxHeight * aspect);
        image.setHeight(maxHeight);
        image.setPosition(0, 0);
        
        image.addListener(mainStage);
        
        imageSlider.addActor(image);
        
        images.add(image);
        
		float imgOffset = 0;
		for(int i=0; i<images.size; i++){
			Image img = images.get(i);
			img.setPosition(dragPos + imgOffset, 0);
			imgOffset += (img.getWidth() + imageMargin);
		}	        
	}
	
	public void show(){		
		this.setVisible(true);
		isDragging = false;
		dragOffset = 0;
	}
	public void hide(){
		this.setVisible(false);		
		isDragging = false;
		dragOffset = 0;		
	}
	public void drag(Actor target, float relx, float rely){
		if(!isVisible())return;
		
		if(!isDragging){
			isDragging = true;
			dragOffset = relx;
			return;
		}

		dragPos += (relx - dragOffset);
		
		//System.out.println("dp "+dragPos+"/rel "+relx+"/offs "+dragOffset+" / posx "+target.getX());
		
		float imgOffset = 0;
		for(int i=0; i<images.size; i++){
			Image img = images.get(i);
			img.setPosition(dragPos + imgOffset, 0);
			imgOffset += (img.getWidth() + imageMargin);
		}		
	}
	public void touch(Actor target, float x, float y){
		if(!isVisible())return;
		
		if(!isDragging){

			System.out.println("Open image");
			
			//TODO: show/hide large image
			
		}else{		
			isDragging = false;
			dragOffset = 0;
		}
	}	
}
