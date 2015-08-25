package com.plearning.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TimerActor extends Actor {

	
	
	Vector2 position = new Vector2(200, 200);
	Skin skin;
	PlearningGame game;
	
	LabelStyle style;
	Label counter;
	
	int timeSecs = 90;
	
	
	public TimerActor(PlearningGame g){
		game = g;
		
		style = new LabelStyle();
		style.font = game.manager.get("UI/font.fnt", BitmapFont.class);
		style.fontColor = Color.WHITE;
		
		counter = new Label(""+timeSecs, style);
		/*setPosition(position.x,position.y);
		setWidth(70);
		setHeight(70);*/
	}

	public void draw(Batch batch, float alpha){
		//batch.begin();
		counter.draw(batch, alpha);
			
		
		//batch.end();
	}
}
