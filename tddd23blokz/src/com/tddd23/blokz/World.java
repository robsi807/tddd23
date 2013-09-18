package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<GameObject> dynamicObjects;

	private Player player;

	public ArrayList<GameObject> getDynamicObjects() {
		return dynamicObjects;
	}

	private Vector2 gravity;

	
	public World() {
		gravity = new Vector2(0, -0.05f);
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
		for(int y=0;y<39;y++){
			dynamicObjects.add(BlockFactory.createBlock(56, y, this));
			dynamicObjects.add(BlockFactory.createBlock(0, y, this));
		}
		for(int x=0;x<57;x++){
			dynamicObjects.add(BlockFactory.createBlock(x, 38, this));
			dynamicObjects.add(BlockFactory.createBlock(x, 0, this));
		}
		dynamicObjects.add(BlockFactory.createBlock(32, 12, this));
		dynamicObjects.add(BlockFactory.createBlock(43, 3, this));
		dynamicObjects.add(BlockFactory.createBlock(12, 23, this));
		dynamicObjects.add(BlockFactory.createBlock(43, 22, this));
		dynamicObjects.add(BlockFactory.createBlock(2, 12, this));
		dynamicObjects.add(BlockFactory.createBlock(21, 22, this));
		dynamicObjects.add(BlockFactory.createBlock(12, 33, this));
		dynamicObjects.add(BlockFactory.createBlock(32, 1, this));
	}

	/*
	 * Creates the player
	 */
	private void createPlayer() {
		this.player = new Player(new Vector2(Constants.SIZE*30, Constants.SIZE*30), this);
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
	
	
	public void addDynamicObject(GameObject go){
		dynamicObjects.add(go);
	}
	
	/*
	 * Used for testing collision
	 */
	public void flipGravity(){
		gravity.x = -gravity.x;
		gravity.y = -gravity.y;
	}


}
