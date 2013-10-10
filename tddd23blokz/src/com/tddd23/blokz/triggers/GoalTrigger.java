package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.GameScreen.GameState;

public class GoalTrigger extends PlayerTrigger {

	public GoalTrigger(Player player, Rectangle bounds, GameScreen screen) {
		super(player, bounds,screen);
	}

	@Override
	public void trigger() {
		screen.setState(GameState.WAITING_FOR_NEXT_MAP);
	}
}
