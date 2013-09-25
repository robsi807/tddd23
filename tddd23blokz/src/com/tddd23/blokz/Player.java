package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class Player extends MovableObject {

	public Player(Vector2 position, World world) {
		super(position, world);
		getBounds().height = 28f;
		getBounds().width = 16;
	}

	public void jump() {
		if (grounded) {
			grounded = true;
			getAcceleration().y = Constants.JUMPING_SPEED;
		}
	}

	private void playergetPositionDebugText() {

		DebugWindow.addText(" ");
		DebugWindow.addText("PlayergetAcceleration(): X: "
				+ getAcceleration().x + " Y: " + getAcceleration().y);
		DebugWindow.addText("PlayergetVelocity(): X: " + getVelocity().x
				+ " Y: " + getVelocity().y);
		DebugWindow.addText("PlayergetPosition(): X: " + getPosition().x
				+ " Y: " + getPosition().y);
		DebugWindow.addText("På marken: " + grounded);

	}

}
