package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;

public class DeathTrigger extends PlayerTrigger {

	public DeathTrigger(Player player, Rectangle bounds, GameScreen screen) {
		super(player, bounds, screen);
	}

	@Override
	public void trigger() {
		if (player.getPosition().y >= getBounds().y)
			player.getWorld().killPlayer();

	}

	public enum Facing {
		UP, DOWN, LEFT, RIGHT;
	}
}
