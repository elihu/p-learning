package com.plearning.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Labyrinth {
	//Free Blocks Determinist
	private final Vector2 free1 = new Vector2(8, 5);
	private final Vector2 free2 = new Vector2(6, 7);
	private final Vector2 free3 = new Vector2(12, 9);
	private final Vector2 free4 = new Vector2(5, 11);
	private final Vector2 free5 = new Vector2(5, 13);
	private final Vector2 free6 = new Vector2(10, 15);
	private final Vector2 free7 = new Vector2(7, 17);
	
	private final int iIni = 5;
	private final int iFin = 12;
	private final int jIni = 5;
	private final int jFin = 17;
	//private final Random rand = new Random();
	PlearningGame game;
	SpriteBatch batch;
	String world;
	
	float scaleFactor = 35;
	
	AtlasRegion floor; //temporal hasta convertirlo en clase y comprobar overlaps
	public Array<Rectangle> platforms = new Array<Rectangle>(); // Un array que contiene todas las plataformas del juego
	
	public Labyrinth(PlearningGame plearning, AssetManager manager, String w){
		game = plearning;
		batch = game.batch;
		world = w;
		TextureAtlas atlas = manager.get("PLearning.pack", TextureAtlas.class);
		if(world.equalsIgnoreCase("background-w1")){
			floor = atlas.findRegion("floor-w1");
		}
		else if(world.equalsIgnoreCase("background-w2")){
			floor = atlas.findRegion("floor-w2");
		}
	}
	
	public void draw(){
		for(int i = iIni; i<= iFin ; i++){
			for(int j = jIni; j<= jFin; j+=2){
				
				if(i==free1.x && j== free1.y){}
					else if(i==free2.x && j== free2.y){}
						else if(i==free3.x && j== free3.y){}
							else if(i==free4.x && j== free4.y){}
								else if(i==free5.x && j== free5.y){}
									else if(i==free6.x && j== free6.y){}
										else if(i==free7.x && j== free7.y){}
										else{
											batch.begin();
											batch.draw(floor, i*scaleFactor, j*scaleFactor);
											batch.end();											
										}
			}
		}
	}

	public void initialize(){
		for(int i = iIni-1; i<= iFin+1 ; i++){
			for(int j = jIni-2; j<= jFin; j+=2){
				
				if(i==free1.x && j== free1.y){}
					else if(i==free2.x && j== free2.y){}
						else if(i==free3.x && j== free3.y){}
							else if(i==free4.x && j== free4.y){}
								else if(i==free5.x && j== free5.y){}
									else if(i==free6.x && j== free6.y){}
										else if(i==free7.x && j== free7.y){}
											else{
												platforms.add(new Rectangle(i*scaleFactor, j*scaleFactor, 35, 35));	
												System.out.println("i= "+i+ " j ="+j);
												}
			}
		}
		
		for(int j = jIni-1; j<= jFin+1; j+=2){
			platforms.add(new Rectangle((iIni-1)*scaleFactor, j*scaleFactor, 35, 35));	
			System.out.println("i= "+ (iIni-1) + " j ="+j);
			
			platforms.add(new Rectangle((iFin+1)*scaleFactor, j*scaleFactor, 35, 35));	
			System.out.println("i= "+ (iFin+1) + " j ="+j);
		}
		
	}
	
	public Array<Rectangle> getPlatforms(){return platforms;}
	public boolean validatePosition(Vector2 controlPosition) {
		boolean value;
		
		//Overlapping doors control
		if(controlPosition.dst2(5, 18)==0 || controlPosition.dst2(12, 4)==0)
			return false;
		if(controlPosition.equals(free1) || controlPosition.equals(free2) ||controlPosition.equals(free3) ||controlPosition.equals(free4) ||controlPosition.equals(free5) ||controlPosition.equals(free6) ||controlPosition.equals(free7))
			return true;
		
		//Controlling setting the control back to the controls box
		if(controlPosition.x >= 1 && controlPosition.x<=3 && controlPosition.y>=4 && controlPosition.y<=18)
			return true;
		value = (controlPosition.x>=iIni && controlPosition.x <= iFin);
		value = value && (controlPosition.y>=jIni-1 && controlPosition.y <= jFin+1);
		value = value && (controlPosition.y%2 == 0);
		return value;
	}

}
