package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	public Player(Vector2 position, World world) {
		super(position, world);
	}

	public void jump() {
		if (grounded) {
			acceleration.add(new Vector2(0, -0.5f));
		}
	}

}
