package com.plearning.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	protected Vector2 position; // Vector que tiene dos parametros, x e y, y sirve para marcar la posicion del personaje (Suponiendo que solo hay 2 dimensiones)
	protected TextureRegion region; // Es un objeto que apunta a una Textura, y permite ahorar memoria
	protected float width; // Ancho del personaje
	protected float height; // Alto del personaje
	protected Rectangle body; // Objeto que determinara el tamaño del personaje y permitira comprobar si se solapa con otro
	
	public Entity(float width, float height, Rectangle body) { // Inicializamos los valores necesarios
		this.position = new Vector2();
		this.width = width;
		this.height = height;
		this.body = body;
		body.width = width;
		body.height = height;
	}
	
	public void draw(SpriteBatch batch) { // Permite dibujar el personaje
		if (region == null)
			return;
		
		batch.draw(region, position.x, position.y, width, height);
	}
	
	public Vector2 getPosition() { // Devuelve la posicion del personaje
		return position;
	}
	
	public void setPosition(float x, float y) { // Sobreescribe la posicion del personaje
		position.x = x;
		position.y = y;
		body.setPosition(x, y);
	}
	
	public Vector2 getCenterPosition() { // Devuelve la posicion del personaje centrada
		return new Vector2(position.x + width * 0.5f, position.y + height * 0.5f);
	}
	
	public void translate(float x, float y) { // Mueve el personaje las x e y que le indiques
		position.x += x;
		position.y += y;
		body.x += x;
		body.y += y;
	}
	
	public Rectangle getBody() { // devuelve el rectangulo que forma el personaje
		return body;
	}
	
	public void setRegion(TextureRegion region) { // Sobrescribe la region del personaje apuntando a otra textura
		this.region = region;
	}
	
	public boolean collide(Entity e) { // Comprueba si se solapa con otro personaje
		return body.overlaps(e.body);
	}
	
	public static boolean collide(Entity e1, Entity e2) { // Metodo estatico que comprueba si se solapa con otro personaje
		return e1.body.overlaps(e2.body);
	}
}
