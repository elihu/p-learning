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
import com.badlogic.gdx.utils.TimeUtils;

public class PlearningGameScene extends BaseScene {
	AssetManager manager;
	String world;
	AtlasRegion background;
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
	int timeOut = 30000;
	boolean pauseState;
	
	boolean [] controlsPushed = {false, false, false, false, false, false, false};
	Vector3 touchPosition;
	Vector2 controlPosition;
	
	
	SpriteBatch batch;
	OrthographicCamera camera;
	TextureAtlas atlas;
	
	Sound tapSound;
	
	PlearningGame game;
	Labyrinth labyrinth;
	int scaleFactorX;
	int scaleFactorY;
	
	private long time;
	
	static enum Color{
		RED, BLUE, GREEN, YELLOW
	}
	static enum GameState{
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS, START
	}
	
	GameState gameState = GameState.INIT;
	
	
	
	public PlearningGameScene (PlearningGame plearning, String w) {
		super(plearning);
		game = plearning;
		world = w;
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
		background = atlas.findRegion(world);
		
		//ball = atlas.findRegion("blue");
		door = atlas.findRegion("door");
		
		options = new OptionActor(game, OptionActor.optionType.OPTIONS);
		restart = new OptionActor(game, OptionActor.optionType.RESTART);
		play = new OptionActor(game, OptionActor.optionType.PLAY);
		pause = new OptionActor(game, OptionActor.optionType.PAUSE);
		
		//Here we choose the controls
		/* watch out here --> int nControls = 7; */
		controls = new Vector<ControlActor>();
		controls.add(new ControlActor(game, ControlActor.controlType.FLAG, Color.BLUE, 0));
		controls.add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.YELLOW, 1));
		controls.add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 2));
		controls.add(new ControlActor(game, ControlActor.controlType.CREATOR, Color.BLUE, 3));
		controls.add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		controls.add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.BLUE, 5));
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.BLUE, 6));
		
		//Here we choose the balls in and out
		ballsIn = new Vector<BallActor>();
		ballsOut = new Vector<BallActor>();
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		ballsIn.addElement(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 0));
		ballsIn.addElement(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1));
		ballsIn.addElement(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 2));
		ballsIn.addElement(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3));
		
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0));
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 1));
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 2));
		
		touchPosition = new Vector3(0,0,0);
		controlPosition = new Vector2(0,0);
		
		
		labyrinth = new Labyrinth (game, manager, world);
		//Sound and Music
		if(game.soundEnabled){
			tapSound = manager.get("SOUNDS/pop.ogg", Sound.class);
			stopMusic();
			changeMusic(manager.get("SOUNDS/w1-1Song.mp3", Music.class));
			
		} 
		//timer control
		time = TimeUtils.millis()+timeOut;
		//---
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
            	if(!pauseState){
            		gameState = GameState.START;
	            	//Sound and Music
	        		if(game.soundEnabled){
	        			stopMusic();
	        			changeMusic(manager.get("SOUNDS/w1-2Song.mp3", Music.class));
	        		}
	        	}
            	else{
            		pauseState = false;           		
            		gameState = GameState.PLAYING;
            	}
            }
        });
		pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(pauseState){
            		pauseState = false;
            		gameState = GameState.PLAYING;
            	}
            	else{
            		pauseState = true;
            		gameState = GameState.PAUSE;
            	}
            }
        });
		
		pauseState = false;
		/*Static initializations*/
		labyrinth.initialize();
		//-----------------------
		
	}
	private void captureTouch(){
		if(Gdx.input.justTouched()){
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPosition);

			
			//Trying to catch touched positions
			System.out.println("Touched at: "+ gridValueX(touchPosition.x)+", "+gridValueY(touchPosition.y));
			
			for(int i=0;i<nControls;i++){
				if(controlsPushed[i]){
					controls.get(i).playTap();
					controlPosition.x = ungridValueX(touchPosition.x);
					controlPosition.y = ungridValueY(touchPosition.y);
					if(labyrinth.validatePosition(new Vector2 (gridValueX(controlPosition.x), gridValueY(controlPosition.y))))
						controls.get(i).setPosition(controlPosition.x, controlPosition.y);
					controlsPushed[i] = false;
				}
				else if(controls.get(i).overlaps(touchPosition)){
					controls.get(i).playTap();
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
				playMusic();
				music.setVolume(game.soundVolume);
			}
			
			pause.setVisible(false);
			gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.CONTROLS){
			captureTouch();
			if(time <= TimeUtils.millis()){
				if(game.soundEnabled){
        			stopMusic();
        			changeMusic(manager.get("SOUNDS/w1-2Song.mp3", Music.class));
        		}
				gameState = GameState.START;
			}
		}
		else if(gameState == GameState.PAUSE){
			play.setVisible(true);
				
		}
		else if(gameState == GameState.START){
			/*for(BallActor ball: ballsIn){
				ball.start();
			}*/
			ballsIn.get(0).start();
			gameState = GameState.PLAYING;
		}
		else if(gameState == GameState.PLAYING){
			pause.setVisible(true);
			play.setVisible(false);
			if(game.soundEnabled){
				playMusic();
				music.setVolume(game.soundVolume);
			}
			/*for(BallActor ball: ballsIn){
				ball.update(labyrinth.getPlatforms());
			}*/
			ballsIn.get(0).update(labyrinth.getPlatforms());
			for(ControlActor control: controls){
				
				/*for(BallActor ball: ballsIn){
					
					if(ball.collide(control.getBody())){
						if(control.type == ControlActor.controlType.CREATOR){
							nBallsIn++;
							ballsIn.add(new BallActor(game, ballsIn.get(0).type, BallActor.BallInOut.IN, 4, new Vector2(control.getX()+35,control.getY())));
							stage.addActor(ballsIn.lastElement());
						}
						control.triggerAction(ball);
						
					}
					
				}*/
				
				for(BallActor ball: ballsIn){
					
					if(ball.collide(control.getBody())){
						if(control.type == ControlActor.controlType.CREATOR){
							nBallsIn++;
							ballsIn.add(new BallActor(game, ballsIn.get(0).type, BallActor.BallInOut.IN, 4, new Vector2(control.getX()+35,control.getY())));
							stage.addActor(ballsIn.lastElement());
						}
						control.triggerAction(ballsIn.get(0));
					}	
				}
			}
		}
		else if(gameState == GameState.FINISH){
			if(game.soundEnabled){
				stopMusic();
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
		
		
				
		batch.end();
		
		labyrinth.draw();
	}
	private void resetScene(){
		if(game.soundEnabled){
			stopMusic();
		}
		game.setScreen(new PlearningGameScene(game, world));
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
