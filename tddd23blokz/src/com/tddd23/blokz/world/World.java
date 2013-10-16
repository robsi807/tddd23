package com.tddd23.blokz.world;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.GameScreen.GameState;
import com.tddd23.blokz.MinMax;
import com.tddd23.blokz.MovableObject;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.blocks.Block.BlockType;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.triggers.PlayerTrigger;

public class World {

	private GameScreen gamescreen;

	private ArrayList<PlayerTrigger> triggers;
	private ArrayList<MovableObject> dynamicObjects;
	private Block[][] blocks;
	private int[] allowedBlocks;
	private Player player;
	private Point spawnPoint;
	private float stateTime;
	private ArrayList<BlockType> blockOrder;

	private Dimension mapSize;

	private int maxNrOfBlocks;

	private Rectangle rect;

	private MinMax relevantBlocks;

	private Vector2 gravity;

	private GameMap gameMap;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight,
			GameScreen gamescreen, GameMap gameMap) {
		this.gamescreen = gamescreen;
		mapSize = new Dimension(nrOfBlocksWidth, nrOfBlocksHeight);
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		this.triggers = new ArrayList<PlayerTrigger>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
		relevantBlocks = new MinMax();
		this.gameMap = gameMap;
		allowedBlocks = new int[] { -1, -1, -1 };
		blockOrder = new ArrayList<BlockType>();
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void addBlockObject(float posX, float posY, GameScreen gameScreen2) {
		if (allowedBlocks[player.getSelectedBlockType().ordinal()] > 0) {
			blocks[(int) (posX / Constants.SIZE)][(int) (posY / Constants.SIZE)] = new Block(
					new Vector2(posX, posY), this,
					player.getSelectedBlockType(), gamescreen);
			allowedBlocks[player.getSelectedBlockType().ordinal()]--;
			SoundCache.place_block.play();
		}
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

	public void update(float delta) {
		stateTime += delta;
		player.update(delta);
		updateBlocks(delta);
	}

	public float getStateTime() {
		return stateTime;
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

	public void setMaxNrOfBlocks(int[] maxNrOfBlocks) {
		allowedBlocks = maxNrOfBlocks;
		for (int i = 0; i < maxNrOfBlocks.length; i++)
			if (maxNrOfBlocks[i] >= 0)
				blockOrder.add(BlockType.values()[i]);

	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void killPlayer() {
		gamescreen.getRenderer().getVisualEffHand().showDeath();
		gamescreen.setState(GameState.GAME_OVER);
	}

	public void resetMap() {
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

	public int[] getAllowedBlocks() {
		return allowedBlocks;
	}

	public ArrayList<BlockType> getBlockOrder() {
		return blockOrder;
	}

}
