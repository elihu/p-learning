package com.plearning.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {
	private static final int WIDTH = 315;
	private static final int HEIGHT = 483;
	
	public AbstractScreen gameScreen;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;
	public boolean win;
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		gameScreen = new GameScreen(this);
		win = false;
		setScreen(gameScreen);

	}
	@Override
	public void dispose() {
		gameScreen.dispose();
		batch.dispose();
	}

}
