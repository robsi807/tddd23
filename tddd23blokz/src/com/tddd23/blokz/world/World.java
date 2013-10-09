package com.tddd23.blokz.world;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.MinMax;
import com.tddd23.blokz.MovableObject;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.triggers.PlayerTrigger;
import com.tddd23.blokz.triggers.Triggerable;

public class World {

	private GameScreen gamescreen;

	private ArrayList<PlayerTrigger> triggers;
	private ArrayList<MovableObject> dynamicObjects;
	private Block[][] blocks;
	private Player player;
	private Point spawnPoint;

	private Dimension mapSize;

	private int maxNrOfBlocks;

	private Rectangle rect;

	private MinMax relevantBlocks;

	private Vector2 gravity;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight,
			GameScreen gamescreen) {
		this.gamescreen = gamescreen;
		mapSize = new Dimension(nrOfBlocksWidth, nrOfBlocksHeight);
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		this.triggers = new ArrayList<PlayerTrigger>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
		relevantBlocks = new MinMax();
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

		rect = new Rectangle(tileX * Constants.SIZE, tileY * Constants.SIZE,
				Constants.SIZE, Constants.SIZE);
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
		updateBlocks(delta);
	}

	private void updateBlocks(float delta) {
		relevantBlocks.setRelevantCoordinates(Constants.RENDER_DIST,
				getPlayer().getPosition(), this);
		for (int y = relevantBlocks.minY; y < relevantBlocks.maxY; y++)
			for (int x = relevantBlocks.minX; x < relevantBlocks.maxX; x++)
				if (getBlocks()[x][y] != null)
					blocks[x][y].update(delta);

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
		gamescreen.resetMap();
	}

	public void loadNextWorld() {
		gamescreen.loadNextMap();
	}

	public void addTrigger(PlayerTrigger trigger) {
		triggers.add(trigger);
	}

	public void addTrigger(ArrayList<PlayerTrigger> triggerList) {
		for (PlayerTrigger t : triggerList)
			triggers.add(t);
	}

	public ArrayList<PlayerTrigger> getTriggers() {
		return triggers;
	}

}
