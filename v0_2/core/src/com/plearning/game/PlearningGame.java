package com.plearning.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlearningGame extends Game {
	public static final int windowHeight =800;
	public static final int windowWidth = 480;
	
	SpriteBatch batch;
	OrthographicCamera camera;
	Viewport viewport;
	TextureAtlas atlas;
	
	FPSLogger fpsLogger; //prints fps in-game, only in dev
	
	float scaleFactorX;
	float scaleFactorY;
	
	AssetManager manager;
	public boolean soundEnabled;
	public float soundVolume;
	
	public PlearningGame(){
		
		fpsLogger = new FPSLogger();//prints fps in-game, only in dev
		//Camera
		camera = new OrthographicCamera();
		//camera.setToOrtho(false, windowWidth, windowHeight);
		camera.position.set((windowWidth*0.5f), (windowHeight*0.5f), 0);
		viewport = new FitViewport(windowWidth, windowHeight, camera);
		
		manager = new AssetManager();
	}
	@Override
	public void create() {
		scaleFactorX = Math.round(windowWidth/14);
		scaleFactorY = Math.round(windowHeight/23);
		
		/*//assets  (Moved to LoadingScreen)
		manager.load("SOUNDS/pop.ogg", Sound.class);
		manager.load("SOUNDS/music.mp3", Music.class);
		manager.load("PLearning.pack", TextureAtlas.class);
		manager.finishLoading();	
		atlas = manager.get("PLearning.pack", TextureAtlas.class);
		*/
		batch = new SpriteBatch();
		setScreen(new LoadingScreen(this));
		
		
		
	}
	public void render(){
		fpsLogger.log();//prints fps in-game, only in dev
		super.render();
	}
	
	public void resize(int width, int height){
		viewport.update(width, height);
		scaleFactorX = Math.round(width/14);
		scaleFactorY = Math.round(height/23);
		
	}
	public void dispose(){
		batch.dispose();
		atlas.dispose();
	}
}
