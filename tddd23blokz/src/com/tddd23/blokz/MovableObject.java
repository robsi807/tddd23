package com.tddd23.blokz;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovableObject extends GameObject implements Movable {

	private TiledMapTileLayer blocks;
	private Block collidingBlock;

	public MovableObject(Vector2 position, World world) {
		super(position, world);
		blocks = (TiledMapTileLayer) world.getMap().getLayers().get("blocks");
	}

	@Override
	public void update(float delta) {
		world.collisionRects.clear();

		acceleration.y += world.getGravity().y;

		if (state == State.IDLE) {
			velocity.set(0, acceleration.y);
		}
		if (acceleration.y < -Constants.MAX_FALLING_SPEED)
			acceleration.y = -Constants.MAX_FALLING_SPEED;

		if (state == State.WALKING) {
			if (facingLeft) {
				acceleration.set(-SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x < -Constants.MAX_MOVING_SPEED)
					velocity.set(-Constants.MAX_MOVING_SPEED, velocity.y);

			} else {
				acceleration.set(SPEED, acceleration.y);
				velocity.set(velocity.x + acceleration.x, acceleration.y);
				if (velocity.x > Constants.MAX_MOVING_SPEED)
					velocity.set(Constants.MAX_MOVING_SPEED, velocity.y);
			}
		}

		boolean hasCollidedX = false;
		boolean hasCollidedY = false;

		// X axis collision handling
		displacementBox = new Rectangle(position.x + velocity.x, position.y,
				bounds.width, bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);
		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedX = true;
			// state = State.IDLE;
			if (velocity.x < 0) {
				position.x = collidingBlock.position.x + collidingBlock.bounds.width;

			} else {
				position.x = collidingBlock.position.x
						- collidingBlock.bounds.width;
			}

		}

		// Y axis collision handling
		displacementBox = new Rectangle(position.x, position.y + velocity.y,
				bounds.width, bounds.height);

		collidingBlock = getCollidingBlock(displacementBox);

		if (collidingBlock != null) {
			world.collisionRects.add(collidingBlock.getPositionRectangle());
			hasCollidedY = true;

			if (velocity.y < 0) {
				grounded = true;
				acceleration.y = 0;
			} else { // Collision with block above player,
				acceleration.y = 0;
			}

			if (velocity.y < 0) {
				position.y = collidingBlock.position.y
						+ collidingBlock.bounds.height;

			} else {
				position.y = collidingBlock.position.y - bounds.height;
			}
		} else {
			grounded = false;
		}

		if (hasCollidedX)
			velocity.x = 0;
		if (hasCollidedY)
			velocity.y = 0;

		position.add(velocity);

	}

	protected Block getCollidingBlock(Rectangle rect) {

		collidingBlock = null;

		// loop all the tiles
		for (int y = 0; y < world.getMapSize().height; y++) {
			for (int x = 0; x < world.getMapSize().width; x++) {

				// if a block exists and is solid
				if (blocks.getCell(x, y) != null
						&& blocks.getCell(x, y).getTile().getProperties()
								.containsKey("solid")) {
					// do they overlaps?
					collidingBlock = new Block(
							new Vector2(x * blocks.getTileWidth(), y
									* blocks.getTileHeight()), world);
					if (collidingBlock.getPositionRectangle().overlaps(rect))
						return collidingBlock;
				}

			}
		}
		return null;
	}
}
