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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlearningGameScene extends BaseScene {
	AssetManager manager;
	
	AtlasRegion background;
	AtlasRegion ball;
	
	//AtlasRegion restart;
	AtlasRegion door;
	
	//Actors and Stage
	Stage stage;
	OptionActor options;
	OptionActor restart;
	OptionActor play;
	OptionActor pause;
	
	ControlActor control1;
	ControlActor control2;
	ControlActor control3;
	ControlActor control4;
	ControlActor control5;
	ControlActor control6;
	ControlActor control7;
	
	boolean controlPushed;
	Vector3 touchPosition;
	Vector2 controlPosition;
	
	//private final Vector2 control1IniPosition;
	//private Vector2 control1Position;
	
	
	//private boolean control1Pushed;
	
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
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS
	}
	
	GameState gameState = GameState.INIT;
	
	
	
	public PlearningGameScene (PlearningGame plearning) {
		super(plearning);
		game = plearning;
		
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		
		batch = game.batch;
		camera = game.camera;
		manager = game.manager;
		atlas = game.atlas;
		
		scaleFactorX = game.scaleFactorX;
		scaleFactorY = game.scaleFactorY;
		background = atlas.findRegion("background-w1");
		
		ball = atlas.findRegion("blue");
		door = atlas.findRegion("door");
		
		options = new OptionActor(game, OptionActor.optionType.OPTIONS);
		restart = new OptionActor(game, OptionActor.optionType.RESTART);
		play = new OptionActor(game, OptionActor.optionType.PLAY);
		pause = new OptionActor(game, OptionActor.optionType.PAUSE);
		
		control1 = new ControlActor(game, ControlActor.controlType.FLAG, 1);
		control2 = new ControlActor(game, ControlActor.controlType.IFLEFT, 2);
		control3 = new ControlActor(game, ControlActor.controlType.FLAG, 3);
		control4 = new ControlActor(game, ControlActor.controlType.FLAG, 4);
		control5 = new ControlActor(game, ControlActor.controlType.FLAG, 5);
		control6 = new ControlActor(game, ControlActor.controlType.CREATOR, 6);
		control7 = new ControlActor(game, ControlActor.controlType.FLAG, 7);
		
		touchPosition = new Vector3(0,0,0);
		controlPosition = new Vector2(0,0);
		controlPushed = false;		
		//control1Pushed = false;
		
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
		stage.addActor(control1);
		stage.addActor(control2);
		stage.addActor(control3);
		stage.addActor(control4);
		stage.addActor(control5);
		stage.addActor(control6);
		stage.addActor(control7);
		
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
            	gameState = GameState.PLAYING;
            }
        });
		pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameState = GameState.PAUSE;
            }
        });
		
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
			
			
			if(controlPushed){
				controlPosition.x = ungridValueX(touchPosition.x);
				controlPosition.y = ungridValueY(touchPosition.y);
				control1.setPosition(controlPosition.x, controlPosition.y);
				controlPushed = false;
			}
			
			else if(control1.overlaps(touchPosition)){
				controlPushed = true;
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
			control1.setX(control1.control1Position.x);
			control1.setY(control1.control1Position.y);
			//control1Position.y = control1IniPosition.y;
			gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.CONTROLS){
			captureTouch();
		}
		else if(gameState == GameState.PAUSE){
		}
		else if(gameState == GameState.PLAYING){
			pause.setVisible(true);
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
		
		batch.draw(ball, 175, 700);
		batch.draw(ball, 245, 735);
		batch.draw(ball, 350, 700);
		batch.draw(ball, 420, 735);
				
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
	
	/*private boolean gridOverlaps(float i1, float i2, float j1, float j2){
		int x1 = gridValueX(i1);
		int x2 = gridValueX(i2);
		int y1 = gridValueY(j1);
		int y2 = gridValueY(j2); 
		
		return (x1==x2 || x1==(x2+1)) && (y1==y2 || y1==(y2+1));
	}*/
}
