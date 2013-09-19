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


	private boolean hasCollidedX, hasCollidedY;
	protected boolean grounded;
	protected World world;
	boolean facingLeft = true;
	protected Rectangle displacementBox;
	protected Block collidingBlock;
	protected Rectangle collidingRect;

	public GameObject(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.world = world;
	}

	public void update() {
		
	}

	protected Block getCollidingBlock(Rectangle rect) {
		for (GameObject object : world.getDynamicObjects()) {
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
	
	private int tick;
	private void playerPositionDebugText(){
		tick++;
		if(tick % 60 == 0){
			System.out.println();
			System.out.println(tick /60);
			System.out.println("PlayerAcceleration: X: "+ acceleration.x+" Y: "+acceleration.y);
			System.out.println("PlayerVelocity: X: "+ velocity.x+" Y: "+velocity.y);
			System.out.println("PlayerPosition: X: "+ position.x+" Y: "+position.y);
			System.out.println("På marken: "+grounded);
			
		}
	}

	/*
	 * Used for testing collision detection
	 */
}
