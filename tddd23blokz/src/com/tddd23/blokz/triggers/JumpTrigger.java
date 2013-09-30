package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;

public class JumpTrigger extends PlayerTrigger{

	public JumpTrigger(Player player, Rectangle bounds) {
		super(player, bounds);
		
	}

	public void trigger() {
		player.jump(1);
	}

}
