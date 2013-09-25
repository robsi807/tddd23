package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class Player extends MovableObject {

	public Player(Vector2 position, World world) {
		super(position, world);
		bounds.height = 28f;
		bounds.width = 16;
	}

	public void jump() {
		if (grounded) {
//			playerPositionDebugText();
			grounded = true;
			acceleration.y = Constants.JUMPING_SPEED;
		}
	}

	private void playerPositionDebugText() {

		DebugWindow.addText(" ");
		DebugWindow.addText("PlayerAcceleration: X: " + acceleration.x + " Y: "
				+ acceleration.y);
		DebugWindow.addText("PlayerVelocity: X: " + velocity.x + " Y: "
				+ velocity.y);
		DebugWindow.addText("PlayerPosition: X: " + position.x + " Y: "
				+ position.y);
		DebugWindow.addText("På marken: " + grounded);

	}

}
