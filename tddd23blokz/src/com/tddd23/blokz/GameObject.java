package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 0.1f; // unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.5f; // half a unit

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = true;

	public GameObject(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public void update() {
		if (state == State.IDLE)
			return;

		if (state == State.WALKING) {
			if (facingLeft) {
				velocity.set(-SPEED, velocity.y);
			} else {
				velocity.set(SPEED, velocity.y);
			}
		}

		position.x += velocity.x;
		position.y += velocity.y;

	}
	
}
