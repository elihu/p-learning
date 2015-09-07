package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelScene extends BaseScene {
	private Stage stage;
	private Image screenBg;
	private Skin skin;
	private Label helpTip;
	private Table table;
	private TextButton backButton;
	private TextButton level1Button;
	private TextButton level2Button;
	private PlearningGame game;
	
	
	public LevelScene(PlearningGame plearning) {
		super(plearning);
		game = plearning;
		
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

		screenBg = new Image(game.manager.get("UI/background.png", Texture.class));

		helpTip=new Label("Select a level to play!",skin);
		helpTip.setColor(Color.WHITE);
		
		table=new Table();//.debug();
		level1Button=new TextButton(" LEVEL 1 ", skin);
		table.add(level1Button).padBottom(10).fill();
		table.row();
		level2Button=new TextButton(" LEVEL 2 ", skin);
		table.add(level2Button).padBottom(10).fill();
		table.row();
		table.row();
		
		
		backButton=new TextButton(" BACK ", skin);
		table.add(backButton).fill();
		table.setPosition(400, -200);
		
		
		
		stage.addActor(screenBg);
		
		stage.addActor(helpTip);
		stage.addActor(table);
		
		
		level1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setLevel(0);
                game.setScreen(new PlearningGameScene(game));
            }
        });
		
		level2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setLevel(1);
                game.setScreen(new PlearningGameScene(game));
            }
        });
		
		backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new WorldScene(game));
            }
        });
		
		
	}
	
	@Override
	public void show() {
		
		helpTip.setPosition(240-helpTip.getWidth()/2, 620);
		
		MoveToAction actionMove = Actions.action(MoveToAction.class);
		actionMove.setPosition(240-helpTip.getWidth()/2, 620);
		actionMove.setDuration(2);
		actionMove.setInterpolation(Interpolation.elasticIn);
		
		MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
		actionMove2.setPosition(240, 390);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);
		
		
		table.addAction(actionMove2);
	}


	
	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Show the loading screen
		stage.act();
		stage.draw();
		
		//Table.drawDebug(stage);
		super.render(delta);
	}
	
	
	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
	
}
