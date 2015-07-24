package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public class PlearningGameScene extends ScreenAdapter {
	AssetManager manager;
	
	AtlasRegion background;
	AtlasRegion control;
	AtlasRegion ball;
	AtlasRegion pause;
	AtlasRegion options;
	AtlasRegion play;
	AtlasRegion restart;
	AtlasRegion door;
	
	Vector3 touchPosition;
	
	SpriteBatch batch;
	OrthographicCamera camera;
	TextureAtlas atlas;
	
	Sound tapSound;
	Music music;
	
	PlearningGame game;
	Labyrinth labyrinth;
	float scaleFactorX;
	float scaleFactorY;
	
	static enum GameState{
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS
	}
	
	GameState gameState = GameState.INIT;
	
	
	
	public PlearningGameScene (PlearningGame plearning) {
		
		game = plearning;
		batch = game.batch;
		camera = game.camera;
		manager = game.manager;
		atlas = game.atlas;
		
		scaleFactorX = game.scaleFactorX;
		scaleFactorY = game.scaleFactorY;
		
		background = atlas.findRegion("background-w1");
		control = atlas.findRegion("stopGreen");
		ball = atlas.findRegion("blue");
		pause = atlas.findRegion("pause");
		options = atlas.findRegion("options");
		play = atlas.findRegion("play");
		restart = atlas.findRegion("restart");
		door = atlas.findRegion("door");
		
		touchPosition = new Vector3();
		
		labyrinth = new Labyrinth (game, manager);
		//Sound and Music
		if(game.soundEnabled){
			tapSound = manager.get("SOUNDS/pop.ogg", Sound.class);
			music = manager.get("SOUNDS/music.mp3", Music.class);
			music.setLooping(true);
			music.play();
			music.setVolume(game.soundVolume);
		}
		
		
		
		
	}
	private void updateScene(){
		scaleFactorX = game.scaleFactorX;
		scaleFactorY = game.scaleFactorY;
		if(Gdx.input.justTouched()){
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPosition);
			
			if(game.soundEnabled){
				tapSound.play();
			}
			System.out.println("Touched at: "+ gridValueX(touchPosition.x)+", "+gridValueY(touchPosition.y));
		}
	}
	
	private float gridValueX(float value){
		return Math.round(value/scaleFactorX);
	}
	private float gridValueY(float value){
		return Math.round(value/scaleFactorY);
	}
	
	private void drawScene(){
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();

		batch.disableBlending();
		batch.draw(background, 0, 0);
		batch.enableBlending();
		batch.draw(door, 175, 630);
		//batch.draw(labyrinth, 175, 140);
		//batch.draw(labyrinth, 420, 630);
		batch.draw(door, 420, 140);
		batch.draw(control, 70, 175);
		batch.draw(control, 70, 595);
		batch.draw(ball, 175, 700);
		batch.draw(ball, 245, 735);
		batch.draw(ball, 350, 700);
		batch.draw(ball, 420, 735);
		batch.draw(options, 35, 35);
		batch.draw(restart, 175, 35);
		batch.draw(pause, 280, 35);
		batch.draw(play, 395, 35);
		
		batch.end();
		
		labyrinth.draw();
	}
	private void resetScene(){
		gameState = GameState.INIT;
		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateScene();
		drawScene();
	}
	
	public void dispose(){
		tapSound.dispose();
		music.dispose();
	}
}
