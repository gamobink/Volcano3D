package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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
    public Table		messageTable = null;
    public Table 		imageSliderTable = null;    
    public Array<ImageExpandable>	images = new Array<ImageExpandable>();
    
    public float		dragLimit = 0;
    public float		dragPos = 0;    
    public boolean 		isDragging = false;
    public float		dragOffset = 0;    
    public boolean 		isImageOpen = false;    
    public float 		contentWidth = 0;
	private float 		contentHeight = 0;

    //Slider dimensions
    public final float		imageMarginRight = 15; 
    public final float		imageMarginTopBottom = 5;     
    public final Vector2 	imageMaxSize = new Vector2(400,400);
    public final float 		sliderPositionY = 100;
    public final float 		sliderHeight = 100;
    public final float 		titleMargin = 10;
    public final float 		textMargin = 20;
    
    public Array<String>	imagesFileNames = new Array<String>();
    
	public VStageInfoWindow(VStageMain main){
		
		mainStage = main;
		
        contentWidth = main.mainStage.getWidth();
        contentHeight = main.mainStage.getHeight();

        title = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));	
        title.setWrap(true);
        title.setWidth(contentWidth);
        title.setHeight(25);
        title.setAlignment(Align.top);
        title.setPosition((main.mainStage.getWidth() - contentWidth) / 2, 0);
        
        this.addActor(title);
        
        text = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.calibri25Font, Color.WHITE));
        text.setWrap(true);
        text.setWidth(contentWidth - (textMargin * 2));
        text.setAlignment(Align.top);
        text.setPosition((main.mainStage.getWidth() - (contentWidth - (textMargin * 2))) / 2, 0);
        
        Pixmap labelColor = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        labelColor.setColor(0.1f, 0.1f, 0.1f, 0.55f);
        labelColor.fill();

        messageTable = new Table();
        messageTable.background(new Image(new Texture(labelColor)).getDrawable());
        messageTable.setWidth(contentWidth);
        messageTable.addActor(text);
        
        this.addActor(messageTable);
        
        imageSliderTable = new Table();
        imageSliderTable.background(new Image(new Texture(labelColor)).getDrawable());
        imageSliderTable.setWidth(contentWidth);
        imageSliderTable.setHeight(sliderHeight);
        imageSliderTable.setPosition(0, sliderPositionY);
        
        this.addActor(imageSliderTable);
        
        labelColor.dispose();
                
        main.mainStage.addActor(this);
        
        this.setVisible(false);
	}
	
	public void onLoad(){
		for(int i=0; i<imagesFileNames.size; i++){
			initCreateImage(imagesFileNames.get(i));
		}
	}
	
	public void setTitle(String titleStr){
		title.setText(titleStr);
	}
	public void setText(String textStr, float topOffset){
		text.setText(textStr);
		text.setPosition((mainStage.mainStage.getWidth() - (contentWidth - (textMargin * 2))) / 2, text.getPrefHeight() + textMargin);	
		messageTable.setHeight(text.getPrefHeight() + (textMargin * 2));
		
		float top = contentHeight - topOffset - title.getHeight();
		title.setPosition((mainStage.mainStage.getWidth() - contentWidth) / 2, top);
		top = top - titleMargin - messageTable.getHeight();
		messageTable.setPosition(0, top);
	}	
	public void addImage(String imageFileName){
		imagesFileNames.add(imageFileName);
		mainStage.volcano.assetsManager.load(imageFileName, Texture.class);
	}
	public void initCreateImage(String imageFileName){
		
		if(!mainStage.volcano.assetsManager.isLoaded(imageFileName)){
			return;
		}
			
		Texture texImage = mainStage.volcano.assetsManager.get(imageFileName, Texture.class);
		
		float aspect = (float)texImage.getWidth() / (float)texImage.getHeight();
		
		ImageExpandable image = new ImageExpandable(texImage);
        image.setName(imageFileName);
        
        float maxHeight = imageSliderTable.getHeight() - (imageMarginTopBottom * 2);
        
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
        
        imageSliderTable.addActor(image);
        
        images.add(image);
        
        float w = recalculateResetImgPositions();
        
        if(w < contentWidth){
        	dragPos = (contentWidth - w)/2;
        	recalculateResetImgPositions();
        }else{
        	dragPos = 0;
        }
	}
	public float recalculateResetImgPositions(){
		float imgOffset = 0;
		for(int i=0; i<images.size; i++){
			ImageExpandable img = images.get(i);
			img.setWidth(img.thumbnailWidth);
			img.setHeight(img.thumbnailHeight);
			img.imageOpen = false;
			img.thumbnailPositionX = dragPos + imgOffset;
			img.setPosition(img.thumbnailPositionX, imageMarginTopBottom);
			imgOffset += (img.getWidth() + imageMarginRight);
		}	
		dragLimit = contentWidth - imgOffset + imageMarginRight;
		if(dragLimit > 0)dragLimit = (contentWidth - imgOffset)/2;
		isImageOpen = false;
		imageSliderTable.setHeight(sliderHeight);
		return imgOffset; 
	}
	public void show(){		
		isDragging = false;
		dragOffset = 0;
		recalculateResetImgPositions();
		this.clearActions();
		this.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.8f)));
	}
	public void hide(){
		isDragging = false;
		dragOffset = 0;
		this.clearActions();
		this.addAction(Actions.sequence(Actions.fadeOut(0.8f), Actions.hide()));
	}
	public void act(float delta){
		super.act(delta);
		float imgOpenOffset = 0;
		float imgOpenX = 0;
		boolean isOpen = false;
		for(int i=0; i<images.size; i++){
			ImageExpandable img = images.get(i);
			if(img.imageOpen){				
				imgOpenX = img.getX();
				isOpen = true;
				break;
			}
			imgOpenOffset += (img.getWidth() + imageMarginRight);
		}
		if(isOpen){
			float offsetStart = -(imgOpenOffset - imgOpenX);
			for(int i=0; i<images.size; i++){
				ImageExpandable img = images.get(i);
				img.setPosition(offsetStart, imageMarginTopBottom);
				offsetStart += (img.getWidth() + imageMarginRight);
			}
		}
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
		recalculateResetImgPositions();		
	}
	public void touch(Actor target, float x, float y){
		if(!isVisible())return;
		
		if(target.getClass() == ImageExpandable.class){
			ImageExpandable im = (ImageExpandable)target;
			if(isImageOpen && im.imageOpen){
				
				class MyRun implements Runnable{
					public MyRun(ImageExpandable i){
						im = i;
					}
					ImageExpandable im;
					@Override
				    public void run() {
						isImageOpen = false;
						im.imageOpen = false;
					}
				}

				im.addAction(
						Actions.sequence(
							Actions.parallel(Actions.sizeTo(im.thumbnailWidth, im.thumbnailHeight, 0.3f),
											Actions.moveTo(im.thumbnailPositionX, imageMarginTopBottom, 0.3f)),
							Actions.run(new MyRun(im))
							)
						);
				imageSliderTable.addAction(Actions.sizeTo(imageSliderTable.getWidth(), sliderHeight, 0.3f));				

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
					imageSliderTable.addAction(Actions.sizeTo(imageSliderTable.getWidth(), im.fullHeight + (imageMarginTopBottom * 2), 0.3f));
				}
			}else{		
				isDragging = false;
				dragOffset = 0;
			}
		}
	}	
}
