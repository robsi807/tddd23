package com.tddd23.blokz;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.blocks.Block;

public class GameObject {
	

	
	

	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private Rectangle bounds = new Rectangle();
	

	protected boolean grounded;
	protected World world;
	boolean facingLeft = true;
	protected Rectangle displacementBox;
	protected Block collidingBlock;
	protected Rectangle collidingRect;

	public GameObject(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = Constants.SIZE;
		this.bounds.width = Constants.SIZE;
		this.world = world;
	}

	public Rectangle getPositionRectangle() {
		return new Rectangle(position.x, position.y, bounds.width,
				bounds.height);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
