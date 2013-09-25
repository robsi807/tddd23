package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.tddd23.blokz.gfx.WorldRenderer;

public class GameScreen implements Screen {

	public static final int MAP_WIDTH = 30, MAP_HEIGHT = 30;

	private WorldRenderer renderer;
	private World world;
	
	public GameScreen(Blokz game) {
		world = WorldFactory.createMap("test2");
		renderer = new WorldRenderer(world);
		Gdx.input.setInputProcessor(new GameInput(world, game));
	}

	@Override
	public void render(float delta) {
		updateGame(delta);
		renderer.render(delta);
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
