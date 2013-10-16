package com.tddd23.blokz.menu;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.font.FontHandler;
import com.tddd23.blokz.gfx.TextureHandler;

public abstract class Menu implements Screen {
	private SpriteBatch batch;
	private ShapeRenderer rectRenderer;
	private ArrayList<AbstractMenuItem> menuItems;
	private ArrayList<AbstractMenuItem> menuItemsToShow;
	private int pointer;
	private int relPointer;
	private int highLightedItem;
	protected Blokz game;
	private BitmapFont font;

	public Menu(Blokz game) {
		this.game = game;
		Gdx.input.setInputProcessor(new MenuInput(this));
		menuItems = new ArrayList<AbstractMenuItem>();
		menuItemsToShow = new ArrayList<AbstractMenuItem>();
		batch = new SpriteBatch();
		rectRenderer = new ShapeRenderer();

	}

	public abstract String getTitle();
	
	public abstract void goBack();

	protected void addMenuItem(AbstractMenuItem item) {
		menuItems.add(item);

	}

	public void triggerMenuItem(float x, float y) {
		for (AbstractMenuItem item : menuItemsToShow) {
			if (item.isUnlocked() && item.getBounds().contains(x, y))
				item.trigger();
		}
	}

	public void triggerMenuItem() {
		if (!menuItems.get(pointer).isUnlocked())
			return;
		menuItems.get(pointer).trigger();
	}

	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawText("Blokz", 700, 18);
		drawText("Use UP/DOWN/ENTER or SCROLL/CLICK on your mouse to select",
				30, 1);
		drawText(getDashes(16), 580, 5);
		drawText(getTitle(), 500, 6);
		setMenuItemsToShow();
		for (AbstractMenuItem item : menuItemsToShow) {
			if (menuItemsToShow.indexOf(item) == highLightedItem) {
				rectRenderer.begin(ShapeType.Filled);
				rectRenderer.rect(item.getBounds().x, item.getBounds().y,
						item.getBounds().width, item.getBounds().height,
						new Color(Color.DARK_GRAY), Color.DARK_GRAY,
						Color.DARK_GRAY, Color.DARK_GRAY);
				rectRenderer.end();
			}
			if (item.isUnlocked()) {
				font.setColor(1, 1, 1, 1);
			} else {
				font.setColor(1, 1, 1, 0.2f);
			}
			if (item.getTitle2() != null && !item.getTitle2().equals("-1"))
				drawText(item.getTitle2(),500,
						(400 - menuItemsToShow.indexOf(item) * 75), 3);
			drawText(item.getTitle(),
					(400 - menuItemsToShow.indexOf(item) * 75), 3);

		}
	}

	private void setMenuItemsToShow() {
		menuItemsToShow.clear();
		if (pointer < 3) {
			for (int x = 0; x < 5; x++) {
				if (x < menuItems.size())
					menuItemsToShow.add(menuItems.get(x));
			}
		} else if (pointer > 2 && pointer < menuItems.size() - 2) {
			for (int x = pointer - 2; x < pointer + 3; x++)
				menuItemsToShow.add(menuItems.get(x));
		} else {
			for (int x = menuItems.size() - 5; x < menuItems.size(); x++)
				menuItemsToShow.add(menuItems.get(x));
		}
		setRectangles();
	}

	private void setRectangles() {
		font = FontHandler.font[3];
		for (AbstractMenuItem item : menuItemsToShow) {
			item.setBounds(new Rectangle(
					50,
					400
							- menuItemsToShow.indexOf(item)
							* 75
							- font.getBounds(item.getTitle(), 0, item
									.getTitle().length()).height - 15,
					font.getBounds(item.getTitle(), 0, item.getTitle().length()).width,
					font.getBounds(item.getTitle(), 0, item.getTitle().length()).height + 10 + 30));
		}
	}

	private void drawText(String str, int y, int fontSize) {
		batch.begin();
		font = FontHandler.font[fontSize];
		font.setColor(Color.WHITE);
		font.draw(batch, str, 50, y);
		batch.end();
	}

	private void drawText(String str, int x, int y, int fontSize) {
		batch.begin();
		font = FontHandler.font[fontSize];
		font.setColor(Color.WHITE);
		font.draw(batch, str, x, y);
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
		relPointer = pointer;
		if (menuItems.size() > 4 && pointer > menuItems.size() - 3)
			relPointer = 5 - (menuItems.size() - pointer);

		if (menuItems.size() > 4 && pointer > 2
				&& pointer < menuItems.size() - 2) {
			relPointer = 2;
		}
		highLightedItem = relPointer;
	}

	public void decreasePointer() {
		pointer--;
		if (pointer < 0)
			pointer = menuItems.size() - 1;
		relPointer = pointer;
		if (menuItems.size() > 4 && pointer > menuItems.size() - 3)
			relPointer = 5 - (menuItems.size() - pointer);
		if (menuItems.size() > 4 && pointer > 2
				&& pointer < menuItems.size() - 2)
			relPointer = 2;
		highLightedItem = relPointer;
	}

	@Override
	public void dispose() {
	}

	private String getDashes(int i) {
		String a = "";
		for (int x = 0; x < i; x++)
			a += "-";
		return a;
	}

	public void hoverMenuItem(int screenX, int screenY) {
		for (AbstractMenuItem item : menuItemsToShow) {
			if (item.getBounds().contains(screenX, screenY))
				highLightedItem = menuItemsToShow.indexOf(item);
		}
	}

}
