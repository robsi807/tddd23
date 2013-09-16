package com.tddd23.blokz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Blokz extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}

	public void exitGame() {
		Gdx.app.exit();
	}
}