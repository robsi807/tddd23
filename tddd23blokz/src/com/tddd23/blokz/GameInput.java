package com.tddd23.blokz;

import com.badlogic.gdx.InputProcessor;
import com.tddd23.blokz.GameObject.State;

public class GameInput implements InputProcessor {

	private Blokz game;
	private Player player;

	public GameInput(Player player, Blokz game) {
		this.player = player;
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case 29:
			player.state = State.WALKING;
			player.facingLeft = true;
			return true;
		case 32:
			player.state = State.WALKING;
			player.facingLeft = false;
			return true;
		case 131:
			game.exitGame();
			return true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case 29:
			player.state = State.IDLE;
			return true;
		case 32:
			player.state = State.IDLE;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
