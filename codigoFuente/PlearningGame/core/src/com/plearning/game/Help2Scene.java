package com.plearning.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Help2Scene extends BaseScene {
	private Stage stage;
	private Image screenBg;
	private Skin skin;
	
	private Table table;
	private TextButton backButton;
	private TextButton nextButton;


	private PlearningGame game;
	
	
	public Help2Scene(PlearningGame plearning) {
		super(plearning);
		game = plearning;
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

		screenBg = new Image(game.manager.get("HELP/help-2.png", Texture.class));

		
		
		table=new Table();//.debug();
		
		
		
		
		backButton=new TextButton(" BACK ", skin);
		table.add(backButton).fill();
		nextButton=new TextButton(" NEXT ", skin);
		table.add(nextButton).fill();
		table.setPosition(400, -200);
		
		
		
		stage.addActor(screenBg);
		stage.addActor(table);
		
		
		
		
		backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new Help1Scene(game));
            }
        });
		nextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new Help3Scene(game));
            }
        });
		
		
	}
	
	@Override
	public void show() {
		
	
		
		MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
		actionMove2.setPosition(240, 120);
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
