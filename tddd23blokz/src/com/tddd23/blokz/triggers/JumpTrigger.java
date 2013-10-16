package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.gfx.DebugWindow;

public class JumpTrigger extends PlayerTrigger {

	public JumpTrigger(Player player, int x, int y, GameScreen screen) {
		super(player, screen);

		bounds = new Rectangle((x * Constants.SIZE) + 1, (y * Constants.SIZE)
				+ Constants.SIZE, Constants.SIZE - 2, 3);
	}

	public void trigger() {
		if (player.getPosition().y >= getBounds().y)
			player.jump(2f);
	}

}
