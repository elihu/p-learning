package com.plearning.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WinDialog extends Dialog{
	TextButton closeButton;
	PlearningGame game;
	public WinDialog(PlearningGame g, String title, Skin skin) {
		super(title, skin);
		game = g;
		closeButton = new TextButton(" OK ", skin);
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		getContentTable().add(new Label(" Try Another ", skin));
		button(closeButton);
		
		closeButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				hide();
				game.setScreen(new WorldScene(game));
			}
		});
	}

}
