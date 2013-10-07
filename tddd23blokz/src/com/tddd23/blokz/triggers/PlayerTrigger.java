package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public abstract class PlayerTrigger implements Triggerable {

	protected Player player;
	private Rectangle bounds;

	public PlayerTrigger(Player player, Rectangle bounds) {
		this.player = player;
		this.bounds = bounds;
	}

	@Override
	public abstract void trigger();

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
