package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.gfx.DebugWindow;

public class JumpTrigger extends PlayerTrigger {

	public JumpTrigger(Player player, Rectangle bounds) {
		super(player, bounds);

	}

	public void trigger() {
		if (player.getPosition().y >= getBounds().y)
			player.jump(1);
	}

}
