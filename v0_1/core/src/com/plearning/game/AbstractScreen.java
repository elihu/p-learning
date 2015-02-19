package com.plearning.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AbstractScreen implements Screen {
	protected Main main;
	protected SpriteBatch batch;
	protected OrthographicCamera camera;
	protected Viewport viewport;

	public AbstractScreen(Main main) {
		this.main = main;
		this.batch = main.batch;
		this.camera = main.camera;
		this.viewport = main.viewport;
	}
	
	@Override
	public void render(float delta) {} // Método que permite actualizar los valores del juego y dibujar el juego para que lo vea el usuario.
	
	@Override
	public void resize(int width, int height) { // Método que sirve para redimensionar la pantalla del juego.
		viewport.update(width, height);
	} 
	
	@Override
	public void show() {} // Método que se llama cuando se establece esta pantalla como actual
	
	@Override
	public void hide() {} // Método que se llama cuando se deja de usar esta pantalla.
	
	@Override
	public void pause() {} // Método que se llama cuando en un dispositivo móvil perdemos el foco
	
	@Override
	public void resume() {} // Método que se llama cuando el juego vuelve a coger el foco en un dispositivo movil
	
	@Override
	public void dispose() {} // Metodo que se encarga de destruir los valores del juego y liberar recursos.
}