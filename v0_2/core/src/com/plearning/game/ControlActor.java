package com.plearning.game;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ControlActor extends Actor {

	AtlasRegion image;
	public static Vector<Vector2> controlsPositions = new Vector<Vector2>();	
	int index;
	TextureAtlas atlas;	
	PlearningGame game;
	
	static enum controlType{
		FLAG, IFLEFT, IFRIGHT, CREATOR, DESTRUCTOR, CONVERTER, DIRCHANGER
	}
	static enum controlColor{
		RED, BLUE, GREEN, YELLOW
	}
	controlType type;
	controlColor color;
	
	public ControlActor(PlearningGame g, controlType o, controlColor c, int position){
		game = g;
		atlas = game.atlas;
		type = o;
		index = position;
		color = c;
		
		
		switch (type){
		case FLAG:
			switch(color){
			case RED:
				image = atlas.findRegion("stopRed");
				break;
			case BLUE:
				image = atlas.findRegion("stopBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("stopYellow");
				break;
			case GREEN:
				image = atlas.findRegion("stopGreen");
				break;
			}
			
			break;
		case IFLEFT:
			switch(color){
			case RED:
				image = atlas.findRegion("ifleftRed");
				break;
			case BLUE:
				image = atlas.findRegion("ifleftBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("ifleftYellow");
				break;
			case GREEN:
				image = atlas.findRegion("ifleftGreen");
				break;
			}
			break;
		case IFRIGHT:
			switch(color){
			case RED:
				image = atlas.findRegion("ifrightRed");
				break;
			case BLUE:
				image = atlas.findRegion("ifrightBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("ifrightYellow");
				break;
			case GREEN:
				image = atlas.findRegion("ifrightGreen");
				break;
			}
			break;
		case CREATOR:
			image = atlas.findRegion("duplicator");
			break;
		case DESTRUCTOR:
			switch(color){
			case RED:
				image = atlas.findRegion("deleteRed");
				break;
			case BLUE:
				image = atlas.findRegion("deleteBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("deleteYellow");
				break;
			case GREEN:
				image = atlas.findRegion("deleteGreen");
				break;
			}
			break;
			
		case CONVERTER:
			switch(color){
			case RED:
				image = atlas.findRegion("convertRed");
				break;
			case BLUE:
				image = atlas.findRegion("convertBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("convertYellow");
				break;
			case GREEN:
				image = atlas.findRegion("convertGreen");
				break;
			}
			break;
			
		case DIRCHANGER:
			switch(color){
			case RED:
				image = atlas.findRegion("dirchangeRed");
				break;
			case BLUE:
				image = atlas.findRegion("dirchangeBlue");
				break;
			case YELLOW:
				image = atlas.findRegion("dirchangeYellow");
				break;
			case GREEN:
				image = atlas.findRegion("dirchangeGreen");
				break;
			}
			break;
		}
		
		
		
		setPosition(controlsPositions.get(position).x,controlsPositions.get(position).y);
		
		setWidth(35);
		setHeight(35);
	}
	public static void initialize(){
		controlsPositions.add(new Vector2(70,175));
		controlsPositions.add(new Vector2(70, 245));
		controlsPositions.add(new Vector2(70, 315));
		controlsPositions.add(new Vector2(70, 385));
		controlsPositions.add(new Vector2(70, 455));
		controlsPositions.add(new Vector2(70, 525));
		controlsPositions.add(new Vector2(70, 595));
	}
	public void draw(Batch batch, float alpha){		
		
		batch.draw(image, this.getX(), this.getY());
	}
	
	public boolean overlaps (Vector3 touchPosition){
		if(touchPosition.x >= this.getX() && touchPosition.x<=this.getX()+35)
			if(touchPosition.y >= this.getY() && touchPosition.y<=this.getY()+35)
				return true;
		
		return false;
		
	}
	
	public void resetPosition(){
		this.setX(controlsPositions.get(index).x);
		this.setY(controlsPositions.get(index).y);
	}
}
