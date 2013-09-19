package com.tddd23.blokz;

import javax.crypto.spec.PSource;

import sun.java2d.StateTrackable.State;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 0.005f; // unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = Constants.SIZE; // half a unit

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	int tick;

	private boolean hasCollidedX, hasCollidedY;
	protected boolean grounded;
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
		
		if(!(this instanceof Player))
			return;
		world.collisionRects.clear();
		tick++;
		if(tick % 60 == 0){
			System.out.println();
			System.out.println(tick /60);
			System.out.println("PlayerAcceleration: X: "+ acceleration.x+" Y: "+acceleration.y);
			System.out.println("PlayerVelocity: X: "+ velocity.x+" Y: "+velocity.y);
			System.out.println("PlayerPosition: X: "+ position.x+" Y: "+position.y);
			System.out.println("P� marken: "+grounded);
			
		}
		
		acceleration.y += world.getGravity().y;
		if (state == State.IDLE) {
			velocity.set(0,acceleration.y);
		}
		if(acceleration.y > Constants.MAX_FALLING_ACCELERATION)
			acceleration.y = Constants.MAX_FALLING_ACCELERATION;

		if (state == State.WALKING) {
			if (facingLeft) {
				acceleration.set(-SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x < -0.3)
					velocity.set(-0.3f, velocity.y);

			} else {
				acceleration.set(SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x > 0.3)
					velocity.set(0.3f, velocity.y);
			}
		}
		boolean hasCollidedX = false;
		boolean hasCollidedY = false;

		// Displacement in X
		displacementBox = new Rectangle(world.getPlayer().position.x
				+ world.getPlayer().velocity.x, world.getPlayer().position.y,
				world.getPlayer().bounds.width, world.getPlayer().bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);
		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedX = true;
			collidingRect = collidingBlock.getPositionRectangle();
			// state = State.IDLE;

			if (velocity.x > 0) {
				position.x = collidingBlock.position.x - bounds.width;

			} else {
				position.x = collidingBlock.position.x
						+ collidingBlock.bounds.width;
			}

		}

		// Displacement in Y
		displacementBox = new Rectangle(world.getPlayer().position.x,
				world.getPlayer().position.y + world.getPlayer().velocity.y,
				world.getPlayer().bounds.width, world.getPlayer().bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);

		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedY = true;
			collidingRect = collidingBlock.getPositionRectangle();
			// state = State.IDLE;
		
			if (velocity.y > 0){
				grounded = true;
				
			}else{
				acceleration.y = world.getGravity().y;
			}
			if (velocity.y < 0) {
				position.y = collidingBlock.position.y + bounds.height;

			} else {
				position.y = collidingBlock.position.y
						- collidingBlock.bounds.height;
			}

		}else{
			grounded = false;
		}

		if (hasCollidedX)
			velocity.x = 0;
		if (hasCollidedY)
			velocity.y = 0;


		position.add(velocity);
	}

	// private Rectangle getIntersectionRectangle(Rectangle playerRect,
	// Rectangle collidingRect) {
	//
	// Rectangle intersection = new Rectangle();
	//
	// intersection.x = Math.max(playerRect.x, collidingRect.x);
	// intersection.width = Math.min((playerRect.x + playerRect.width),
	// collidingRect.x + collidingRect.width) - intersection.x;
	// intersection.y = Math.max(playerRect.y, collidingRect.y);
	// intersection.height = Math.min(playerRect.y + playerRect.height,
	// collidingRect.y + collidingRect.height) - intersection.y;
	//
	// if (world.getPlayer().position.x < collidingRect.x)
	// intersection.width = -intersection.width;
	// if (world.getPlayer().position.y < collidingRect.y)
	// intersection.height = -intersection.height;
	// return intersection;
	//
	// }

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
