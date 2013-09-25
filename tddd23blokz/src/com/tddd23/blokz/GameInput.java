package com.tddd23.blokz;

import java.awt.Point;

import javax.swing.JEditorPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.GameObject.State;

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
		case 73:
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


	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == 1){
			world.getDynamicObjects().clear();
			return false;
		}
		ray = game.getGameScreen().getRenderer().getRay(screenX, screenY);
		clickPoint  = new Point((int)ray.origin.x,(int)ray.origin.y);
		clickPoint.x = (int) (clickPoint.x - (clickPoint.x%Constants.SIZE));
		clickPoint.y = (int) (clickPoint.y - (clickPoint.y%Constants.SIZE));
		
		
		if(clickPoint.y <0 || clickPoint.x < 0 || clickPoint.x>=world.getMapSize().width || clickPoint.y>=world.getMapSize().height)
			return false; //Utanför
		
		if(!world.isPlaceable(clickPoint.x, clickPoint.y))
			return false;//Klickat på ett befintlig block
		world.addBlockObject(clickPoint.x, clickPoint.y);
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		ray = game.getGameScreen().getRenderer().getRay(screenX, screenY);
		clickPoint  = new Point((int)ray.origin.x,(int)ray.origin.y);
		clickPoint.x = (int) (clickPoint.x - (clickPoint.x%Constants.SIZE));
		clickPoint.y = (int) (clickPoint.y - (clickPoint.y%Constants.SIZE));
		
		

		if(clickPoint.y <0 || clickPoint.x < 0 || clickPoint.x>=world.getMapSize().width || clickPoint.y>=world.getMapSize().height)
			return false; //Utanför
		
		if(!world.isPlaceable(clickPoint.x, clickPoint.y))
			return false;//Klickat på ett befintlig block
		world.addBlockObject(clickPoint.x, clickPoint.y);
		
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
