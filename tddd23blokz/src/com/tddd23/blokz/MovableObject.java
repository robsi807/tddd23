package com.tddd23.blokz;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovableObject extends GameObject implements Movable {

	private TiledMapTileLayer blocks;
	private Rectangle collidingRectangle;
	private boolean movable = true;
	private float stateTime =0;

	public MovableObject(Vector2 position, World world) {
		super(position, world);
		blocks = (TiledMapTileLayer) world.getMap().getLayers().get("blocks");
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
		if (!movable)
			return;
		
		world.collisionRects.clear();

		getAcceleration().y += world.getGravity().y;

		if (state == State.IDLE) {
			getVelocity().set(0, getAcceleration().y);
		}
		if (getAcceleration().y < -Constants.MAX_FALLING_SPEED)
			getAcceleration().y = -Constants.MAX_FALLING_SPEED;

		if (state == State.WALKING) {
			if (facingLeft) {
				getAcceleration().set(-SPEED, getAcceleration().y);
				getVelocity().set(getVelocity().x + getAcceleration().x, getAcceleration().y);
				if (getVelocity().x < -Constants.MAX_MOVING_SPEED)
					getVelocity().set(-Constants.MAX_MOVING_SPEED, getVelocity().y);

			} else {
				getAcceleration().set(SPEED, getAcceleration().y);
				getVelocity().set(getVelocity().x + getAcceleration().x, getAcceleration().y);
				if (getVelocity().x > Constants.MAX_MOVING_SPEED)
					getVelocity().set(Constants.MAX_MOVING_SPEED, getVelocity().y);
			}
		}

		boolean hasCollidedX = false;
		boolean hasCollidedY = false;

		// X axis collision handling
		displacementBox = new Rectangle(getPosition().x + getVelocity().x, getPosition().y,
				getBounds().width, getBounds().height);

		collidingRectangle = getCollidingBlock(displacementBox);
		if (collidingRectangle != null) {
			hasCollidedX = true;
			if (getVelocity().x < 0) {
				getPosition().x = collidingRectangle.x
						+ collidingRectangle.width;

			} else {
				getPosition().x = collidingRectangle.x
						- getBounds().width;
			}

		}

		// Y axis collision handling
		displacementBox = new Rectangle(getPosition().x, getPosition().y + getVelocity().y,
				getBounds().width, getBounds().height);

		collidingRectangle = getCollidingBlock(displacementBox);

		if (collidingRectangle != null) {
			hasCollidedY = true;

			if (getVelocity().y < 0) {
				grounded = true;
				getAcceleration().y = 0;
			} else { // Collision with block above player,
				getAcceleration().y = 0;
			}

			if (getVelocity().y < 0) {
				getPosition().y = collidingRectangle.y
						+ collidingRectangle.height;

			} else {
				getPosition().y = collidingRectangle.y - getBounds().height;
			}
		} else {
			grounded = false;
		}

		if (hasCollidedX)
			getVelocity().x = 0;
		if (hasCollidedY)
			getVelocity().y = 0;
		getPosition().add(getVelocity());

	}

	public float getStateTime() {
		return stateTime;
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
