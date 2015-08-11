package com.plearning.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ControlActor extends Actor {

	AtlasRegion image;

	
	Vector2 control1Position = new Vector2(70, 175);
	Vector2 control2Position = new Vector2(70, 245);
	Vector2 control3Position = new Vector2(70, 315);
	Vector2 control4Position = new Vector2(70, 385);
	Vector2 control5Position = new Vector2(70, 455);
	Vector2 control6Position = new Vector2(70, 525);
	Vector2 control7Position = new Vector2(70, 595);
	
	
	TextureAtlas atlas;	
	PlearningGame game;
	
	static enum controlType{
		FLAG, IFLEFT, IFRIGHT, CREATOR, DESTRUCTOR
	}
	
	controlType type;
	
	public ControlActor(PlearningGame g, controlType o, int position){
		game = g;
		atlas = game.atlas;
		type = o;
		
		switch (type){
		case FLAG:
			image = atlas.findRegion("stopGreen");
			break;
		case IFLEFT:
			image = atlas.findRegion("stopYellow");
			break;
		case IFRIGHT:
			image = atlas.findRegion("stopGreen");
			break;
		case CREATOR:
			image = atlas.findRegion("stopYellow");
			break;
		}
		
		switch (position){
		case 1:
			setPosition(control1Position.x,control1Position.y);
			break;
		case 2:
			setPosition(control2Position.x,control2Position.y);
			break;
		case 3:
			setPosition(control3Position.x,control3Position.y);
			break;
		case 4:
			setPosition(control4Position.x,control4Position.y);
			break;
		case 5:
			setPosition(control5Position.x,control5Position.y);
			break;
		case 6:
			setPosition(control6Position.x,control6Position.y);
			break;
		case 7:
			setPosition(control7Position.x,control7Position.y);
			break;
		}
		setWidth(35);
		setHeight(35);
	}

	public void draw(Batch batch, float alpha){
		//batch.begin();
		
		
		batch.draw(image, this.getX(), this.getY());
		
		
		//batch.end();
	}
	
	public boolean overlaps (Vector3 touchPosition){
		if(touchPosition.x >= this.getX() && touchPosition.x<=this.getX()+35)
			if(touchPosition.y >= this.getY() && touchPosition.y<=this.getY()+35)
				return true;
		
		return false;
		
	}
}
