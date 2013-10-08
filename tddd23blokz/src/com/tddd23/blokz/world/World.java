package com.tddd23.blokz.world;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.MovableObject;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.triggers.Triggerable;

public class World {

	private GameScreen gamescreen;

	private ArrayList<Triggerable> triggers;
	private ArrayList<MovableObject> dynamicObjects;
	private Block[][] blocks;
	private Player player;
	private Point spawnPoint;

	private Dimension mapSize;

	private int maxNrOfBlocks;

	private Rectangle rect;

	public ArrayList<Triggerable> getTriggers() {
		return triggers;
	}

	public void addTrigger(Triggerable trigger) {
		triggers.add(trigger);
	}

	private Vector2 gravity;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight,
			GameScreen gamescreen) {
		this.gamescreen = gamescreen;
		mapSize = new Dimension(nrOfBlocksWidth, nrOfBlocksHeight);
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		this.triggers = new ArrayList<Triggerable>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
		// MusicCache.level1.play();
	}

	public void addBlockObject(float posX, float posY) {
		blocks[(int) (posX / Constants.SIZE)][(int) (posY / Constants.SIZE)] = new Block(
				new Vector2(posX, posY), this, player.getSelectedBlockType());
	}

	public void createPlayer() {
		this.player = new Player(new Vector2(spawnPoint.x, spawnPoint.y),
				Constants.SPEED, this);
	}

	public boolean isPlaceable(int tileX, int tileY) {
		if (blocks[tileX][tileY] != null)
			return false;

		rect = new Rectangle(tileX*Constants.SIZE, tileY*Constants.SIZE, Constants.SIZE, Constants.SIZE);
		if (player.getPositionRectangle().overlaps(rect))
			return false;

		return true;
	}

	public MovableObject getDynamicObjectAt(int x, int y) {
		for (MovableObject obj : dynamicObjects)
			if (obj.getPositionRectangle().overlaps(rect))
				return obj;
		return null;
	}

	public void update(float delta) {
		player.update(delta);
		for (MovableObject obj : dynamicObjects) {
			obj.update(delta);
		}
	}

	public ArrayList<MovableObject> getDynamicObjects() {
		return dynamicObjects;
	}

	public Player getPlayer() {
		return player;
	}

	public Vector2 getGravity() {
		return gravity;
	}

	public void addDynamicObject(MovableObject go) {
		dynamicObjects.add(go);
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public Point getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(Point spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxNrOfBlocks() {
		return maxNrOfBlocks;
	}

	public void setMaxNrOfBlocks(int maxNrOfBlocks) {
		this.maxNrOfBlocks = maxNrOfBlocks;
	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void killPlayer() {
		// player.setPosition(new Vector2(spawnPoint.x, spawnPoint.y));
		gamescreen.resetMap();
	}
	public void loadNextWorld() {
		// player.setPosition(new Vector2(spawnPoint.x, spawnPoint.y));
		gamescreen.loadNextMap();
	}

}
