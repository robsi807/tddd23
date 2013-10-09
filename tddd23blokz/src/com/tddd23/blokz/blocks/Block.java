package com.tddd23.blokz.blocks;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.GameObject;
import com.tddd23.blokz.triggers.Triggerable;
import com.tddd23.blokz.world.World;

public class Block extends GameObject {

	private BlockType type;
	private int stateTime;
	private Triggerable connectedTrigger;

	public Block(Vector2 position, World world, BlockType type,
			Triggerable connectedTrigger) {
		super(position, world);
		this.connectedTrigger = connectedTrigger;
		this.type = type;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public void update(float delta) {
		switch (type) {
		case FIRE:

			break;
		default:
			break;
		}
	}

	public enum BlockType {
		DIRT, JUMP, SPIKE, GRAVITY, STONE, GOAL, FIRE;
	}

}
