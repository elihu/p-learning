package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;

public class BaseScene extends ScreenAdapter {
	protected PlearningGame game;
	private boolean keyHandled;
	public BaseScene(PlearningGame plearning){
		game = plearning;
		keyHandled = false;
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
	}
	public void render(float delta){
		super.render(delta);
		
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			if(keyHandled){
				return;
			}
			handleBackPress();
			keyHandled = true;
		}
		else{
			keyHandled = false;
		}
	}
	
	protected void handleBackPress(){
		
	}

}
