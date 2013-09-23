package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<MovableObject> dynamicObjects;

	private Block[][] blocks;

	private Player player;

	ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();

	private Vector2 gravity;

	public World(int nrOfBlocksWidth, int nrOfBlocksHeight) {
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<MovableObject>();
		blocks = new Block[nrOfBlocksWidth][nrOfBlocksHeight];
		initBlocks(); // test method
		createPlayer();
		createDynamicObjects();
	}

	public void addBlockObject(int posX, int posY) {
		if (blocks[posX][posY] == null) {
			blocks[posX][posY] = BlockFactory.createBlock(posX, posY, this);
		}

	}

	private void createDynamicObjects() {
		// for (int y = 0; y < 39; y++) {
		// dynamicObjects.add(BlockFactory.createBlock(56, y, this));
		// dynamicObjects.add(BlockFactory.createBlock(0, y, this));
		// }
		// for (int x = 0; x < 57; x++) {
		// dynamicObjects.add(BlockFactory.createBlock(x, 38, this));
		// dynamicObjects.add(BlockFactory.createBlock(x, 0, this));
		// }

	}

	private void initBlocks() {
		for (int y = 0; y < blocks[0].length; y++) {
			for (int x = 0; x < blocks.length; x++) {
				if (x == blocks.length - 1 || x == 0 || y == 0
						|| y == blocks[0].length - 1) {
					blocks[x][y] = BlockFactory.createBlock(x, y, this);
				}
			}
		}
	}

	private void createPlayer() {
		this.player = new Player(new Vector2(Constants.SIZE * 10,
				Constants.SIZE * 10), this);
	}

	public void update() {
		player.update();
		for (MovableObject obj : dynamicObjects) {
			obj.update();
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

}
