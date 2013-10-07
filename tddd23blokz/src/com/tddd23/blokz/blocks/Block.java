package com.tddd23.blokz.blocks;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.GameObject;
import com.tddd23.blokz.world.World;

public class Block extends GameObject {

	private BlockType type;

	public Block(Vector2 position, World world, BlockType type) {
		super(position, world);
		this.type = type;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public enum BlockType {
		DIRT, JUMP, SPIKE, GRAVITY, STONE;
	}

}
