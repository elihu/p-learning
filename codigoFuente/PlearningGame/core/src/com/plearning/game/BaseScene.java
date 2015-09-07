package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.ScreenAdapter;

public class BaseScene extends ScreenAdapter {
	protected PlearningGame game;
	private boolean keyHandled;
	
	AssetManager manager;
	static Music music;
	
	public BaseScene(PlearningGame plearning){
		game = plearning;
		keyHandled = false;
		//For music
		manager = game.manager;
		
		
		music = manager.get("SOUNDS/themeSong.mp3", Music.class);
		music.setLooping(true);	
		
		
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
	
	public void playMusic(){
		if(game.soundEnabled){
			music.play();
		}
	}
	
	public void stopMusic(){
		if(game.soundEnabled){
			music.stop();
		}
	}
	
	public void changeMusic(Music m){
		if(game.soundEnabled){
			music = m;
			music.setLooping(true);
		}
		
	}
	protected void handleBackPress(){
		
	}

}
