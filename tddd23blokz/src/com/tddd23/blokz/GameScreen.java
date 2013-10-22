package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tddd23.blokz.audio.MusicCache;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.world.World;
import com.tddd23.blokz.world.WorldFactory;
import com.tddd23.blokz.world.WorldRenderer;

public class GameScreen implements Screen {

	public static final int MAP_WIDTH = 30, MAP_HEIGHT = 30;

	public enum GameState {
		GAME_READY, GAME_RUNNING, GAME_PAUSED, GAME_OVER, WAITING_FOR_NEXT_MAP;
	}

	private GameState state;

	private WorldRenderer renderer;
	private World world;
	private Blokz game;
	private GameMap currentMap;

	private boolean updateGame;

	public GameScreen(Blokz game, GameMap map) {
		state = GameState.GAME_READY;
		this.currentMap = map;
		world = WorldFactory.createMap(map, this);
		renderer = new WorldRenderer(world, this);
		this.game = game;
		updateGame = true;
		Gdx.input.setInputProcessor(new GameInput(world, game));
	}

	public GameMap getCurrentMap() {
		return currentMap;
	}

	@Override
	public void render(float delta) {
		switch (state) {
		case GAME_READY:
			renderer.setRunning(false);
			renderer.setOpacity(0.2f);
			renderer.render(delta);
			renderer.drawGetReady();
			break;
		case GAME_RUNNING:
			renderer.setRunning(true);
			renderer.setOpacity(1f);
			updateGame(delta);
			renderer.render(delta);
			break;
		case GAME_PAUSED:
			renderer.setRunning(false);
			renderer.setOpacity(0.2f);
			renderer.render(delta);
			renderer.drawPause();
			break;
		case WAITING_FOR_NEXT_MAP:
			boolean record = isNewRecord();
			renderer.setRunning(false);
			renderer.setOpacity(0.2f);
			renderer.render(delta);
			renderer.drawNextMap(record, currentMap.getTime().getCopyTime());
			break;
		case GAME_OVER:
			renderer.setRunning(false);
			renderer.setOpacity(0.2f);
			renderer.render(delta);
			renderer.drawDeath();
			break;
		}
	}

	public boolean isNewRecord() {
		if (currentMap.getTime().getCopyTime().getMillis() == -1)
			return true;
		return (currentMap.getTime().getCopyTime().getMillis() > renderer
				.getTime().getMillis()) ? true : false;
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
		if (updateGame)
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
		updateGame = false;
		game.startGame(currentMap);
	}

	public void loadNextMap() {
		game.loadNextMap();
	}

}
