package com.tddd23.blokz.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.font.Key;

public class MenuInput implements InputProcessor {

	private Menu callingMenu;

	public MenuInput(Menu menu) {
		callingMenu = menu;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Key.W:
		case Key.UP:
			callingMenu.decreasePointer();
			SoundCache.menu_beep.play(SoundCache.getVolume());
			return true;
		case Key.S:
		case Key.DOWN:
			callingMenu.increasePointer();
			SoundCache.menu_beep.play(SoundCache.getVolume());
			return true;
		case Key.SPACE:
		case Key.ENTER:
			if (callingMenu instanceof CreditsMenu)
				break;
			callingMenu.triggerMenuItem();
			SoundCache.menu_beep_select.play(SoundCache.getVolume());
			return true;
		case Key.ESC:
			callingMenu.goBack();
			break;
		case Key.CONSOLE:
			callingMenu.unlockMaps();
			break;
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
		callingMenu
				.triggerMenuItem(screenX, Gdx.graphics.getHeight() - screenY);
		SoundCache.menu_beep_select.play();
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
		callingMenu.hoverMenuItem(screenX, Gdx.graphics.getHeight() - screenY);

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if (amount < 0)
			callingMenu.decreasePointer();
		else
			callingMenu.increasePointer();
		return false;
	}

}
