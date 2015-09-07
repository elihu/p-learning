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
		
		if(game.soundEnabled){
			music = manager.get("SOUNDS/themeSong.mp3", Music.class);
			music.setLooping(true);	
		}
		
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
	
	public static void playMusic(){
		music.play();
	}
	
	public static void stopMusic(){
		music.stop();
	}
	
	public static void changeMusic(Music m){
		music = m;
		music.setLooping(true);
	}
	protected void handleBackPress(){
		
	}

}
