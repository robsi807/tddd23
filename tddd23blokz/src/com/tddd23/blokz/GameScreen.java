package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.world.World;
import com.tddd23.blokz.world.WorldFactory;
import com.tddd23.blokz.world.WorldRenderer;

public class GameScreen implements Screen {

	public static final int MAP_WIDTH = 30, MAP_HEIGHT = 30;

	public enum GameState {
		GAME_READY, GAME_RUNNING, GAME_PAUSED, GAME_OVER;
	}

	private GameState state;

	private WorldRenderer renderer;
	private World world;
	private Blokz game;
	private GameMap currentMap;

	public GameScreen(Blokz game, GameMap map) {
		state = GameState.GAME_READY;
		this.currentMap = map;
		world = WorldFactory.createMap(map, this);
		renderer = new WorldRenderer(world);
		this.game = game;
		Gdx.input.setInputProcessor(new GameInput(world, game));
	}

	@Override
	public void render(float delta) {
		switch (state) {
		case GAME_READY:
			renderer.setOpacity(0.2f);
			renderer.render();
			renderer.drawGetReady();
			break;
		case GAME_RUNNING:
			renderer.setOpacity(1f);
			updateGame(delta);
			renderer.render();
			break;
		case GAME_PAUSED:
			renderer.setOpacity(0.2f);
			renderer.render();
			renderer.drawPause();
			break;
		case GAME_OVER:
			// Skriv ut något och gå till game_ready
			break;
		}
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public WorldRenderer getRenderer() {
		return renderer;
	}

	private void updateGame(float delta) {
		world.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
	}

	public void resetMap() {
		game.startGame(currentMap);
	}
	public void loadNextMap() {
		//Rendera ut lite goa grejer om stats etc
		game.loadNextMap(currentMap);
	}

}
