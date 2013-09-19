package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<GameObject> dynamicObjects;

	private Player player;

	ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();

	public ArrayList<GameObject> getDynamicObjects() {
		return dynamicObjects;
	}

	public ArrayList<Rectangle> getCollisionRects() {
		return collisionRects;
	}

	public void setCollisionRects(ArrayList<Rectangle> collisionRects) {
		this.collisionRects = collisionRects;
	}

	private Vector2 gravity;

	public World() {
		gravity = Constants.WORLD_GRAVITY;
		this.dynamicObjects = new ArrayList<GameObject>();
		createPlayer();
		createDynamicObjects();
		// dynamicObjects.add(player);
		System.out.println("Size of dyn = " + dynamicObjects.size());
	}

	/*
	 * Creates dynamic objects and adds them to the worlds arraylist over
	 * dynamicobjects
	 */
	private void createDynamicObjects() {
		for (int y = 0; y < 39; y++) {
			dynamicObjects.add(BlockFactory.createBlock(56, y, this));
			dynamicObjects.add(BlockFactory.createBlock(0, y, this));
		}
		for (int x = 0; x < 57; x++) {
			dynamicObjects.add(BlockFactory.createBlock(x, 38, this));
			dynamicObjects.add(BlockFactory.createBlock(x, 0, this));
		}

		// Maze

		for (int y = 3; y < 30; y += 3) {
			int startX = 1;
			int endX = 50;
			if (y % 2 == 0) {
				startX += 6;
				endX += 6;
			}
			for (int x = startX; x < endX; x++)
				dynamicObjects.add(BlockFactory.createBlock(x, y, this));

		}
		dynamicObjects.add(BlockFactory.createBlock(4, 35, this));
		dynamicObjects.add(BlockFactory.createBlock(4, 37, this));
		dynamicObjects.add(BlockFactory.createBlock(4, 32, this));
		
		
		dynamicObjects.add(BlockFactory.createBlock(15, 35, this));
		
		
		dynamicObjects.add(BlockFactory.createBlock(20, 32, this));
		
		
		dynamicObjects.add(BlockFactory.createBlock(30, 35, this));
		
		dynamicObjects.add(BlockFactory.createBlock(35, 34, this));
		
		dynamicObjects.add(BlockFactory.createBlock(40, 33, this));
		
		dynamicObjects.add(BlockFactory.createBlock(45, 32, this));
		
		dynamicObjects.add(BlockFactory.createBlock(50, 31, this));
		
	}

	/*
	 * Creates the player
	 */
	private void createPlayer() {
		this.player = new Player(new Vector2(Constants.SIZE * 30,
				Constants.SIZE * 30), this);
	}

	public void update() {
		player.update();
		for (GameObject obj : dynamicObjects) {
			obj.update();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public Vector2 getGravity() {
		return gravity;
	}

	public void addDynamicObject(GameObject go) {
		dynamicObjects.add(go);
	}

	/*
	 * Used for testing collision
	 */
	public void flipGravity() {
		gravity.x = -gravity.x;
		gravity.y = -gravity.y;
	}

}
