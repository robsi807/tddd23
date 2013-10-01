package com.tddd23.blokz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tddd23.blokz.audio.MusicCache;
import com.tddd23.blokz.audio.SoundCache;

public class Blokz extends Game {

	private GameScreen gameScreen;
	public void create() {
		SoundCache.load();
		MusicCache.load();
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void exitGame() {
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}