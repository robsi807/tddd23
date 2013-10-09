package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.gfx.DebugWindow;

public class JumpTrigger extends PlayerTrigger {

	public JumpTrigger(Player player, Rectangle bounds) {
		super(player, bounds);

	}

	public void trigger() {
		DebugWindow.addText(player.getPosition().y + " hoppa!"); // Ultimate bug
																	// of death,
																	// tas denna
																	// bort (aka
																	// vi gör
																	// INTE ett
																	// tidskrävande
																	// anrop
																	// innan
																	// jump) så
																	// hoppar
																	// han
																	// endast
																	// 3-5 ggr
																	// på ett
																	// jumpblock
		player.jump(1);
	}

}
