package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.GameObject.State;

public class Player extends GameObject {

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
	
	public void update(){
		playerPositionDebugText();
		world.collisionRects.clear();
		
		acceleration.y += world.getGravity().y;
		if (state == State.IDLE) {
			velocity.set(0,acceleration.y);
		}
		if(acceleration.y > Constants.MAX_FALLING_SPEED)
			acceleration.y = Constants.MAX_FALLING_SPEED;

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

		// X axis collision handling
		displacementBox = new Rectangle(position.x
				+ velocity.x, position.y,
				bounds.width, bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);
		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedX = true;
			// state = State.IDLE;
			if (velocity.x > 0) {
				position.x = collidingBlock.position.x - bounds.width;

			} else {
				position.x = collidingBlock.position.x
						+ collidingBlock.bounds.width;
			}

		}

		// Y axis collision handling
		displacementBox = new Rectangle(position.x,
				position.y + velocity.y,
				bounds.width, bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);

		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedY = true;
	
		
			if (velocity.y > 0){
				grounded = true;
				acceleration.y = Constants.MAX_FALLING_SPEED;
//				System.out.println(acceleration.y);
			}else{ //Collision with block above player, 
				acceleration.y =0;
			}
			
			if (velocity.y < 0) {
				position.y = collidingBlock.position.y + collidingBlock.bounds.height;
				
			} else {
				position.y = collidingBlock.position.y
						- bounds.height;
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

}
