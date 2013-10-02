package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.triggers.Triggerable;

public abstract class MovableObject extends GameObject implements Movable {

	private Rectangle collidingRectangle;
	private boolean movable = true;
	private float stateTime = 0;
	private float speed;

	private MinMax relevantCoords;

	public MovableObject(Vector2 position, float speed, World world) {
		super(position, world);
		this.speed = speed;
		relevantCoords = new MinMax();
	}

	@Override
	public void update(float delta) {

		updateObject();

		stateTime += delta;
		if (!movable)
			return;

		addGravity();
		updateObject();

		boolean hasCollidedX = false;
		boolean hasCollidedY = false;

		// X axis collision handling
		displacementBox = new Rectangle(getPosition().x + getVelocity().x,
				getPosition().y, getBounds().width, getBounds().height);

		collidingRectangle = getCollidingBlock(displacementBox);
		if (collidingRectangle != null) {
			hasCollidedX = true;
			if (getVelocity().x < 0) {
				getPosition().x = collidingRectangle.x
						+ collidingRectangle.width;

			} else {
				getPosition().x = collidingRectangle.x - getBounds().width;
			}

		}

		// Y axis collision handling
		displacementBox = new Rectangle(getPosition().x, getPosition().y
				+ getVelocity().y, getBounds().width, getBounds().height);

		if (checkForTriggers(displacementBox))
			return;
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

	private boolean checkForTriggers(Rectangle displacementRectangle) {
		for (Triggerable trigger : world.getTriggers()) {
			if (trigger.getBounds().overlaps(displacementRectangle)) {
				// Only trigger when player is STANDING on a trigger
				if (world.getPlayer().getPosition().y < trigger.getBounds().y
						+ trigger.getBounds().height)
					return false;
				trigger.trigger();
				return true;
			}
		}
		return false;
	}

	public float getStateTime() {
		return stateTime;
	}

	protected Rectangle getCollidingBlock(Rectangle rect) {

		collidingRectangle = null;
		if (world != null) {
			relevantCoords.setRelevantCoordinates(3, getPosition(), world);

			for (int y = relevantCoords.minY; y < relevantCoords.maxY; y++) {
				for (int x = relevantCoords.minX; x < relevantCoords.maxX; x++) {

					if (world.getBlocks()[x][y] != null) {
						collidingRectangle = world.getBlocks()[x][y]
								.getPositionRectangle();
						// do they overlaps?
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

		}
		return null;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public float getSpeed() {
		return speed;
	}

	public abstract void addGravity();

	public abstract void updateObject();
}
