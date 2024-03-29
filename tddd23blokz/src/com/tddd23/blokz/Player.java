package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.blocks.Block.BlockType;
import com.tddd23.blokz.gfx.DebugWindow;
import com.tddd23.blokz.world.World;

public class Player extends MovableObject {
	private State state = State.IDLE;

	private BlockType selectedBlockType = BlockType.STONE;

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	public Player(Vector2 position, float speed, World world) {
		super(position, speed, world);

		getBounds().height = 28f;
		getBounds().width = 13;
		setMovable(true);
	}

	public void jump(float multiplier) {

		if (grounded) {
			if (!isInvertGravity()) {
				getAcceleration().y = Constants.JUMPING_SPEED * multiplier;
			} else {
				getAcceleration().y = -Constants.JUMPING_SPEED * multiplier;
			}
			SoundCache.jump.play(SoundCache.getVolume());
		}
	}

	public void jump() {
		jump(1f);
	}

	public void addGravity(float delta) {
		if (!isInvertGravity()) {
			getAcceleration().y += world.getGravity().y;
		} else {
			getAcceleration().y -= world.getGravity().y;
		}
	}

	@Override
	public void updateObject(float delta) {
		if (state == State.IDLE) {
			getVelocity().set(0, getAcceleration().y);
		}

		if (!isInvertGravity()) {
			if (getAcceleration().y < -Constants.MAX_FALLING_SPEED)
				getAcceleration().y = -Constants.MAX_FALLING_SPEED;
		} else {
			if (getAcceleration().y > Constants.MAX_FALLING_SPEED)
				getAcceleration().y = Constants.MAX_FALLING_SPEED;
		}

		if (state == State.WALKING) {
			if (facingLeft) {
				getVelocity().set(-getSpeed(), getAcceleration().y);
				if (getVelocity().x < -Constants.MAX_MOVING_SPEED)
					getVelocity().set(-Constants.MAX_MOVING_SPEED,
							getVelocity().y);

			} else {
				getVelocity().set(getSpeed(), getAcceleration().y);
				if (getVelocity().x > Constants.MAX_MOVING_SPEED)
					getVelocity().set(Constants.MAX_MOVING_SPEED,
							getVelocity().y);
			}
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public BlockType getSelectedBlockType() {
		return selectedBlockType;
	}

	public void setSelectedBlockType(BlockType selectedBlockType) {
		this.selectedBlockType = selectedBlockType;
	}

}
