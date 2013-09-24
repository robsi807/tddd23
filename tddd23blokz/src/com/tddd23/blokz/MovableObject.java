package com.tddd23.blokz;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovableObject extends GameObject implements Movable {

	private TiledMapTileLayer blocks;
	private Rectangle collidingRectangle;
	private boolean movable = true;;

	public MovableObject(Vector2 position, World world) {
		super(position, world);
		blocks = (TiledMapTileLayer) world.getMap().getLayers().get("blocks");
		System.out.println(blocks);
	}

	@Override
	public void update(float delta) {
		if (!movable || !(this instanceof Player))
			return;
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

		collidingRectangle = getCollidingBlock(displacementBox);
		if (collidingRectangle != null) {
			hasCollidedX = true;
			// state = State.IDLE;
			if (velocity.x < 0) {
				position.x = collidingRectangle.x
						+ collidingRectangle.width;

			} else {
				position.x = collidingRectangle.x
						- bounds.width;
			}

		}

		// Y axis collision handling
		displacementBox = new Rectangle(position.x, position.y + velocity.y,
				bounds.width, bounds.height);

		collidingRectangle = getCollidingBlock(displacementBox);

		if (collidingRectangle != null) {
			hasCollidedY = true;

			if (velocity.y < 0) {
				grounded = true;
				acceleration.y = 0;
			} else { // Collision with block above player,
				acceleration.y = 0;
			}

			if (velocity.y < 0) {
				position.y = collidingRectangle.y
						+ collidingRectangle.height;

			} else {
				position.y = collidingRectangle.y - bounds.height;
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

	protected Rectangle getCollidingBlock(Rectangle rect) {

		collidingRectangle = null;

		// loop all the tiles
		for (int y = 0; y < world.getMapSize().height; y++) {
			for (int x = 0; x < world.getMapSize().width; x++) {

				// if a block exists and is solid
				if (blocks.getCell(x, y) != null
						&& blocks.getCell(x, y).getTile().getProperties()
								.containsKey("solid")) {
					// do they overlaps?
					collidingRectangle = new Rectangle(x*blocks.getTileWidth(),y*blocks.getTileHeight(),
							blocks.getTileWidth(), blocks.getTileHeight());
					if (collidingRectangle.overlaps(rect))
						return collidingRectangle;
				}

			}
		}

		for (GameObject object : world.getDynamicObjects()) {
			if (object instanceof Player) {
				continue;
			}
			if (object.getPositionRectangle().overlaps(rect)) {
				return object.getPositionRectangle();
			}
		}
		return null;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}
