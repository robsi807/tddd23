package com.tddd23.blokz.blocks;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.GameObject;
import com.tddd23.blokz.triggers.DeathTrigger.Facing;
import com.tddd23.blokz.triggers.FireTrigger;
import com.tddd23.blokz.triggers.PlayerTrigger;
import com.tddd23.blokz.world.World;

public class Block extends GameObject {

	private BlockType type;
	private float stateTime;
	private ArrayList<PlayerTrigger> connectedTriggers;
	private static int FLAME_REPEAT = 3;
	private static int FLAME_LENGTH = 1;
	private Facing facing;

	public Block(Vector2 position, World world, BlockType type) {
		super(position, world);
		this.connectedTriggers = new ArrayList<PlayerTrigger>();
		this.type = type;
	}

	public Block(Vector2 position, World world, BlockType type, Facing facing) {
		super(position, world);
		this.connectedTriggers = new ArrayList<PlayerTrigger>();
		this.type = type;
		this.facing = facing;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public void update(float delta) {
		stateTime = world.getStateTime() % (FLAME_LENGTH + FLAME_REPEAT);
		switch (type) {
		case FIRE:
			if (stateTime > FLAME_REPEAT
					&& stateTime < FLAME_LENGTH + FLAME_REPEAT) {
				for (PlayerTrigger t : connectedTriggers){
					t.setActive(true);
					((FireTrigger)t).getEffect().start();
				}

			} else {
				for (PlayerTrigger t : connectedTriggers){
					t.setActive(false);
					((FireTrigger)t).getEffect().stop();
				}
			}
			if (stateTime > FLAME_LENGTH + FLAME_REPEAT)
				stateTime = 0;
			break;
		default:
			break;
		}
	}

	public enum BlockType {
		DIRT, JUMP, GRAVITY, SPIKE, STONE, GOAL, FIRE;
	}

	public void addTrigger(PlayerTrigger trigger) {
		connectedTriggers.add(trigger);
	}

	public Facing getFacing() {
		return facing;
	}

	public void addTrigger(ArrayList<PlayerTrigger> triggerList) {
		for (PlayerTrigger t : triggerList)
			connectedTriggers.add(t);
	}

	public ArrayList<PlayerTrigger> getConnectedTriggers() {
		return connectedTriggers;
	}

}
