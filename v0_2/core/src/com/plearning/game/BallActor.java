package com.plearning.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class BallActor extends Actor {

	AtlasRegion image;
	
	public static Vector2 startPosition = new Vector2(175, 630);
	public static final float VELOCITY = 30f; // Velocidad del jugador
	private boolean inFloor; // Indica si esta saltando o en el suelo respectivamente
	private int direction;
	private Rectangle body;
	//private boolean out;
	
	
	public static Vector<Vector2> ballsInPositions = new Vector<Vector2>();	
	public static Vector<Vector2> ballsOutPositions = new Vector<Vector2>();
	int index;
	TextureAtlas atlas;	
	PlearningGame game;
	
	static enum BallInOut{
		IN, OUT
	}
	static enum BallType{
		RED, YELLOW, GREEN, BLUE
	}
	
	BallType type;
	BallInOut inout;
	
	public BallActor(PlearningGame g, BallType o, BallInOut i, int p){
		game = g;
		atlas = game.atlas;
		type = o;
		index = p;
		inout = i;
		
		switch (type){
		case RED:
			image = atlas.findRegion("red");
			break;
		case YELLOW:
			image = atlas.findRegion("yellow");
			break;
		case GREEN:
			image = atlas.findRegion("green");
			break;
		case BLUE:
			image = atlas.findRegion("blue");
			break;
		}
		
		
		if(inout == BallInOut.IN){
			setPosition(ballsInPositions.get(p).x,ballsInPositions.get(p).y);
		}
		else if(inout == BallInOut.OUT){
			setPosition(ballsOutPositions.get(p).x,ballsOutPositions.get(p).y);
		}
		setWidth(35);
		setHeight(35);
		
		body = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		inFloor = false;
		direction = 1;
	}
	public static void initialize(){
		ballsInPositions.add(new Vector2(245, 700));
		ballsInPositions.add(new Vector2(210, 700));
		ballsInPositions.add(new Vector2(175, 700));
		ballsInPositions.add(new Vector2(175, 735));
		ballsInPositions.add(new Vector2(210, 735));
		ballsInPositions.add(new Vector2(245, 735));
		
		ballsOutPositions.add(new Vector2(420, 700));
		ballsOutPositions.add(new Vector2(385, 700));
		ballsOutPositions.add(new Vector2(350, 700));
		ballsOutPositions.add(new Vector2(350, 735));
		ballsOutPositions.add(new Vector2(385, 735));
		ballsOutPositions.add(new Vector2(420, 735));
	}
	public void start(){
		setPosition(startPosition.x, startPosition.y);
		body.x = startPosition.x;
		body.y = startPosition.y;
	}
	public void draw(Batch batch, float alpha){		
		
		batch.draw(image, this.getX(), this.getY());
	}
	public void update(Array<Rectangle> platforms) { // Metodo donde se actualiza los valores del jugador y se comprueban las colisiones
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
		
		
		
		if(!down){ // Si no colisiona para abajo
			inFloor = false;
			translate(0, -VELOCITY * Gdx.graphics.getDeltaTime());
			
		}
		if(inFloor){
			translate(direction * VELOCITY * Gdx.graphics.getDeltaTime(), 0);
		
			
		}
	}
	public boolean overlaps (Vector3 touchPosition){
		if(touchPosition.x >= this.getX() && touchPosition.x<=this.getX()+35)
			if(touchPosition.y >= this.getY() && touchPosition.y<=this.getY()+35)
				return true;
		
		return false;
		
	}
	
	public void translate(float x, float y) { // Mueve el personaje las x e y que le indiques
		
		body.x += x;
		body.y += y;
		
		setPosition(body.x, body.y);
	}
	
	public boolean collide(Rectangle x){
		return x.overlaps(body);
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
	
	public void resetPosition(){
		
		if(inout == BallInOut.IN){
			this.setX(ballsInPositions.get(index).x);
			this.setY(ballsInPositions.get(index).y);
		}
		else if(inout == BallInOut.OUT){
			this.setX(ballsOutPositions.get(index).x);
			this.setY(ballsOutPositions.get(index).y);
		}
		
	}
}
