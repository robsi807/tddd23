package com.tddd23.blokz.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tddd23.blokz.font.FontHandler;
import com.tddd23.blokz.gfx.TextureHandler;

public abstract class Menu implements Screen {
	private SpriteBatch batch;
	private ArrayList<AbstractMenuItem> menuItems;
	private ArrayList<AbstractMenuItem> menuItemsToShow;
	private int pointer;
	protected Game game;
	private BitmapFont font;

	public Menu(Game game) {
		this.game = game;
		Gdx.input.setInputProcessor(new MenuInput(this));
		menuItems = new ArrayList<AbstractMenuItem>();
		menuItemsToShow = new ArrayList<AbstractMenuItem>();
		batch = new SpriteBatch();

	}

	public abstract String getTitle();

	protected void addMenuItem(AbstractMenuItem item) {
		menuItems.add(item);

	}

	public void triggerMenuItem() {
		menuItems.get(pointer).trigger();
	}

	public void render(float delta) {
		setMenuItemsToShow();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font = FontHandler.courier[14];
		font.draw(batch, "Blokz", 50, 700);
		font = FontHandler.courier[3];
		font.draw(batch, "Use UP/DOWN and ENTER to select", 55, 580);
		font.draw(batch, "-----------------------------------------------------------------", 55, 500);
		font = FontHandler.courier[6];
		font.draw(batch, getTitle(), 50, 450);
		font = FontHandler.courier[3];
		for (AbstractMenuItem item : menuItemsToShow)
			font.draw(batch, item.getTitle(), 50,
					(float) (350 - menuItemsToShow.indexOf(item) * 75));

		font.draw(batch, "|", 30, (float) (350 - pointer * 75));
		batch.end();
	}

	private void setMenuItemsToShow() {
		menuItemsToShow.clear();
		if(pointer <3)
			for(int x=0;x<menuItems.size();x++)
				menuItemsToShow.add(menuItems.get(x));
		else if  (pointer >2 && pointer< menuItems.size()-2)
			for(int x=pointer-2;x<pointer+5;x++)
				menuItemsToShow.add(menuItems.get(x));
		else 
			for(int x=menuItems.size()-5;x<menuItems.size();x++)
				menuItemsToShow.add(menuItems.get(x));
		
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
			pointer = menuItems.size() - 1;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
