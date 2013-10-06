package com.tddd23.blokz.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tddd23.blokz.font.FontHandler;
import com.tddd23.blokz.gfx.TextureHandler;

public class Menu implements Screen {
	private SpriteBatch batch;
	private ArrayList<AbstractMenuItem> menuItems;
	private int pointer;
	protected Game game;
	private BitmapFont font;

	public Menu(Game game) {
		this.game = game;
		Gdx.input.setInputProcessor(new MenuInput(this));
		menuItems = new ArrayList<AbstractMenuItem>();
		batch = new SpriteBatch();
		font = FontHandler.courier25;

	}

	protected void addMenuItem(AbstractMenuItem item) {
		menuItems.add(item);

	}

	public void triggerMenuItem() {
		menuItems.get(pointer).trigger();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(TextureHandler.mainMenuTitle, 200, 500);
		batch.end();
		batch.begin();
		for (AbstractMenuItem item : menuItems)
			font.draw(batch, item.getTitle(), 250,
					(float) (500 - menuItems.indexOf(item) * 75));
		font.draw(batch,"|", 230,
				(float) (500 - pointer * 75));
		batch.end();
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

	public void increasePointer() {
		pointer++;
		if (pointer >= menuItems.size())
			pointer = 0;
	}

	public void decreasePointer() {
		pointer--;
		if (pointer < 0)
			pointer = menuItems.size()-1;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
