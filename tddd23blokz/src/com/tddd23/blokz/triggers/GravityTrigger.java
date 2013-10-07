package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public class GravityTrigger extends PlayerTrigger {

	public GravityTrigger(Player player, Rectangle bounds) {
		super(player, bounds);
	}

	@Override
	public void trigger() {
		player.setInvertGravity(true);
	}

}
