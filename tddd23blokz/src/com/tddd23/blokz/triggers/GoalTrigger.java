package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.GameScreen.GameState;

public class GoalTrigger extends PlayerTrigger {

	public GoalTrigger(Player player,int x, int y, GameScreen screen) {
		super(player, screen);
		bounds = new Rectangle((x * Constants.SIZE) - 1,
				(y * Constants.SIZE) - 1,
				Constants.SIZE + 2,
				Constants.SIZE + 2);
	}

	@Override
	public void trigger() {
		screen.setState(GameState.WAITING_FOR_NEXT_MAP);
	}
}
