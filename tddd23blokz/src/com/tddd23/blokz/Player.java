package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.gfx.DebugWindow;

public class Player extends MovableObject {
	private State state = State.IDLE;

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	public Player(Vector2 position, float speed, World world) {
		super(position, speed, world);
		getBounds().height = 28f;
		getBounds().width = 16;
		setMovable(true);
	}

	public void jump(float multiplier) {
		getAcceleration().y = Constants.JUMPING_SPEED*multiplier;
	}

	public void jump() {
		if (grounded) {
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

	@Override
	public void addGravity() {
		getAcceleration().y += world.getGravity().y;
	}

	@Override
	public void updateObject() {
		if (state == State.IDLE) {
			getVelocity().set(0, getAcceleration().y);
		}
		if (getAcceleration().y < -Constants.MAX_FALLING_SPEED)
			getAcceleration().y = -Constants.MAX_FALLING_SPEED;

		if (state == State.WALKING) {
			if (facingLeft) {
				getVelocity().set(-getSpeed(), getAcceleration().y);
				if (getVelocity().x < -Constants.MAX_MOVING_SPEED)
					getVelocity().set(-Constants.MAX_MOVING_SPEED,
							getVelocity().y);

			} else {
				getVelocity().set(getSpeed(), getAcceleration().y);
				if (getVelocity().x > Constants.MAX_MOVING_SPEED)
					getVelocity().set(Constants.MAX_MOVING_SPEED,
							getVelocity().y);
			}
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
