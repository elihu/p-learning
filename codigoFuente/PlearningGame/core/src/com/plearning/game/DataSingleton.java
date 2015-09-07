package com.plearning.game;

import com.badlogic.gdx.utils.Array;
import com.plearning.game.PlearningGameScene.Color;

public class DataSingleton {
	PlearningGame game;
	//Managing controls
	int[] nControls;
	//BooleanArray controlsPushed;
	Array<Array<ControlActor>> controls;
	//Vector<ControlActor> controls;
	
	//Managin balls
		//Timer for all balls
	long timeB;
	long timeBMax;
	
	Array<Array<BallActor>> ballsIn;
	Array<Array<BallActor>> ballsOut;
	int level;
	
	public DataSingleton(PlearningGame plearning){
		game = plearning;
		
		//static inicializations
		ControlActor.initialize();
		BallActor.initialize();
		//Level 0 ----------------------
		level = 0;
		//Here we choose the controls
		/* watch out here --> int nControls = 7; int level = 1*/
		nControls = new int[4];
		
		controls = new Array<Array<ControlActor>>();
		controls.size = 4;
		for(int i=0; i<4;i++){
			controls.set(i, new Array<ControlActor>()); 
		}
		nControls[level] = 7;
		//controlsPushed = new BooleanArray(nControls);
		
			
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.YELLOW, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.BLUE, 5));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.BLUE, 6));
		
		//Here we choose the balls in and out
		ballsIn = new Array<Array<BallActor>>();
		ballsOut = new Array<Array<BallActor>>();
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		timeBMax = 4 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.size = 4;
		for(int i=0; i<4;i++){
			ballsIn.set(i, new Array<BallActor>()); 
		}
		
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3, timeBMax -6000));
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 2, 0));
		
		//Level 1 ----------------------
		level = 1;
		//Here we choose the controls
		/* watch out here --> int nControls = 7; int level = 0*/
		nControls[level] = 5;
		//controlsPushed = new BooleanArray(nControls);
		//controls = new Array<Array<ControlActor>>();
		
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.YELLOW, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		
		
		//Here we choose the balls in and out
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 5;
			 * 		int nBallsOut = 3;
			 * 
			*/
		timeBMax = 5 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.add(new Array<BallActor>());
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3, timeBMax -6000));
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 4, timeBMax -8000));
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.OUT, 2, 0));
	}
	public Array<ControlActor> getControls(int level){
		return controls.get(level);
	}
	public Array<BallActor> getBallsIn(int level){
		return ballsIn.get(level);
	}
	public Array<BallActor> getBallsOut(int level){
		return ballsOut.get(level);
	}
	public int countBallsIn(int level){
		return ballsIn.get(level).size;
	}
	public int countBallsOut(int level) {
		return ballsOut.get(level).size;
	}
	public int countControls(int level) {
		return nControls[level];
	}
}
