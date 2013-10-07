package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public class DeathTrigger extends PlayerTrigger {

	public DeathTrigger(Player player, Rectangle bounds) {
		super(player, bounds);
	}

	@Override
	public void trigger() {
		if (player.getPosition().y < getBounds().y + getBounds().height)
			player.getWorld().killPlayer();
	}
}
