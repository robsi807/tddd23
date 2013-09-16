package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class World {

	private ArrayList<GameObject> dynamicObjects;

	private Player player;

	public World() {
		this.dynamicObjects = new ArrayList<GameObject>();
		this.player = new Player(new Vector2(0, 0));
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
