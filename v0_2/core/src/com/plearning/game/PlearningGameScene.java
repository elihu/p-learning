package com.plearning.game;

import java.util.Vector;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlearningGameScene extends BaseScene {
	AssetManager manager;
	
	AtlasRegion background;
	//AtlasRegion ball;
	
	//AtlasRegion restart;
	AtlasRegion door;
	
	//Actors and Stage
	Stage stage;
	OptionActor options;
	OptionActor restart;
	OptionActor play;
	OptionActor pause;
	
	Vector<ControlActor> controls;
	Vector<BallActor> ballsIn;
	Vector<BallActor> ballsOut;
	
	int nControls = 7;
	int nBallsIn = 4;
	int nBallsOut = 3;
	
	boolean [] controlsPushed = {false, false, false, false, false, false, false};
	Vector3 touchPosition;
	Vector2 controlPosition;
	
	
	SpriteBatch batch;
	OrthographicCamera camera;
	TextureAtlas atlas;
	
	Sound tapSound;
	Music music;
	
	PlearningGame game;
	Labyrinth labyrinth;
	int scaleFactorX;
	int scaleFactorY;
	
	static enum GameState{
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS, START
	}
	
	GameState gameState = GameState.INIT;
	
	
	
	public PlearningGameScene (PlearningGame plearning) {
		super(plearning);
		game = plearning;
		
		/*Static initializations*/
		ControlActor.initialize();
		BallActor.initialize();
		//-----------------------
		
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		
		batch = game.batch;
		camera = game.camera;
		manager = game.manager;
		atlas = game.atlas;
		
		scaleFactorX = game.scaleFactorX;
		scaleFactorY = game.scaleFactorY;
		background = atlas.findRegion("background-w1");
		
		//ball = atlas.findRegion("blue");
		door = atlas.findRegion("door");
		
		options = new OptionActor(game, OptionActor.optionType.OPTIONS);
		restart = new OptionActor(game, OptionActor.optionType.RESTART);
		play = new OptionActor(game, OptionActor.optionType.PLAY);
		pause = new OptionActor(game, OptionActor.optionType.PAUSE);
		
		//Here we choose the controls
		/* watch out here --> int nControls = 7; */
		controls = new Vector<ControlActor>();
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, ControlActor.controlColor.YELLOW, 0));
		controls.add(new ControlActor(game, ControlActor.controlType.IFLEFT, ControlActor.controlColor.YELLOW, 1));
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, ControlActor.controlColor.RED, 2));
		controls.add(new ControlActor(game, ControlActor.controlType.CREATOR, ControlActor.controlColor.BLUE, 3));
		controls.add(new ControlActor(game, ControlActor.controlType.CONVERTER, ControlActor.controlColor.BLUE, 4));
		controls.add(new ControlActor(game, ControlActor.controlType.IFRIGHT, ControlActor.controlColor.BLUE, 5));
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, ControlActor.controlColor.BLUE, 6));
		
		//Here we choose the balls in and out
		ballsIn = new Vector<BallActor>();
		ballsOut = new Vector<BallActor>();
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		ballsIn.addElement(new BallActor(game, BallActor.BallType.BLUE, BallActor.BallInOut.IN, 0));
		ballsIn.addElement(new BallActor(game, BallActor.BallType.RED, BallActor.BallInOut.IN, 1));
		ballsIn.addElement(new BallActor(game, BallActor.BallType.RED, BallActor.BallInOut.IN, 2));
		ballsIn.addElement(new BallActor(game, BallActor.BallType.YELLOW, BallActor.BallInOut.IN, 3));
		
		ballsOut.addElement(new BallActor(game, BallActor.BallType.BLUE, BallActor.BallInOut.OUT, 0));
		ballsOut.addElement(new BallActor(game, BallActor.BallType.BLUE, BallActor.BallInOut.OUT, 1));
		ballsOut.addElement(new BallActor(game, BallActor.BallType.BLUE, BallActor.BallInOut.OUT, 2));
		
		touchPosition = new Vector3(0,0,0);
		controlPosition = new Vector2(0,0);
		
		
		labyrinth = new Labyrinth (game, manager);
		//Sound and Music
		if(game.soundEnabled){
			tapSound = manager.get("SOUNDS/pop.ogg", Sound.class);
			music = manager.get("SOUNDS/music.mp3", Music.class);
			music.setLooping(true);
			
		}
		
		stage.addActor(restart);
		stage.addActor(options);
		stage.addActor(play);
		stage.addActor(pause);
		
		for(int i=0; i<nControls;i++){
			stage.addActor(controls.get(i));
		}
		
		for(int i=0; i<nBallsIn;i++){
			stage.addActor(ballsIn.get(i));
		}
		
		for(int i=0; i<nBallsOut;i++){
			stage.addActor(ballsOut.get(i));
		}
		
		restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetScene();
            }
        });
		options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameState = GameState.FINISH;
            }
        });
		play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameState = GameState.START;
            }
        });
		pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameState = GameState.PAUSE;
            }
        });
		
		/*Static initializations*/
		labyrinth.initialize();
		//-----------------------
		
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
			
			for(int i=0;i<nControls;i++){
				if(controlsPushed[i]){
					controlPosition.x = ungridValueX(touchPosition.x);
					controlPosition.y = ungridValueY(touchPosition.y);
					if(labyrinth.validatePosition(new Vector2 (gridValueX(controlPosition.x), gridValueY(controlPosition.y))))
						controls.get(i).setPosition(controlPosition.x, controlPosition.y);
					controlsPushed[i] = false;
				}
				else if(controls.get(i).overlaps(touchPosition)){
					controlsPushed[i] = true;
				}
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
			
			pause.setVisible(false);
			
			for(int i=0; i< nControls; i++){
				controls.get(i).resetPosition();
			}
			for(int i=0; i< nBallsIn; i++){
				ballsIn.get(i).resetPosition();
			}
			
			gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.CONTROLS){
			captureTouch();
		}
		else if(gameState == GameState.PAUSE){
		}
		else if(gameState == GameState.START){
			ballsIn.get(0).start();
			gameState = GameState.PLAYING;
		}
		else if(gameState == GameState.PLAYING){
			pause.setVisible(true);
			ballsIn.get(0).update(labyrinth.getPlatforms());
			//gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.FINISH){
			if(game.soundEnabled){
				music.stop();
			}
			game.setScreen(new MenuScene(game));
		}
		
	}
	
	private int gridValueX(float value){
		return (int) Math.floor(value/35); 
	}
	private int ungridValueX(float value){
		return gridValueX(value)*35;
	}
	
	private int gridValueY(float value){
		return (int) Math.floor(value/35);
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
		batch.draw(door, 420, 140);
		
		/*batch.draw(ball, 175, 700);
		batch.draw(ball, 245, 735);
		batch.draw(ball, 350, 700);
		batch.draw(ball, 420, 735);*/
				
		batch.end();
		
		labyrinth.draw();
	}
	private void resetScene(){
		//-- depurar
		System.out.println("Reset");
		
		gameState = GameState.INIT;
		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateScene();
		drawScene();
		stage.draw();
	}
	protected void handleBackPress() {
		//-- depurar
		System.out.println("back");
		
		game.setScreen(new MenuScene(game));
	}
	
	public void dispose(){
		tapSound.dispose();
		music.dispose();
		stage.dispose();
	}
	
}
