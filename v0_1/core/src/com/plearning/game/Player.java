package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {
	public static final float WIDTH = 30; // Ancho
	public static final float HEIGHT = 30; // Alto
	public static final float VELOCITY = 120f; // Velocidad del jugador
	
	private Texture frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10, frame11; // Texturas del jugador
	private Animation animation; // Permite animar los sprites en el juego y que parezca que el jugador anda.
	private float stateTime; // Permite devolver el frame indicado
	private boolean inFloor; // Indica si esta saltando o en el suelo respectivamente
	private int direction;
	
	public Player() {
		super(WIDTH, HEIGHT, new Rectangle());
		
		stateTime = 0;
		inFloor = false; // Inicializamos con la informacion basica
		setPosition(0, 0);
			
		frame1 = new Texture("Player/p1_walk01.png");
		frame2 = new Texture("Player/p1_walk02.png");
		frame3 = new Texture("Player/p1_walk03.png");
		frame4 = new Texture("Player/p1_walk04.png");
		frame5 = new Texture("Player/p1_walk05.png");
		frame6 = new Texture("Player/p1_walk06.png");
		frame7 = new Texture("Player/p1_walk07.png");
		frame8 = new Texture("Player/p1_walk08.png");
		frame9 = new Texture("Player/p1_walk09.png"); // Cargamos las texturas
		frame10 = new Texture("Player/p1_walk10.png");
		frame11 = new Texture("Player/p1_walk11.png");
		animation = new Animation(0.01f, new TextureRegion(frame1), new TextureRegion(frame2), new TextureRegion(frame3), new TextureRegion(frame2), 
										 new TextureRegion(frame3), new TextureRegion(frame4), new TextureRegion(frame5), new TextureRegion(frame6), 
										 new TextureRegion(frame7), new TextureRegion(frame8), new TextureRegion(frame9), new TextureRegion(frame10),
										 new TextureRegion(frame11)); // Las intoducimos en la animacion con una velocidad indicada en el primer parametro
		
		animation.setPlayMode(PlayMode.LOOP); // Ponemos que se ejecute en bucle.
	}
	
	@Override
	public void draw(SpriteBatch batch) { // Permite dibujar el jugador
		setRegion(animation.getKeyFrame(stateTime)); // Introducimos en la region la textura indicada para dibujar
		super.draw(batch); // Llamamos al draw de la clase base.
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
			stateTime += Gdx.graphics.getDeltaTime(); // Aumentamos statetime para cambiar de textura del personaje.
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
	
	public void dispose() { // Eliminamos los recursos reservados.
		frame1.dispose();
		frame2.dispose();
		frame3.dispose();
		frame4.dispose();
		frame5.dispose();
		frame6.dispose();
		frame7.dispose();
		frame8.dispose();
		frame9.dispose();
		frame10.dispose();
		frame11.dispose();
	}
}