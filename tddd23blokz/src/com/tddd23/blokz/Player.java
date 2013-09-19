package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.GameObject.State;

public class Player extends GameObject {

	public Player(Vector2 position, World world) {
		super(position, world);
	}

	public void jump() {
		if (grounded) {
			acceleration.add(new Vector2(0, -0.5f));
		}
	}
	
	public void update(){
//		playerPositionDebugText()
		world.collisionRects.clear();
		
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

		// X axis collision handling
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

		// Y axis collision handling
		displacementBox = new Rectangle(world.getPlayer().position.x,
				world.getPlayer().position.y + world.getPlayer().velocity.y,
				world.getPlayer().bounds.width, world.getPlayer().bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);

		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedY = true;
			collidingRect = collidingBlock.getPositionRectangle();
		
			if (velocity.y > 0){
				grounded = true;
			}else{ //Collision with block above player, 
				acceleration.y =0;
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

}
