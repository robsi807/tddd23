package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;

public class DeathTrigger extends PlayerTrigger {

	private Facing facing;

	public DeathTrigger(Player player, int x, int y, Facing direction,
			GameScreen screen) {
		super(player, screen);
		this.facing = direction;
		bounds = new Rectangle((x * Constants.SIZE) + 1, (y * Constants.SIZE)
				+ Constants.SIZE, Constants.SIZE - 2, 3);
	}

	@Override
	public void trigger() {
		switch (facing) {
		case UP:
			if (!player.isInvertGravity()) {
				if (player.getPosition().y >= bounds.y
						&& player.getVelocity().y < 0)
					player.getWorld().killPlayer();
			} else {
				if (player.getPosition().y < bounds.y
						&& player.getVelocity().y > 0)
					player.getWorld().killPlayer();
			}
			break;

		}

	}

	public enum Facing {
		UP, DOWN, LEFT, RIGHT;
	}
}
