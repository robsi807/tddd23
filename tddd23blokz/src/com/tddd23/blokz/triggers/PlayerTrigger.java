package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;

public abstract class PlayerTrigger implements Triggerable {

	protected Player player;
	protected Rectangle bounds;
	protected boolean active;
	protected GameScreen screen;

	public PlayerTrigger(Player player, GameScreen screen) {
		this.player = player;
		this.screen = screen;
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
