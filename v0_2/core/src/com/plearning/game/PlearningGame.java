package com.plearning.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlearningGame extends ApplicationAdapter {
	SpriteBatch batch;
	AtlasRegion background;
	AtlasRegion control;
	AtlasRegion labyrinth;
	AtlasRegion ball;
	AtlasRegion pause;
	AtlasRegion options;
	AtlasRegion play;
	AtlasRegion restart;
	AtlasRegion door;
	
	Vector3 touchPosition;

	OrthographicCamera camera;
	Viewport viewport;
	
	float windowHeight =800;
	float windowWidth = 480;
	
	TextureAtlas atlas;
	
	Sound tapSound;
	Music music;
	
	float scaleFactorX;
	float scaleFactorY;
	
	static enum GameState{
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS
	}
	
	GameState gameState = GameState.INIT;
	
	FPSLogger fpsLogger; //prints fps in-game, only in dev
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("PLearning.pack"));
		
		background = atlas.findRegion("background-w1");
		labyrinth = atlas.findRegion("floor-w1");
		control = atlas.findRegion("stopGreen");
		ball = atlas.findRegion("blue");
		pause = atlas.findRegion("pause");
		options = atlas.findRegion("options");
		play = atlas.findRegion("play");
		restart = atlas.findRegion("restart");
		door = atlas.findRegion("door");
		
		touchPosition = new Vector3();
		
		//Sound and Music
		tapSound = Gdx.audio.newSound(Gdx.files.internal("SOUNDS/pop.ogg"));
		music = Gdx.audio.newMusic(Gdx.files.internal("SOUNDS/music.mp3"));
		music.setLooping(true);
		music.play();
		
		//Camera
		camera = new OrthographicCamera();
		//camera.setToOrtho(false, windowWidth, windowHeight);
		camera.position.set((windowWidth*0.5f), (windowHeight*0.5f), 0);
		viewport = new FitViewport(windowWidth, windowHeight, camera);
		scaleFactorX = Math.round(windowWidth/14);
		scaleFactorY = Math.round(windowHeight/23);
		
		fpsLogger = new FPSLogger();//prints fps in-game, only in dev
	}
	private void updateScene(){
		
		if(Gdx.input.justTouched()){
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//camera.unproject(touchPosition);
			
			tapSound.play();
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
		batch.draw(labyrinth, 175, 140);
		batch.draw(labyrinth, 420, 630);
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
	}
	private void resetScene(){
		
	}
	
	public void resize(int width, int height){
		viewport.update(width, height);
		scaleFactorX = Math.round(width/14);
		scaleFactorY = Math.round(height/23);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		fpsLogger.log();//prints fps in-game, only in dev
		
		updateScene();
		drawScene();
	}
	
	public void dispose(){
		tapSound.dispose();
		music.dispose();
		batch.dispose();
		atlas.dispose();
	}
}
