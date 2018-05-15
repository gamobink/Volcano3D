package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcore.VStaticAssets;

public class VStageInfoWindow extends Group{
	
	public class ImageExpandable extends Image{
		public ImageExpandable(){
			super();
		}		
		public ImageExpandable(Texture t){
			super(t);
		}
		public String fileName = "";
		public float thumbnailWidth = 0;
		public float thumbnailHeight = 0;
		public float fullWidth = 0;
		public float fullHeight = 0;
		public float fullPositionX = 0;
		public float thumbnailPositionX = 0;		
		public float aspect = 0;	
		public boolean imageOpen = false;
		public String annotation = ""; 
	};
	
	public VStageMain 	mainStage = null;
	
	public Label 		title = null;
    public Label		text = null;
    public Table		messageTable = null;
    public Table 		imageSliderTable = null;    
    public Array<ImageExpandable>	images = new Array<ImageExpandable>();	
	public Label 		imageLabel = null;
    
    public float		dragLimit = 0;
    public float		dragPos = 0;    
    public boolean 		isDragging = false;
    public float		dragOffset = 0;    
    public boolean 		isImageOpen = false;    
    public float 		contentWidth = 0;
	private float 		contentHeight = 0;

    //Slider dimensions
    public float		imageMarginRight = 0.01875f;			//fraction - 15; 
    public float		imageMarginTopBottom = 0.00625f;		//fraction	- 5;     
    public Vector2 		imageMaxSize = new Vector2(0.5f, 0.5f);	//fraction
    public float 		sliderPositionY = 0.25f;//0.125f;				//fraction
    public float 		sliderHeight = 0.17f;//0.125f;					//fraction
    public float 		titleMargin = 0.0125f;					//fraction - 10;
    public float 		textMargin = 0.025f;					//fraction - 20;
    
    public Array<String>	imagesFileNames = new Array<String>();
    
    public Array<ImageExpandable>	imagesPreloader = new Array<ImageExpandable>();    
    
	public VStageInfoWindow(VStageMain main){
		
		mainStage = main;
		
        contentWidth = main.mainStage.getWidth();
        contentHeight = main.mainStage.getHeight();
        
        //Scale slider dimensions
        imageMarginRight = contentHeight * imageMarginRight;
        imageMarginTopBottom = contentHeight * imageMarginTopBottom;
        sliderHeight = contentHeight * sliderHeight;
        sliderPositionY = contentHeight * sliderPositionY;
        imageMaxSize.scl(contentHeight);
        titleMargin = contentHeight * titleMargin;
        textMargin = contentHeight * textMargin;
        
        title = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.introText, Color.WHITE));	
        title.setWrap(true);
        title.setWidth(contentWidth);
        title.setHeight(40);
        title.setAlignment(Align.top);
        title.setPosition((main.mainStage.getWidth() - contentWidth) / 2, 0);
        
        this.addActor(title);
        
        text = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.futuraFont, Color.WHITE));
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
        
        imageLabel = new Label("", new Label.LabelStyle(VStaticAssets.Fonts.futuraFont, Color.WHITE));
        imageLabel.setWidth(contentWidth);
        imageLabel.setHeight(25);
        imageLabel.setAlignment(Align.top);
        imageLabel.setPosition(0, sliderPositionY - 25 - 5);
        imageLabel.setVisible(false);
        imageLabel.setColor(1, 1, 1, 0);
        
        this.addActor(imageLabel);
        
        labelColor.dispose();
                
        main.mainStage.addActor(this);
        
        this.setVisible(false);
        this.setColor(1, 1, 1, 0);
	}
	
	public void onLoad(){
		for(int i=0; i<imagesPreloader.size; i++){
			initImage(imagesPreloader.get(i));
		}
	}
	
	public void setSliderPositionY(float fractions){
		sliderPositionY = mainStage.mainStage.getHeight() * fractions;
		imageSliderTable.setPosition(0, sliderPositionY);
	}
	
	public void setTitle(String titleStr){
		title.setText(titleStr);
	}
	public void setText(String textStr, float topOffset){		
		topOffset = mainStage.mainStage.getHeight() * topOffset;		
		text.setText(textStr);
		text.setPosition((mainStage.mainStage.getWidth() - (contentWidth - (textMargin * 2))) / 2, text.getPrefHeight() + textMargin);	
		messageTable.setHeight(text.getPrefHeight() + (textMargin * 2));
		
		float top = contentHeight - topOffset - title.getHeight();
		title.setPosition((mainStage.mainStage.getWidth() - contentWidth) / 2, top);
		top = top - titleMargin - messageTable.getHeight();
		messageTable.setPosition(0, top);
	}	
	public void addImage(String fileName, String annotation){
		ImageExpandable img = new ImageExpandable();
		img.fileName = fileName;
		img.annotation = annotation;
		mainStage.volcano.assetsManager.load(fileName, Texture.class);
		imagesPreloader.add(img);
	}
	public void initImage(ImageExpandable image){
		if(!mainStage.volcano.assetsManager.isLoaded(image.fileName)){
			return;
		}
		Texture texImage = mainStage.volcano.assetsManager.get(image.fileName, Texture.class);		
		float aspect = (float)texImage.getWidth() / (float)texImage.getHeight();
				
		image.setDrawable(new TextureRegionDrawable(new TextureRegion(texImage)));
		
        float maxHeight = imageSliderTable.getHeight() - (imageMarginTopBottom * 2);
        
        image.setName(image.fileName);
        
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
	public void addImage(String imageFileName){
		addImage(imageFileName, "");
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
		imageLabel.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.hide()));
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
		float dragrel = (relx - dragOffset);
		if(Math.abs(dragrel) < 6){
			isDragging = false;
			dragOffset = 0;
			return;			
		}
		dragPos += dragrel;
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
							Actions.touchable(Touchable.disabled),	
							Actions.parallel(Actions.sizeTo(im.thumbnailWidth, im.thumbnailHeight, 0.3f),
											Actions.moveTo(im.thumbnailPositionX, imageMarginTopBottom, 0.3f)),
							Actions.run(new MyRun(im)),
							Actions.touchable(Touchable.enabled)
							)
						);
				imageSliderTable.addAction(Actions.sizeTo(imageSliderTable.getWidth(), sliderHeight, 0.3f));				
				imageLabel.clearActions();
				imageLabel.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.hide()));
				
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
					im.addAction(Actions.sequence(
							Actions.touchable(Touchable.disabled),
							Actions.parallel(
									Actions.sizeTo(im.fullWidth, im.fullHeight, 0.3f),
									Actions.moveTo(im.fullPositionX, imageMarginTopBottom, 0.3f)
									),
							Actions.touchable(Touchable.enabled)
							));
					
					imageSliderTable.addAction(Actions.sizeTo(imageSliderTable.getWidth(), im.fullHeight + (imageMarginTopBottom * 2), 0.3f));

					imageLabel.clearActions();
					imageLabel.setText(im.annotation);
					imageLabel.setColor(1,1,1,0);
					imageLabel.addAction(Actions.sequence(Actions.delay(0.3f),Actions.show(), Actions.fadeIn(0.2f)));
				}
			}else{		
				isDragging = false;
				dragOffset = 0;
			}
		}
	}	
}
