package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen extends AbstractScreen {
	private Map map;
	private Texture texture;
	
	public GameScreen(Main main){
		super(main);
		map = new Map(main, batch);
		texture = new Texture("");
	}
	@Override
	public void render(float delta) { // Método que permite actualizar los valores del juego y dibujar el juego para que lo vea el usuario.
		Gdx.gl.glClearColor(0, 0, 0, 1);    //Gdx es una clase con la que podemos acceder a variables que hacen referencia a todos los subsitemas, como son graficos, audio, ficheros, entrada y aplicaciones
		                                    // gl es una variable de tipo GL, nos permite acceder a metodos de GL10, GL11 y GL20
		                                   //En este caso glClearColor es un bucle (game loop) que establecera el fondo de la pantalla negro (0,0,0) con transparencia 1
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Despues de la funcion anterior es necesario ejecutar esta, para que se lleve a cabo
		
		if(main.win) // Si hemos ganado
			updateCameraWin();
		else { // Si estamos en el curso normal
			updateCamera();
			map.draw(camera); // Dibujamos el mapa
		}
	}
	
	@Override
	public void dispose() { // Metodo que se encarga de destruir los valores del juego y liberar recursos.
		map.dispose();
	}
	
	private void updateCamera() { // Método que permite actualizar los valores de la camara para poder ver a nuestro protagonista.
		camera.position.x = map.getPlayer().getCenterPosition().x; // Centramos la posicion de la camara.
		camera.position.y = map.getPlayer().getCenterPosition().y;

		TiledMapTileLayer layer = (TiledMapTileLayer)map.getMap().getLayers().get(0); // Cogemos una capa del mapa

		float cameraMinX = viewport.getWorldWidth() * 0.5f; // Minimo punto en el eje x
		float cameraMinY = viewport.getWorldHeight() * 0.5f; // Minimo punto en el eje y
		float cameraMaxX = layer.getWidth() * layer.getTileWidth() - cameraMinX; // Maximo punto en el eje x
		float cameraMaxY = layer.getHeight() * layer.getTileHeight() - cameraMinY; // Maximo punto en el eje y

		camera.position.x = MathUtils.clamp(camera.position.x, cameraMinX, cameraMaxX); // Escogemos camera.posicion.x si es mayor de cameraMinX o menor que cameraMaxX, en cualquier otro caso que 
		camera.position.y= MathUtils.clamp(camera.position.y, cameraMinY, cameraMaxY); // no se cumpla la condicion se coge cameraMinX o cameraMaxX, en ese orden. El segundo es igual pero con el eje Y
                                                                                       // Asi conseguimos que la camara no se salga del escenario.
		camera.update(); // Actualizamos la camara
	}
	
	private void updateCameraWin() {
		batch.begin();
		batch.draw(texture, camera.position.x - texture.getWidth() * 0.5f, camera.position.y - texture.getHeight() * 0.5f, 
				texture.getWidth(), texture.getHeight()); // Dibujamos la textura
		batch.end();
	}
}
