package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class Player extends MovableObject {

	private int tick;

	public Player(Vector2 position, World world) {
		super(position, world);
		bounds.height = .65f;
		bounds.width = .9f;
	}

	public void jump() {
		if (grounded) {
			acceleration.add(new Vector2(0, -0.5f));
		}
	}

	private void playerPositionDebugText() {
		tick++;
		if (tick % 60 == 0) {
			System.out.println();
			System.out.println(tick / 60);
			System.out.println("PlayerAcceleration: X: " + acceleration.x
					+ " Y: " + acceleration.y);
			System.out.println("PlayerVelocity: X: " + velocity.x + " Y: "
					+ velocity.y);
			System.out.println("PlayerPosition: X: " + position.x + " Y: "
					+ position.y);
			System.out.println("På marken: " + grounded);

		}
	}

}
