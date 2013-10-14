package com.tddd23.blokz;

import java.awt.Point;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.GameScreen.GameState;
import com.tddd23.blokz.Player.State;
import com.tddd23.blokz.font.Key;
import com.tddd23.blokz.world.World;

public class GameInput implements InputProcessor {

	private World world;
	private Blokz game;
	private Player player;
	private boolean walkRight = false, walkLeft = false;
	private Ray ray;
	private Point clickPoint;

	public GameInput(World world, Blokz game) {
		this.world = world;
		this.player = world.getPlayer();
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {

		case Key.A:
			walkLeft = true;
			return processMove();
		case Key.D:
			walkRight = true;
			return processMove();
		case Key.SPACE:
			if (game.getGameScreen().getState() == GameState.GAME_PAUSED) {
				game.getGameScreen().setState(GameState.GAME_RUNNING);
				return processMove();
			}
			if (game.getGameScreen().getState() == GameState.GAME_READY) {
				game.getGameScreen().setState(GameState.GAME_RUNNING);
				return processMove();
			}
			if (game.getGameScreen().getState() == GameState.GAME_OVER) {
				game.getGameScreen().resetMap();
				return processMove();
			}
			if (game.getGameScreen().getState() == GameState.WAITING_FOR_NEXT_MAP)
				game.getGameScreen().loadNextMap();
			player.jump();
			return processMove();
		case Key.ESC:
			if (game.getGameScreen().getState() == GameState.GAME_RUNNING) {
				game.getGameScreen().setState(GameState.GAME_PAUSED);
				return true;
			}
			if (game.getGameScreen().getState() == GameState.GAME_PAUSED) {
				game.goToMapMenu();
				return true;
			}
			return true;
		case Key.CONSOLE:
			game.getGameScreen().getRenderer().switchDebugMode();
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
		if (!(game.getGameScreen().getState() == GameState.GAME_RUNNING))
			return false;

		if (walkLeft && walkRight) {
			player.setState(State.IDLE);
			return true;
		}
		if (walkLeft) {
			player.setState(State.WALKING);
			player.facingLeft = true;
		}
		if (walkRight) {
			player.setState(State.WALKING);
			player.facingLeft = false;
		}
		if (!walkRight && !walkLeft)
			player.setState(State.IDLE);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!world.getGameMap().isAllowPlacingBlocks())
			return false;
		if (!(game.getGameScreen().getState() == GameState.GAME_RUNNING))
			return false;

		ray = game.getGameScreen().getRenderer().getRay(screenX, screenY);
		clickPoint = new Point((int) ray.origin.x, (int) ray.origin.y);
		clickPoint.x = (int) (clickPoint.x - (clickPoint.x % Constants.SIZE));
		clickPoint.y = (int) (clickPoint.y - (clickPoint.y % Constants.SIZE));

		if (clickPoint.y < 0 || clickPoint.x < 0
				|| clickPoint.x >= world.getMapSize().width
				|| clickPoint.y >= world.getMapSize().height)
			return false; // Utanför

		if (!world.isPlaceable(clickPoint.x / Constants.SIZE, clickPoint.y
				/ Constants.SIZE))
			return false;// Klickat på ett befintlig block

		world.addBlockObject(clickPoint.x, clickPoint.y);
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if (amount < 0) {
			game.getGameScreen().getRenderer().zoomIn();
		} else {
			game.getGameScreen().getRenderer().zoomOut();
		}
		return false;
	}

}
