package com.tddd23.blokz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tddd23.blokz.audio.MusicCache;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.gfx.TextureHandler;
import com.tddd23.blokz.menu.MainMenu;
import com.tddd23.blokz.menu.Menu;

public class Blokz extends Game {

	private GameScreen gameScreen;
	private Menu menuScreen;
	public void create() {
		TextureHandler.init();
		SoundCache.load();
		MusicCache.load();
		menuScreen = new MainMenu(this);
		setScreen(menuScreen);
	}
	
	//Här skall vi skicka med vilken level som skall spelas som argument till gamescreen
	public void startGame(){
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