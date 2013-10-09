package com.tddd23.blokz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tddd23.blokz.audio.MusicCache;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.font.FontHandler;
import com.tddd23.blokz.gfx.TextureHandler;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.menu.MainMenu;
import com.tddd23.blokz.menu.MapSelectionMenu;
import com.tddd23.blokz.menu.Menu;
import com.tddd23.blokz.world.WorldManager;

public class Blokz extends Game {

	private GameScreen gameScreen;
	private Menu menuScreen;
	private WorldManager worldmanager;

	public void create() {
		TextureHandler.init();
		FontHandler.init();
		SoundCache.load();
		MusicCache.load();
		worldmanager = new WorldManager();
		goToMainMenu();
	}

	public void goToMapMenu() {
		setScreen(new MapSelectionMenu(this, worldmanager));
	}
	public void goToMainMenu() {
		setScreen(new MainMenu(this, worldmanager));
	}


	// Här skall vi skicka med vilken level som skall spelas som argument till
	// gamescreen
	public void startGame(GameMap map) {
		gameScreen = new GameScreen(this, map);
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

	public void loadNextMap(GameMap currentMap) {
		startGame(worldmanager.getMextMap(currentMap));
		
	}
}