package com.tddd23.blokz;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<MovableObject> dynamicObjects;
	private Block[][] blocks;
	private Player player;
	private Point spawnPoint;
	private TiledMap map;

	private Dimension mapSize;

	private int maxNrOfBlocks;

	ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();

	private Vector2 gravity;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight) {
		mapSize = new Dimension(nrOfBlocksWidth, nrOfBlocksHeight);
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
	}

	public void addBlockObject(int posX, int posY) {
		if (blocks[posX][posY] == null) {
			blocks[posX][posY] = BlockFactory.createBlock(posX, posY, this);
		}

	}

	public void createPlayer() {
		this.player = new Player(new Vector2(spawnPoint.x, spawnPoint.y), this);
	}

	public void update() {
		player.update(Gdx.graphics.getDeltaTime());
		for (MovableObject obj : dynamicObjects) {
			obj.update(Gdx.graphics.getDeltaTime());
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
