package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;

public class DeathTrigger extends PlayerTrigger {

	private Facing facing;

	public DeathTrigger(Player player, int x, int y, Facing facing,
			GameScreen screen) {
		super(player, screen);
		this.facing = facing;

		switch (facing) {
		case UP:
			bounds = new Rectangle(x * Constants.SIZE, y * Constants.SIZE
					+ Constants.SIZE, Constants.SIZE, 3);
			break;

		case DOWN:
			bounds = new Rectangle(x * Constants.SIZE, y * Constants.SIZE - 3,
					Constants.SIZE, 3);
			break;

		case RIGHT:
			bounds = new Rectangle(x * Constants.SIZE + Constants.SIZE, y
					* Constants.SIZE, 3, Constants.SIZE);
			break;

		case LEFT:
			bounds = new Rectangle(x * Constants.SIZE - 3, y * Constants.SIZE,
					3, Constants.SIZE);
			break;

		}

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

		case DOWN:
			if (!player.isStillInvertGravity()) { //FEL
				if (player.getPosition().y + player.getBounds().height <= bounds.y + 3
						&& player.getVelocity().y <= 0)
					player.getWorld().killPlayer();
			} else {
				if (player.getPosition().y + player.getBounds().height <= bounds.y + 3
						&& player.getVelocity().y > 0) {
					player.getWorld().killPlayer();
				}
			}
			break;

		case RIGHT:
			if (player.getPosition().y < (getBounds().y + getBounds().height)
					&& player.getVelocity().x < 0)
				player.getWorld().killPlayer();
			break;

		case LEFT:
			if (player.getPosition().y < (getBounds().y + getBounds().height)
					&& player.getVelocity().x > 0)
				player.getWorld().killPlayer();
			break;

		}

	}

	public enum Facing {
		UP, DOWN, LEFT, RIGHT;
	}
}
