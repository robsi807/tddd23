package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.world.World;

public abstract class BlockTrigger implements Triggerable{

	private World world;
	private Rectangle bounds;
	public BlockTrigger(Rectangle bounds, World world){
		this.world = world;
		this.bounds = bounds;
	}

	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}
}
