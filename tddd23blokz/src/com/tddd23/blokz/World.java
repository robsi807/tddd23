package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<GameObject> dynamicObjects;

	private Player player;
	
	private Vector2 gravity;

	public World() {
		gravity = new Vector2(0, 0.01f);
		this.dynamicObjects = new ArrayList<GameObject>();
		this.player = new Player(new Vector2(4, 4), this);
		dynamicObjects.add(player);
		System.out.println("Size of dyn = " + dynamicObjects.size());
	}

	public void update() {
		for (GameObject obj : dynamicObjects) {
			obj.update();
		}
	}

	public Player getPlayer() {
		return player;
	}

}
