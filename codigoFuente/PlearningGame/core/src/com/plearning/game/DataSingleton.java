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
		
		//variables
		nControls = new int[6];
		controls = new Array<Array<ControlActor>>();
		controls.size = 6;
		for(int i=0; i<6;i++){
			controls.set(i, new Array<ControlActor>()); 
		}
		//Level 0 ----------------------
		level = 0;
		//Here we choose the controls
		/* watch out here --> int nControls = 2; int level = 0*/
		nControls[level] = 2;
		//controlsPushed = new BooleanArray(nControls);
		
			
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.RED, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 1));
		/*controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.BLUE, 5));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.BLUE, 6));
		*/
		//Here we choose the balls in and out
		ballsIn = new Array<Array<BallActor>>();
		ballsOut = new Array<Array<BallActor>>();
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		
		ballsIn.size = 6;
		for(int i=0; i<6;i++){
			ballsIn.set(i, new Array<BallActor>()); 
		}
		
		timeBMax = 2 * 2 * 1000;
		timeB = timeBMax;
		
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		/*ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3, timeBMax -6000));
		*/
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		
		
		//Level 1 ----------------------
		level = 1;
		//Here we choose the controls
		/* watch out here --> int nControls = 7; int level = 1*/
		nControls[level] = 6;
		
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.GREEN, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.RED, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.BLUE, 4));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 5));
		
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
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN, 3, timeBMax -6000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 4, timeBMax -8000));
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.OUT, 2, 0));
		
		//Level 2 ----------------------
		level = 2;
		//Here we choose the controls
		/* watch out here --> int nControls = 6; int level = 2*/
		nControls[level] = 2;
		
		
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 1));
				
		//Here we choose the balls in and out
				
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 2;
			 * 
			*/
		timeBMax = 4 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.add(new Array<BallActor>());
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 3, timeBMax -6000));
		
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		
		//Level 3 ----------------------
		level = 3;
		//Here we choose the controls
		/* watch out here --> int nControls = 6; int level = 2*/
		nControls[level] = 6;
		
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.RED, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 4));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.BLUE, 5));
		
		
		//Here we choose the balls in and out
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		timeBMax = 4 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.add(new Array<BallActor>());
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN, 3, timeBMax -6000));
		
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.OUT, 2, 0));
		
		//Level 4 ----------------------
		level = 4;
		//Here we choose the controls
		/* watch out here --> int nControls = 6; int level = 4*/
		nControls[level] = 5;
						
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFRIGHT, Color.GREEN, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.GREEN, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 4));
		
		//Here we choose the balls in and out
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 3;
			 * 		int nBallsOut = 2;
			 * 
			*/
		timeBMax = 3 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.add(new Array<BallActor>());
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN,2, timeBMax-4000));
		
		
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		
		
		//Level 5 ----------------------
		level = 5;
		//Here we choose the controls
		/* watch out here --> int nControls = 6; int level = 5*/
		nControls[level] = 7;
						
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.YELLOW, 0));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.YELLOW, 1));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DESTRUCTOR, Color.RED, 2));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.IFLEFT, Color.BLUE, 3));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DIRCHANGER, Color.YELLOW, 4));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.DIRCHANGER, Color.BLUE, 5));
		controls.get(level).add(new ControlActor(game, ControlActor.controlType.CONVERTER, Color.GREEN, 6));
		
		//Here we choose the balls in and out
		
		
			/*	Care with this ->
			 * 		int nBallsIn = 4;
			 * 		int nBallsOut = 3;
			 * 
			*/
		timeBMax = 4 * 2 * 1000;
		timeB = timeBMax;
		ballsIn.add(new Array<BallActor>());
		ballsIn.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.IN, 0, timeBMax));
		ballsIn.get(level).add(new BallActor(game, Color.RED, BallActor.BallInOut.IN, 1, timeBMax-2000));
		ballsIn.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.IN,2, timeBMax-4000));
		ballsIn.get(level).add(new BallActor(game, Color.BLUE, BallActor.BallInOut.IN, 3, timeBMax -6000));
		
		
		ballsOut.add(new Array<BallActor>());
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 0, 0));
		ballsOut.get(level).add(new BallActor(game, Color.GREEN, BallActor.BallInOut.OUT, 1, 0));
		ballsOut.get(level).add(new BallActor(game, Color.YELLOW, BallActor.BallInOut.OUT, 2, 0));
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
