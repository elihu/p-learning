package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlearningGameScene extends BaseScene {
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
	private final Vector2 optionsPosition;
	private final Vector2 restartPosition;
	private final Vector2 control1IniPosition;
	private Vector2 control1Position;
	
	
	private boolean control1Pushed;
	
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
		super(plearning);
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
		
		optionsPosition = new Vector2(35, 35);
		restartPosition = new Vector2(175, 35);
		control1IniPosition = new Vector2(70, 175);
		control1Position = control1IniPosition;
		
		
		control1Pushed = false;
		
		labyrinth = new Labyrinth (game, manager);
		//Sound and Music
		if(game.soundEnabled){
			tapSound = manager.get("SOUNDS/pop.ogg", Sound.class);
			music = manager.get("SOUNDS/music.mp3", Music.class);
			music.setLooping(true);
			
		}
		
		
		
		
	}
	private void captureTouch(){
		if(Gdx.input.justTouched()){
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPosition);
			
			if(game.soundEnabled){
				tapSound.play();
			}
			//Trying to catch touched positions
			System.out.println("Touched at: "+ gridValueX(touchPosition.x)+", "+gridValueY(touchPosition.y));
			if(gridOverlaps(touchPosition.x, optionsPosition.x, touchPosition.y, optionsPosition.y)){
				gameState = GameState.FINISH;
				
			}
			
			if(control1Pushed){
				control1Position.x = ungridValueX(touchPosition.x);
				control1Position.y = ungridValueY(touchPosition.y);
				control1Pushed = false;
			}
			
			else if(gridOverlaps(touchPosition.x, control1Position.x, touchPosition.y, control1Position.y)){
				control1Pushed = true;
				
			}
			else if(gridOverlaps(touchPosition.x, restartPosition.x, touchPosition.y, restartPosition.y)){
				resetScene();
				
			}
			
			
			
		}
		
	}
	private void updateScene(){
		scaleFactorX = game.scaleFactorX;
		scaleFactorY = game.scaleFactorY;
		
		
		if(gameState == GameState.INIT){
			if(game.soundEnabled){
				music.stop();
				music.play();
				music.setVolume(game.soundVolume);
			}
			
			control1Position.x = control1IniPosition.x;
			control1Position.y = control1IniPosition.y;
			gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.CONTROLS){
			captureTouch();
		}
		else if(gameState == GameState.FINISH){
			if(game.soundEnabled){
				music.stop();
			}
			game.setScreen(new MenuScene(game));
		}
		
	}
	
	private int gridValueX(float value){
		return Math.round(value/35);
	}
	private int ungridValueX(float value){
		return gridValueX(value)*35;
	}
	
	private int gridValueY(float value){
		return Math.round(value/35);
	}
	private int ungridValueY(float value){
		return gridValueY(value)*35;
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
		batch.draw(control, control1Position.x, control1Position.y);
		batch.draw(control, 70, 595);
		batch.draw(ball, 175, 700);
		batch.draw(ball, 245, 735);
		batch.draw(ball, 350, 700);
		batch.draw(ball, 420, 735);
		batch.draw(options, optionsPosition.x, optionsPosition.y);
		batch.draw(restart, restartPosition.x, restartPosition.y);
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
	protected void handleBackPress() {
		game.setScreen(new MenuScene(game));
	}
	
	public void dispose(){
		tapSound.dispose();
		music.dispose();
	}
	
	private boolean gridOverlaps(float i1, float i2, float j1, float j2){
		int x1 = gridValueX(i1);
		int x2 = gridValueX(i2);
		int y1 = gridValueY(j1);
		int y2 = gridValueY(j2); 
		
		return (x1==x2 || x1==(x2+1)) && (y1==y2 || y1==(y2+1));
	}
}
