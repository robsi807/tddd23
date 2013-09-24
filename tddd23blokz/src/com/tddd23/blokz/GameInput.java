package com.tddd23.blokz;

import javax.swing.JEditorPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.tddd23.blokz.GameObject.State;

public class GameInput implements InputProcessor {
	
	private World world;
	private Blokz game;
	private Player player;
	private boolean walkRight = false, walkLeft = false;

	public GameInput(World world, Blokz game) {
		this.world = world;
		this.player = world.getPlayer();
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case 29:
			walkLeft = true;
			return processMove();
		case 32:
			walkRight = true;
			return processMove();
		case 62:
			player.jump();
			return processMove();
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
			walkLeft = false;
			return processMove();
		case 32:
			walkRight = false;
			return processMove();

		}
		return false;
	}

	private boolean processMove() {
		if (walkLeft && walkRight) {
			player.state = State.IDLE;
			return true;
		}
		if (walkLeft) {
			player.state = State.WALKING;
			player.facingLeft = true;
		}
		if (walkRight) {
			player.state = State.WALKING;
			player.facingLeft = false;
		}
		if (!walkRight && !walkLeft)
			player.state = State.IDLE;
		return true;
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
		float percentageX = (float)screenX/Gdx.graphics.getWidth();
		float percentageY = (float)screenY/Gdx.graphics.getHeight();
//	    world.addBlockObject((int)(percentageX*Constants.numberOfBlocksOnScreen.x), (int)(percentageY*Constants.numberOfBlocksOnScreen.y));
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float percentageX = (float)screenX/Gdx.graphics.getWidth();
		float percentageY = (float)screenY/Gdx.graphics.getHeight();
//	    world.addBlockObject((int)(percentageX*57), (int)(percentageY*39));
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
