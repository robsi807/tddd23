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
	}
	
	public void addDynamicObject(int posX, int posY){
		dynamicObjects.add(BlockFactory.createBlock(posX, posY, this));
		
	}
	private void createDynamicObjects() {
		for (int y = 0; y < 39; y++) {
			dynamicObjects.add(BlockFactory.createBlock(56, y, this));
			dynamicObjects.add(BlockFactory.createBlock(0, y, this));
		}
		for (int x = 0; x < 57; x++) {
			dynamicObjects.add(BlockFactory.createBlock(x, 38, this));
			dynamicObjects.add(BlockFactory.createBlock(x, 0, this));
		}

		
	}

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

}
