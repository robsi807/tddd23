package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	public static final int MAP_WIDTH = 30, MAP_HEIGHT = 30;

	public enum GameState {
		GAME_READY, GAME_RUNNING, GAME_PAUSED, GAME_OVER;
	}

	private GameState state;

	private WorldRenderer renderer;
	private World world;

	public GameScreen(Blokz game) {
		state = GameState.GAME_RUNNING;
		world = WorldFactory.createMap("test2");
		renderer = new WorldRenderer(world);
		Gdx.input.setInputProcessor(new GameInput(world, game));
	}

	@Override
	public void render(float delta) {
		switch (state) {
		case GAME_READY:
			// Game_ready = 3...2...1... kör!
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
			//Skriv ut något och gå till game_ready
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
		world.getMap().dispose();
		renderer.getRenderer().dispose();
	}

}
