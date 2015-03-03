package com.plearning.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Option extends Entity {
	public static final float WIDTH = 50; // Ancho
	public static final float HEIGHT = 50; // Alto
	private Texture image;
	private int index;
	private boolean pushed;
	
	public Option(int i) {
		super(WIDTH, HEIGHT, new Rectangle());	
		index = i;
		pushed = false;
		
		switch (index){
		case 0:
			image = new Texture("UIcontrols/Options.png");
			break;
		case 1:
			image = new Texture("UIcontrols/Restart.png");
			break;
		case 2:
			image = new Texture("UIcontrols/Pause.png");
			break;
		case 3:
			image = new Texture("UIcontrols/Play.png");
			break;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) { // Permite dibujar la opcion
		setRegion(new TextureRegion(image));
		super.draw(batch); // Llamamos al draw de la clase base.
	}
	public void update() {
	}
	public void dispose() { // Eliminamos los recursos reservados.
		image.dispose();
	}
	
}
