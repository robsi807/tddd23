package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 0.01f; // unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = Constants.SIZE; // half a unit

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	private World world;
	boolean facingLeft = true;
	private Rectangle displacementBox;
	private Block collidingBlock;
	private Rectangle collidingRect;

	public GameObject(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.world = world;
	}

	public void update() {
		acceleration.y = world.getGravity().y;

		if (state == State.IDLE) {
			velocity.set(0, velocity.y);
			return;
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

		// The rectangle that holds the position of the player next frame
		displacementBox = new Rectangle(world.getPlayer().position.x
				+ world.getPlayer().velocity.x, world.getPlayer().position.y
				+ world.getPlayer().velocity.y, world.getPlayer().bounds.width,
				world.getPlayer().bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);
		if (collidingBlock != null) {
			collidingRect = collidingBlock.getPositionRectangle();
			state = State.IDLE;

			Rectangle intersectingRectangle = getIntersectionRectangle(
					displacementBox, collidingRect);

			// <----------------------------- SYSOS

			System.out.println("Playerrectangle: X:"
					+ world.getPlayer().position.x + " Y:"
					+ world.getPlayer().position.y + " Width:"
					+ world.getPlayer().bounds.width + " Height:"
					+ world.getPlayer().bounds.height);
			System.out.println("Intersectrectangle: X:"
					+ intersectingRectangle.x + " Y:" + intersectingRectangle.y
					+ " Width:" + intersectingRectangle.width + " Height:"
					+ intersectingRectangle.height);

			// <----------------------------SYSOS

			// Om kollision sker p� x-led

			Vector2 undoMove = new Vector2();

			// if (Math.abs(intersectingRectangle.width) > Math
			// .abs(intersectingRectangle.height)) {

			undoMove.set(
					displacementBox.x - Math.abs(intersectingRectangle.width),
					displacementBox.y - Math.abs(intersectingRectangle.height));

			displacementBox = displacementBox.setPosition(undoMove);

			// Moves the player as close as possible to the colliding block
			// if (collidingRect.x > position.x) {
			// position.x += (intersectingRectangle.width);
			// }
			// if (collidingRect.x < position.x) {
			// position.x += (intersectingRectangle.width);
			// }
			// if (collidingRect.y < position.y) {
			// position.y += intersectingRectangle.height;
			// }
			// if (collidingRect.y > position.y) {
			// position.y += intersectingRectangle.height;
			// }

			position.set(velocity.x - Math.abs(intersectingRectangle.width), velocity.y - Math.abs(intersectingRectangle.height));
		} else {
			position.x += velocity.x;
			position.y += velocity.y;
		}

	}

	private Rectangle getIntersectionRectangle(Rectangle playerRect,
			Rectangle collidingRect) {
		Rectangle intersection = new Rectangle();
		intersection.x = Math.max(playerRect.x, collidingRect.x);
		intersection.width = Math.min((playerRect.x + playerRect.width),
				collidingRect.x + collidingRect.width) - intersection.x;
		intersection.y = Math.max(playerRect.y, collidingRect.y);
		intersection.height = Math.min(playerRect.y + playerRect.height,
				collidingRect.y + collidingRect.height) - intersection.y;
		if (world.getPlayer().position.x < collidingRect.x)
			intersection.width = -intersection.width;
		if (world.getPlayer().position.y < collidingRect.y)
			intersection.height = -intersection.height;
		return intersection;

	}

	private Block getCollidingBlock(Rectangle rect) {
		for (GameObject object : world.getDynamicObjects()) {
			if (object instanceof Player) {
				continue;
			}
			if (object.getPositionRectangle().overlaps(rect)) {
				return (Block) object;
			}
		}
		return null;
	}

	public Rectangle getPositionRectangle() {
		return new Rectangle(position.x, position.y, bounds.width,
				bounds.height);
	}

	/*
	 * Used for testing collision detection
	 */
	public void flipGravity() {
		world.flipGravity();
	}
}
