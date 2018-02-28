package com.volcano3d.vstage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcore.VStaticAssets;

public class VStageInfoWindow extends Group{
	
	public class ImageExpandable extends Image{
		public ImageExpandable(Texture t){
			super(t);
		}
		public float thumbnailWidth = 0;
		public float thumbnailHeight = 0;
		public float fullWidth = 0;
		public float fullHeight = 0;
		public float fullPositionX = 0;
		public float thumbnailPositionX = 0;		
		public float aspect = 0;	
		public boolean imageOpen = false;
	};
	
	public VStageMain 	mainStage = null;
	
	public Label 		title = null;
    public Label		text = null;
    public Table 		imageSlider = null;    
    public Array<ImageExpandable>	images = new Array<ImageExpandable>();
    
    public float		dragLimit = 0;
    public float		dragPos = 0;    
    public boolean 		isDragging = false;
    public float		dragOffset = 0;    
    public boolean 		isImageOpen = false;    
    public float 		contentWidth = 0;

    //Slider dimensions
    public final float	imageMarginRight = 15; 
    public final float	imageMarginTopBottom = 5;     
    public Vector2 		imageMaxSize = new Vector2(400,400);
    public float 		sliderPositionY = 100;
    public float 		sliderHeight = 100;
    
	public VStageInfoWindow(VStageMain main){
		
		mainStage = main;
		
        contentWidth = main.mainStage.getWidth();	// * 0.9f;

        title = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        title.setWrap(true);
        title.setWidth(contentWidth);
        title.setHeight(main.mainStage.getHeight());
        title.setAlignment(Align.top);
        title.setPosition((main.mainStage.getWidth() - contentWidth) / 2, 0);
        
        this.addActor(title);
        
        text = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        text.setWrap(true);
        text.setWidth(contentWidth);
        text.setHeight(210);
        text.setAlignment(Align.top);
        text.setPosition((main.mainStage.getWidth() - contentWidth) / 2, 550);
        
        Pixmap labelColor = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        labelColor.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        labelColor.fill();
        text.getStyle().background = new Image(new Texture(labelColor)).getDrawable();        
        
        this.addActor(text);
        
        imageSlider = new Table();
        imageSlider.background(new Image(new Texture(labelColor)).getDrawable());
        imageSlider.setWidth(contentWidth);
        imageSlider.setHeight(sliderHeight);
        imageSlider.setPosition(0, sliderPositionY);
        
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
		
		ImageExpandable image = new ImageExpandable(texImage);
        image.setName(imageFileName);
        
        float maxHeight = imageSlider.getHeight() - (imageMarginTopBottom * 2);
        
        image.thumbnailHeight = maxHeight;
        image.thumbnailWidth = (float)maxHeight * aspect;
        image.setWidth(image.thumbnailWidth);
        image.setHeight(image.thumbnailHeight);
        image.setPosition(0, imageMarginTopBottom);
        image.aspect = aspect;
        
		Vector2 sz = new Vector2(image.thumbnailWidth, image.thumbnailHeight);
		float scalex = imageMaxSize.x / sz.x;
		float scaley = imageMaxSize.y / sz.y;
        sz.scl(Math.min(scalex, scaley));
        
        image.fullWidth = sz.x;
        image.fullHeight = sz.y;
        
        image.fullPositionX = (this.contentWidth - image.fullWidth) / 2;
        
        image.addListener(mainStage);
        
        imageSlider.addActor(image);
        
        images.add(image);
        
        recalculateImgPositions();
	}
	public void recalculateImgPositions(){
		float imgOffset = 0;
		for(int i=0; i<images.size; i++){
			ImageExpandable img = images.get(i);
			//img.setWidth(img.thumbnailWidth);
			//img.setHeight(img.thumbnailHeight);
			img.thumbnailPositionX = dragPos + imgOffset;
			img.setPosition(img.thumbnailPositionX, imageMarginTopBottom);
			imgOffset += (img.getWidth() + imageMarginRight);
		}	
		dragLimit = contentWidth - imgOffset + imageMarginRight;
	}
	public void show(){		
		this.setVisible(true);
		isDragging = false;
		dragOffset = 0;
		recalculateImgPositions();
	}
	public void hide(){
		this.setVisible(false);		
		isDragging = false;
		dragOffset = 0;		
	}
	public void act(float delta){
		super.act(delta);
		float imgOffset = 0;
		for(int i=0; i<images.size; i++){
			ImageExpandable img = images.get(i);
			if(img.imageOpen){				
				dragPos = -(imgOffset - img.fullPositionX);
				break;				
			}
			imgOffset += (img.getWidth() + imageMarginRight);
		}
		recalculateImgPositions();
	}
	public void drag(Actor target, float relx, float rely){
		if(!isVisible() || isImageOpen)return;		
		if(!isDragging){
			isDragging = true;
			dragOffset = relx;
			return;
		}
		dragPos += (relx - dragOffset);
		if(dragPos > 0)dragPos=0;
		if(dragPos < dragLimit)dragPos = dragLimit;
		recalculateImgPositions();		
	}
	public void touch(Actor target, float x, float y){
		if(!isVisible())return;
		
		if(target.getClass() == ImageExpandable.class){
			ImageExpandable im = (ImageExpandable)target;
			if(isImageOpen && im.imageOpen){
				isImageOpen = false;
				im.imageOpen = false;
				im.addAction(Actions.parallel(Actions.sizeTo(im.thumbnailWidth, im.thumbnailHeight, 0.3f),
						Actions.moveTo(im.thumbnailPositionX, imageMarginTopBottom, 0.3f)));
				imageSlider.addAction(Actions.sizeTo(imageSlider.getWidth(), sliderHeight, 0.3f));				

				return;
			}
			if(!isDragging){
				if(!isImageOpen){
					im.setZIndex(50);
					for(int i=0; i<images.size; i++){
						ImageExpandable img = images.get(i);
						im.imageOpen = false;

						if(img == target)img.setZIndex(50);
						else img.setZIndex(0);						
					}
					
					im.thumbnailPositionX = im.getX();
					isImageOpen = true;
					im.imageOpen = true;
					im.addAction(Actions.parallel(Actions.sizeTo(im.fullWidth, im.fullHeight, 0.3f),
													Actions.moveTo(im.fullPositionX, imageMarginTopBottom, 0.3f)));
					imageSlider.addAction(Actions.sizeTo(imageSlider.getWidth(), im.fullHeight + (imageMarginTopBottom * 2), 0.3f));
				}
			}else{		
				isDragging = false;
				dragOffset = 0;
			}
		}
	}	
}
