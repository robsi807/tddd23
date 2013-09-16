package com.tddd23.blokz;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen {

	private WorldRenderer renderer;
	private World world;
	private FPSLogger fpsLog;

	public GameScreen(Blokz game) {
		fpsLog = new FPSLogger();
		world = new World();
		renderer = new WorldRenderer(world);
		Gdx.input.setInputProcessor(new GameInput(world.getPlayer(), game));
	}

	@Override
	public void render(float delta) {
		updateGame();

		fpsLog.log();

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render();

	}

	private void updateGame() {
		world.update();
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
		// TODO Auto-generated method stub
	}

}
