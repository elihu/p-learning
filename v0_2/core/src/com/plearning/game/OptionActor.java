package com.plearning.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class OptionActor extends Actor {

	AtlasRegion image;
	
	Vector2 optionsPosition = new Vector2(35, 35);;
	Vector2 restartPosition = new Vector2(175,35);
	Vector2 pausePosition = new Vector2(280, 35);
	Vector2 playPosition = new Vector2(395, 35);
	
	TextureAtlas atlas;	
	PlearningGame game;
	
	static enum optionType{
		PLAY, PAUSE, OPTIONS, RESTART
	}
	
	optionType type;
	
	public OptionActor(PlearningGame g, optionType o){
		game = g;
		atlas = game.atlas;
		type = o;
		
		switch (type){
		case PLAY:
			image = atlas.findRegion("play");
			setPosition(playPosition.x,playPosition.y);
			break;
		case PAUSE:
			image = atlas.findRegion("pause");
			setPosition(pausePosition.x,pausePosition.y);
			break;
		case OPTIONS:
			image = atlas.findRegion("options");
			setPosition(optionsPosition.x,optionsPosition.y);
			break;
		case RESTART:
			image = atlas.findRegion("restart");
			setPosition(restartPosition.x,restartPosition.y);
			break;
		}
		setWidth(70);
		setHeight(70);
	}

	public void draw(Batch batch, float alpha){
		//batch.begin();
		
		
		batch.draw(image, this.getX(), this.getY());
			
		
		//batch.end();
	}
}
