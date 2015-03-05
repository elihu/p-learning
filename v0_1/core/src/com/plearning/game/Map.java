package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Map {
	private Main main;
	private SpriteBatch batch; // "Grupo de Sprites (imagenes)" nos permite dibujar rectagulos como referencias a texturas, es necesario para mostrar todo por pantalla.
	private TiledMap map; // El mapa importado de fichero tmx que se genera con tiled
	private TmxMapLoader loader; // Permite cargar el fichero tmx
	private OrthogonalTiledMapRenderer renderer; // El mapa contenido en un escenario ortogonal (Se le da las propiedades necesarias) para que lo pueda ver, usar y modificar el usuario
	
	private Ball ball; // El jugador del juego
	private Ball ball2;
	private Sound dieSound; // Sonido cuando se muere 
	private Array<Rectangle> platforms; // Un array que contiene todas las plataformas del juego
	private Array<Rectangle> slots; // Un array que contiene todas las posiciones válidas para los controles
	private Array<Rectangle> controls; // Un array que contiene todos los controles
	private Array<Option> options; // Un array que contiene todas las opciones
	private Rectangle start, goal, out; // La casilla de salida y la casilla de meta. Además una casilla para delimitar si el objeto ya ha salido.

	
	public Map(Main main, SpriteBatch batch) {
		this.main = main;
		loader = new TmxMapLoader();
		map = loader.load("tiledMap.tmx"); // Inicializamos las variables
		this.batch = batch;
		renderer = new OrthogonalTiledMapRenderer(map, batch);
		dieSound = Gdx.audio.newSound(Gdx.files.internal("dieSound.wav"));
		
		processMapMetadata(); // Este metodo inicializa los elementos del mapa.
	}

	public void draw(OrthographicCamera camera) { // Metodo para dibujar y actualizar los valores del mapa
		renderer.setView(camera);
		renderer.render();
		
		update();
		
		batch.begin();
		ball.draw(batch);
		ball2.draw(batch);
		for(Option o : options){
			o.draw(batch);
		}
		batch.end();
	}
	
	private void update() { // Actualiza los valores de los personajes del juego
		ball.update(platforms, out); // Acualizamos los valores de player y comprobamos las colisiones con las plataformas
		if(ball.out())
			ball2.update(platforms, out);
		for(Option o : options){
			o.update();
		}
		if(ball.getBody().overlaps(goal)) { // Si llega a la salida.
			ball.stop(goal);
		}
		if(ball2.getBody().overlaps(goal)) { // Si llega a la salida.
			main.win = true;
			reset();
		}
	}
	
	private void reset() { // Este metodo hace que el jugador vuelva a su posicion inicial
		ball.setPosition(start.x, start.y);
		//dieSound.play();
	}
	
	public void dispose() { // Destruye los elementos innecesarios
		map.dispose();
		renderer.dispose();
		ball.dispose();
		ball2.dispose();
		for(Option o : options){
			o.dispose();
		}
	}
	
	public TiledMap getMap() { // Devuelve el mapa
		return map;
	}

	public Ball getPlayer() { // Devuelve el jugador del mapa
		return ball;
	}

	private void processMapMetadata() { // Este metodo inicializa los elementos del mapa.
		platforms = new Array<Rectangle>(); // Inicializamos los arrays
		options = new Array<Option>(); 

		MapObjects objects = map.getLayers().get("Objects").getObjects(); // Cogemos del mapa los objetos llamados "Objects"

		for (MapObject object : objects) {
			String name = object.getName(); // Cogemos su nombre
			RectangleMapObject rectangleObject = (RectangleMapObject) object; // Lo transformamos en rectangulo
			Rectangle rectangle = rectangleObject.getRectangle();
			if(name.equals("Out")) // Si se llama "Out"
				out = rectangle;
			if(name.equals("PlayerStart")) // Si se llama "PlayerStart"
				start = rectangle;
			if(name.equals("PlayerGoal")) // Si se llama "PlayerGoal"
				goal = rectangle;
			if(name.equals("Options")){ // Si se llama "Options"
				options.insert(0, new Option(0));
				options.get(0).setPosition(rectangle.x, rectangle.y);
			}
			if(name.equals("Restart")){ // Si se llama "Restart"
				options.insert(1, new Option(1));
				options.get(1).setPosition(rectangle.x, rectangle.y);
			}
			if(name.equals("Pause")){ // Si se llama "Pause"
				options.insert(2, new Option(2));
				options.get(2).setPosition(rectangle.x, rectangle.y);
			}
			if(name.equals("Play")){ // Si se llama "Play"
				options.insert(3, new Option(3));
				options.get(3).setPosition(rectangle.x, rectangle.y);
			}
			
		}
		
		
		objects = map.getLayers().get("Physics").getObjects(); // Cogemos del mapa los objetos llamados "Physics"
		
		for (MapObject object : objects) {
			RectangleMapObject rectangleObject = (RectangleMapObject) object; // Lo transformamos en rectangulo
			Rectangle rectangle = rectangleObject.getRectangle();
			platforms.add(rectangle); // Las añadimos al array
		} 
		
		objects = map.getLayers().get("Limits").getObjects(); // Cogemos del mapa los objetos llamados "Limits"
		
		for (MapObject object : objects) {
			RectangleMapObject rectangleObject = (RectangleMapObject) object; // Lo transformamos en rectangulo
			Rectangle rectangle = rectangleObject.getRectangle();
			platforms.add(rectangle); // Las añadimos al array
		} 
		
		
		ball = new Ball(Ball.Color.GREEN); // Creamos al jugador
		ball2 = new Ball(Ball.Color.BLUE);
		ball.setPosition(start.x, start.y); // Lo ponemos en la casilla salida.
		ball2.setPosition(start.x, start.y);
		
	}
}