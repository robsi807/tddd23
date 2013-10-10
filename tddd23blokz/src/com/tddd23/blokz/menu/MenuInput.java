package com.tddd23.blokz.menu;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MenuInput implements InputProcessor {

	private Menu callingMenu;

	public MenuInput(Menu menu) {
		callingMenu = menu;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case 19:
			// callingMenu.decreasePointer();
			callingMenu.decreasePointer();
			return true;
		case 20:
			callingMenu.increasePointer();
			return true;
		case 66:
			callingMenu.triggerMenuItem();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		callingMenu.triggerMenuItem(screenX, Gdx.graphics.getHeight()-screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		callingMenu.hoverMenuItem(screenX, Gdx.graphics.getHeight()-screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(amount<0)
			callingMenu.decreasePointer();
		else
			callingMenu.increasePointer();
		return false;
	}

}
