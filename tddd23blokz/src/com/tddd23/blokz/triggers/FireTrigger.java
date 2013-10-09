package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public class FireTrigger extends PlayerTrigger {

	public FireTrigger(Player player, Rectangle bounds) {
		super(player, bounds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void trigger() {
		player.getWorld().killPlayer();
	}
}
