package com.plearning.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class PlearningGameScene extends BaseScene {
	AssetManager manager;
	String world;
	AtlasRegion background;
	AtlasRegion door;
	
	//Actors and Stage
	Stage stage;
	
	final Rectangle finish = new Rectangle(440, 140, 30, 30);
	
	OptionActor options;
	OptionActor restart;
	OptionActor play;
	OptionActor pause;
	OptionActor speed;
	/*Handle win and loose*/
	Skin skin;
	WinDialog winDialog;
	LooseDialog looseDialog;
	
	private boolean speeded;
	private boolean win;
	
	Vector<ControlActor> controls;
	Vector<BallActor> ballsIn;
	Vector<BallActor> ballsOut;
	Vector<BallActor> ballsEnd;
	
	int nControls = 7;
	int nBallsIn = 4;
	int nBallsOut = 3;
	
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
	
	//Timer countdown code
	long time;
	long timeOut = 90000;
	LabelStyle style;
	Label timer;
	
	//Timer for all balls
	long timeB;
	long timeBMax;
	
	
	
	static enum Color{
		RED, BLUE, GREEN, YELLOW
	}
	static enum GameState{
		INIT, CONTROLS, PLAYING, PAUSE, FINISH, OPTIONS, START, WIN, LOOSE
	}
	
	GameState gameState = GameState.INIT;
	
	
	
	public PlearningGameScene (PlearningGame plearning, String w) {
		super(plearning);
		game = plearning;
		world = w;
		/*Handle win and loose*/
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
		win = false;
		winDialog = new WinDialog(game, " You win this level! ", skin );
		looseDialog = new LooseDialog(game, "You loose", skin);
		
		/*Static initializations*/
		ControlActor.initialize();
		BallActor.initialize();
		BallActor.nBallsIn = nBallsIn;
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
		
		door = atlas.findRegion("door");
		
		options = new OptionActor(game, OptionActor.optionType.OPTIONS);
		restart = new OptionActor(game, OptionActor.optionType.RESTART);
		play = new OptionActor(game, OptionActor.optionType.PLAY);
		pause = new OptionActor(game, OptionActor.optionType.PAUSE);
		speed = new OptionActor(game, OptionActor.optionType.SPEED);
		
		speeded = false;
		
		//Timer initialization
		style = new LabelStyle();
		style.font = game.manager.get("UI/font.fnt", BitmapFont.class);
		style.fontColor = com.badlogic.gdx.graphics.Color.WHITE;
	
		timer = new Label(""+(timeOut*0.001), style);
		timer.setPosition(50, 750);
		
		//Here we choose the controls
		/* watch out here --> int nControls = 7; */
		controls = new Vector<ControlActor>();
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.YELLOW, 1));
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.add(new ControlActor(game, ControlActor.controlType.CREATOR, Color.BLUE, 3));
		controls.add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		controls.add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.BLUE, 5));
		controls.add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.BLUE, 6));
		
		//Timing in balls start
		timeBMax = nBallsIn * 2 * 1000;
		timeB = timeBMax;
		//Here we choose the balls in and out
		ballsIn = new Vector<BallActor>();
		ballsOut = new Vector<BallActor>();
		ballsEnd = new Vector<BallActor>();
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		ballsIn.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.addElement(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.addElement(new BallActor(game, Color.RED, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.addElement(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3, timeBMax -6000));
		
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.addElement(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 2, 0));
		
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
		time = TimeUtils.millis();
		//---
		stage.addActor(restart);
		stage.addActor(options);
		stage.addActor(play);
		stage.addActor(pause);
		stage.addActor(speed);
		stage.addActor(timer);
		
		for(int i=0; i<nControls;i++){
			stage.addActor(controls.get(i));
		}
		
		for(int i=0; i<nBallsIn;i++){
			stage.addActor(ballsIn.get(i));
		}
		
		for(int i=0; i<nBallsOut;i++){
			stage.addActor(ballsOut.get(i));
		}
		
		
		
		
		//Touch Listeners
		restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetScene();
            }
        });
		options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(game.soundEnabled){
        			stopMusic();
        		}
            	game.setScreen(new MenuScene(game));
            }
        });
		
		speed.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				if(speeded){
					BallActor.restartVelocity();
					speeded = false;
            	}
				else{
					BallActor.VELOCITY *= 2;
					speeded = true;
				}
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
		for(final ControlActor c : controls){
			c.addListener(new ClickListener(){
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            		    		
					c.playTap();
					controlsPushed[c.index] = true;
							
	            }
	        });
		}
		
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
			speed.setVisible(false);
			gameState = GameState.CONTROLS;
		}
		else if(gameState == GameState.CONTROLS){
			captureTouch();
			
			if(TimeUtils.millis() - time > 1000){
				//Esta parte se ejecutará cada segundo.
				time = TimeUtils.millis();
				timeOut -= 1000;
			}
			
			
			timer.setText(""+(int)(timeOut*0.001f));
			if(timeOut <= 0){				
				if(game.soundEnabled){
        			stopMusic();
        			changeMusic(manager.get("SOUNDS/w1-2Song.mp3", Music.class));
        		}
				gameState = GameState.START;
			}
		}
		else if(gameState == GameState.PAUSE){
			play.setVisible(true);
			speed.setVisible(false);
				
		}
		else if(gameState == GameState.START){
			for(BallActor ball: ballsIn){
				ball.start();
			}
			
			//ballsIn.get(0).start();
			gameState = GameState.PLAYING;
		}
		else if(gameState == GameState.PLAYING){
			pause.setVisible(true);
			play.setVisible(false);
			
			if(ballsEnd.size()==BallActor.nBallsIn){
				gameState = GameState.FINISH;
			}
			if(game.soundEnabled){
				playMusic();
				music.setVolume(game.soundVolume);
			}
			if(timeB > 0){
				if(TimeUtils.millis() - time > 1000){
					//Esta parte se ejecutará cada segundo.
					time = TimeUtils.millis();
					timeB -= 1000;
					
				}
			}
			if(timeB<=0){
				speed.setVisible(true);
			}
			BallActor ballAux = null;
			boolean ballOut = false;
			for(BallActor ball: ballsIn){
				ball.update(labyrinth.getPlatforms(), timeB);
				if(ball.collide(finish)){
					ballAux = ball;
					ballsEnd.add(ball);
					ballOut = true;
				}
			}
			if (ballOut){
				ballsIn.get(ballsIn.indexOf(ballAux)).setVisible(false);
				ballsIn.get(ballsIn.indexOf(ballAux)).translate(0, 0);
				ballsIn.removeElement(ballAux);
				
			}
			for(ControlActor control: controls){
				boolean creatorWorking = false;
				boolean destructorWorking = false;
				BallActor auxBall = null;
				
				for(BallActor ball: ballsIn){
					
					if(ball.collide(control.getBody())){
						if(control.type == ControlActor.controlType.CREATOR && !control.activated){
							creatorWorking = true;
							control.activated = true;
							//nBallsIn++;
							auxBall = new BallActor(game, ball.type, BallActor.BallInOut.IN, nBallsIn-1, new Vector2(control.getX()+35,control.getY()));
							
						}
						if((control.type == ControlActor.controlType.DESTRUCTOR) && (control.color == ball.type)){
							
							destructorWorking = true;
							auxBall = ball;
						}
				
						control.triggerAction(ball);
					}
				}
				
				if(creatorWorking){
					ballsIn.add(auxBall);
					stage.addActor(ballsIn.lastElement());
					creatorWorking = false;
				}
				if(destructorWorking){
					ballsIn.get(ballsIn.indexOf(auxBall)).setVisible(false);
					ballsIn.get(ballsIn.indexOf(auxBall)).translate(0,0);
					ballsIn.get(ballsIn.indexOf(auxBall)).remove();
					ballsIn.removeElement(ballsIn.get(ballsIn.indexOf(auxBall)));
					BallActor.nBallsIn--;
					destructorWorking = false;
				}
				
			}
			
		}
		else if(gameState == GameState.FINISH){
			
			if(game.soundEnabled){
				stopMusic();
			}
			
			if(ballsEnd.size() == ballsOut.size()){
				win = (ballsEnd.get(0).type == ballsOut.get(0).type);
				for(int i=1; i<ballsEnd.size(); i++){
					win = win && (ballsEnd.get(i).type == ballsOut.get(i).type);
				}
			}
			
			
			if(win){
				gameState = GameState.WIN;
				
			}
			else{
				gameState = GameState.LOOSE;
			}
		}
		else if(gameState == GameState.WIN){
			winDialog.show(stage);
		}
		else if(gameState == GameState.LOOSE){
			looseDialog.show(stage);
			if(looseDialog.retry){
				resetScene();
			}
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
		BallActor.restartVelocity();
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
		if(game.soundEnabled){
			stopMusic();
		}
		game.setScreen(new MenuScene(game));
	}
	
	public void dispose(){
		tapSound.dispose();
		music.dispose();
		stage.dispose();
		skin.dispose();
	}
	
}
