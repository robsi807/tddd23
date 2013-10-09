package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public abstract class PlayerTrigger implements Triggerable {

	protected Player player;
	protected Rectangle bounds;
	protected boolean active;

	public PlayerTrigger(Player player, Rectangle bounds) {
		this.player = player;
		this.bounds = bounds;
		this.active = true;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
