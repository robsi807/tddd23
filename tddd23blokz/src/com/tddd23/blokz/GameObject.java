package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 0.1f; // unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.7f; // half a unit

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	private World world;
	boolean facingLeft = true;

	public GameObject(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.world = world;
	}

	public void update() {
//		acceleration.add(new Vector2(0,-0.01f));
//		velocity.set(velocity.x, velocity.y + acceleration.y);
		
		if (state == State.IDLE) {
			velocity.set(0,velocity.y);
		}

		if (state == State.WALKING) {
			if (facingLeft) {
				acceleration.set(-SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x < -0.5)
					velocity.set(-0.5f, velocity.y);

			} else {
				acceleration.set(SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x > 0.5)
					velocity.set(0.5f, velocity.y);
			}
		}

		position.x += velocity.x;
		position.y += velocity.y;

	}

}
