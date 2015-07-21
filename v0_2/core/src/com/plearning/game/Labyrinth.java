/*Labyrinth customizable, still not floor objects*/
package com.plearning.game;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

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
	private final Random rand = new Random();
	PlearningGame game;
	SpriteBatch batch;

	
	float scaleFactor = 35;
	
	AtlasRegion floor; //temporal hasta convertirlo en clase y comprobar overlaps

	
	public Labyrinth(PlearningGame plearning, AssetManager manager){
		game = plearning;
		batch = game.batch;
		TextureAtlas atlas = manager.get("PLearning.pack", TextureAtlas.class);
		floor = atlas.findRegion("floor-w1");
	}
	
	public void draw(){
		boolean sw=true;
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

}
