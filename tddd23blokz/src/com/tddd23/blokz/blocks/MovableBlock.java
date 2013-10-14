package com.tddd23.blokz.blocks;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Movable;
import com.tddd23.blokz.MovableObject;
import com.tddd23.blokz.world.World;

public class MovableBlock extends MovableObject implements Movable {

	public MovableBlock(Vector2 position, float speed, World world) {
		super(position, speed, world);
	}

	@Override
	public void addGravity(float delta) {

	}

	@Override
	public void updateObject(float delta) {
		
	}
}
