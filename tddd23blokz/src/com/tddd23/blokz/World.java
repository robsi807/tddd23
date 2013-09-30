package com.tddd23.blokz;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.blocks.Block;

public class World {

	private ArrayList<MovableObject> dynamicObjects;
	private Block[][] blocks;
	private Player player;
	private Point spawnPoint;
	private TiledMap map;

	private Dimension mapSize;

	private int maxNrOfBlocks;

	ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();

	private TiledMapTileLayer blockLayer;
	private Rectangle rect;

	private Vector2 gravity;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight) {
		mapSize = new Dimension(nrOfBlocksWidth, nrOfBlocksHeight);
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
	}

	public void addBlockObject(float posX, float posY) {
		// blocks[posX][posY] = new Block(new Vector2(posX, posY), this);
		// dynamicObjects.add(obj);
	}

	public void createPlayer() {
		this.player = new Player(new Vector2(spawnPoint.x, spawnPoint.y),
				Constants.SPEED * 2, this);
	}

	public boolean isPlaceable(int x, int y) {
		blockLayer = (TiledMapTileLayer) map.getLayers().get("blocks");
		if (blockLayer.getCell((int) (x / Constants.SIZE),
				(int) (y / Constants.SIZE)) != null)
			return false;

		rect = new Rectangle(x, y, Constants.SIZE, Constants.SIZE);
		if (player.getPositionRectangle().overlaps(rect))
			return false;

		for (MovableObject obj : dynamicObjects)
			if (obj.getPositionRectangle().overlaps(rect))
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

	public ArrayList<Rectangle> getCollisionRects() {
		return collisionRects;
	}

	public void setCollisionRects(ArrayList<Rectangle> collisionRects) {
		this.collisionRects = collisionRects;
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

	public TiledMap getMap() {
		return map;
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

	public void setMap(TiledMap map) {
		this.map = map;
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

}
