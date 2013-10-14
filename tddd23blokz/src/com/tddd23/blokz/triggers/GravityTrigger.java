package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;

public class GravityTrigger extends PlayerTrigger {

	public GravityTrigger(int x, int y, int width, int height, Player player,
			GameScreen screen) {
		super(player, screen);
		bounds = new Rectangle(x, y, width, height);
	}

	@Override
	public void trigger() {
		player.setInvertGravity(true);
	}

}
