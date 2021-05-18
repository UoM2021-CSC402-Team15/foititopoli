package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Logic.GameInstance;
import com.mygdx.game.UI.Screens.MainMenuScreen;

public class Foititopoli extends Game {

	static public Skin gameSkin;

	private GameInstance gameInstance;

	public Boolean backgroundMusicPlaying = false;

	public void create() {
		gameSkin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
		this.setScreen(new MainMenuScreen(this));
	}

	public void dispose() {
	}

	public GameInstance getGameInstance() {
		return gameInstance;
	}

	public void setGameInstance(GameInstance gameInstance) {
		this.gameInstance = gameInstance;
	}
}
