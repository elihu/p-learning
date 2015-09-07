package com.plearning.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LooseDialog extends Dialog{
	TextButton retryButton;
	TextButton exitButton;
	PlearningGame game;
	public boolean retry = false;
	public LooseDialog(PlearningGame g, String title, Skin skin) {
		super(title, skin);
		game = g;
		retryButton = new TextButton(" RETRY ", skin);
		exitButton = new TextButton(" EXIT ", skin);
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		getContentTable().add(new Label(" Try Again? ", skin));
		button(retryButton);
		button(exitButton);
		
		retryButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				hide();
				retry = true;
			}
		});
		
		exitButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScene(game));
				
			}
		});
	}

}
