package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Ball extends Entity {
	public static final float WIDTH = 20; // Ancho
	public static final float HEIGHT = 20; // Alto
	public static final float VELOCITY = 50f; // Velocidad del jugador
	public static enum Color{RED, BLUE, YELLOW, GREEN};
	
	private boolean out;
	private Color type;
	private Texture red, blue, yellow, green; // Texturas de las pelota
	
	private boolean inFloor; // Indica si esta saltando o en el suelo respectivamente
	private int direction;
	
	public Ball(Color type) {
		super(WIDTH, HEIGHT, new Rectangle());
		this.type = type;
		inFloor = false; // Inicializamos con la informacion basica
		out = false;
		setPosition(0, 0);
		red = new Texture("Ball/red.png");
		blue = new Texture("Ball/blue.png");
		yellow = new Texture("Ball/yellow.png");
		green = new Texture("Ball/green.png");
		
	}
	
	@Override
	public void draw(SpriteBatch batch) { // Permite dibujar el jugador
		switch (type){
		case RED:
			setRegion(new TextureRegion(red));
			break;
		case BLUE:
			setRegion(new TextureRegion(blue));
			break;
		case YELLOW:
			setRegion(new TextureRegion(yellow));
			break;
		case GREEN:
			setRegion(new TextureRegion(green));
			break;
		}
		
		super.draw(batch); // Llamamos al draw de la clase base.
	}
	
	public void update(Array<Rectangle> platforms, Rectangle out) { // Metodo donde se actualiza los valores del jugador y se comprueban las colisiones
		boolean down;
		down = false; // Permite comprobar si hay una colision o no
		for(Rectangle rectangle : platforms) {
			if(downCollision(rectangle)) { // Si colisiona por abajo
				inFloor = true;
				down = true;
			}
			
			if(rightCollision(rectangle)){ // Si colisiona por la derecha
				direction = -1;
			}
			
			if(leftCollision(rectangle)){ // Si colisiona por la izquierda
				direction = 1;
			}
			
		}
		
		if(outCollision(out))
			this.out=true;
			
		
		if(!down){ // Si no colisiona para abajo
			inFloor = false;
			translate(0, -VELOCITY * Gdx.graphics.getDeltaTime());
			this.rotate(direction);
		}
		if(inFloor){
			translate(direction * VELOCITY * Gdx.graphics.getDeltaTime(), 0);
			this.rotate(direction);
			
		}
	}
	
	
	public boolean rightCollision(Rectangle rectangle) { // Comprueba si se colisiona por la derecha.
		Rectangle auxRectangle = new Rectangle(body.x, body.y, body.width, body.height);
		auxRectangle.x += VELOCITY * Gdx.graphics.getDeltaTime();
		return auxRectangle.overlaps(rectangle);
	}
	
	public boolean leftCollision(Rectangle rectangle) { // Comprueba si se colisiona por la izquierda.
		Rectangle auxRectangle = new Rectangle(body.x, body.y, body.width, body.height);
		auxRectangle.x -= VELOCITY * Gdx.graphics.getDeltaTime();
		return auxRectangle.overlaps(rectangle);
	}
	
	public boolean upCollision(Rectangle rectangle) { // Comprueba si se colisiona por arriba.
		Rectangle auxRectangle = new Rectangle(body.x, body.y, body.width, body.height);
		auxRectangle.y += VELOCITY * Gdx.graphics.getDeltaTime();
		return auxRectangle.overlaps(rectangle);
	}
	
	public boolean downCollision(Rectangle rectangle) { // Comprueba si se colisiona por abajo.
		Rectangle auxRectangle = new Rectangle(body.x, body.y, body.width, body.height);
		auxRectangle.y -= VELOCITY * Gdx.graphics.getDeltaTime();
		return auxRectangle.overlaps(rectangle);
	}
	public boolean outCollision(Rectangle rectangle) {
		Rectangle auxRectangle = new Rectangle(body.x, body.y, body.width, body.height);
		auxRectangle.y -= VELOCITY * Gdx.graphics.getDeltaTime();
		return auxRectangle.overlaps(rectangle);
	}
	
	public void dispose() { // Eliminamos los recursos reservados.
		red.dispose();
		blue.dispose();
		yellow.dispose();
		green.dispose();
		
	}
	public boolean out(){
		return out;
	}

	public void stop(Rectangle goal) {
		setPosition(goal.x, goal.y);
		
	}
	
}