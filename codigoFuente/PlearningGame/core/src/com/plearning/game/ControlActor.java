package com.plearning.game;

import java.util.Vector;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.plearning.game.PlearningGameScene.Color;

public class ControlActor extends Actor {

	AtlasRegion image;
	public static Vector<Vector2> controlsPositions = new Vector<Vector2>();	
	int index;
	TextureAtlas atlas;	
	PlearningGame game;
	
	private Rectangle body;
	
	static enum controlType{
		FLAG, IFLEFT, IFRIGHT, CREATOR, DESTRUCTOR, CONVERTER, DIRCHANGER
	}
	
	controlType type;
	Color color;
	
	boolean activated;
	//Sound
	AssetManager manager;
	Sound tapSound;
	
	public ControlActor(PlearningGame g, controlType o, Color c, int position){
		game = g;
		atlas = game.atlas;
		type = o;
		index = position;
		color = c;
		activated = false;
		manager = game.manager;
		
		
		switch (type){
		case FLAG:
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
			image = atlas.findRegion("duplicator");
			break;
		case DESTRUCTOR:
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
			if (game.soundEnabled){
				tapSound =manager.get("SOUNDS/pop.ogg", Sound.class);
			}
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
		
		
		
		super.setPosition(controlsPositions.get(position).x,controlsPositions.get(position).y);
		setWidth(35);
		setHeight(35);
		body = new Rectangle(this.getX(), this.getY(), 3, 7);
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
	
	public void playTap(){
		if (game.soundEnabled){
			tapSound.play();
		}
	}
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		body.x = x;
		body.y = y;
	}
	public void resetPosition(){
		this.setX(controlsPositions.get(index).x);
		this.setY(controlsPositions.get(index).y);
		body.x = this.getX();
		body.y = this.getY();
	}
	public void triggerAction(BallActor ball){
		
		switch (type){
			case FLAG:
				if(!activated && ball.type == color){
					playTap();
					//TODO. Have to think about a new function for this control
					activated = true;
					
				}
				break;
			case IFLEFT:
				if(!activated && ball.type == color){
					playTap();
					ball.dirLeft();
					ball.translate(-20, 0);
					activated = true;
				}
				break;
			case IFRIGHT:
				if(!activated && ball.type == color){
					playTap();
					ball.dirRight();
					ball.translate(35, 0);
					activated = true;
				}
				break;
			case CREATOR:
				playTap();
				ball.translate(35, 0);
				BallActor.nBallsIn--;
				break;
			case DESTRUCTOR:
				if(ball.type == color){
					playTap();
					//ball.setVisible(false);
					/*ball.translate(0,0);
					ball.remove();
					BallActor.nBallsIn--;*/
				}
				break;
				
			case CONVERTER:
				playTap();
				ball.changeColor(color);
				break;
				
			case DIRCHANGER:
				if(ball.type == color){
					ball.changeDir();
				}
				break;
		}
		
	}

	public Rectangle getBody(){return body;}
}
