package com.tddd23.blokz.triggers;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.gfx.VisualEffect;
import com.tddd23.blokz.triggers.DeathTrigger.Facing;

public class FireTrigger extends PlayerTrigger {

	private Facing facing;

	private VisualEffect effect;

	public FireTrigger(Player player, int x, int y, Facing direction,
			GameScreen screen) {
		super(player, screen);
		facing = direction;
		switch (direction) {
		case UP:
			bounds = new Rectangle(x * Constants.SIZE,
					(y * Constants.SIZE + Constants.SIZE), Constants.SIZE,
					Constants.SIZE * 2);
			break;
		case DOWN:
			bounds = new Rectangle(x * Constants.SIZE,
					((y - 2) * Constants.SIZE), Constants.SIZE,
					Constants.SIZE * 2);
			break;
		case RIGHT:
			bounds = new Rectangle((x + 1) * Constants.SIZE,
					(y * Constants.SIZE), Constants.SIZE * 2, Constants.SIZE);
			break;
		case LEFT:
			bounds = new Rectangle((x * Constants.SIZE - Constants.SIZE * 2),
					(y * Constants.SIZE), Constants.SIZE * 2, Constants.SIZE);
			break;
		}
	}

	@Override
	public void trigger() {
		player.getWorld().killPlayer();
	}

	public Facing getFacing() {
		return facing;
	}

	public VisualEffect getEffect() {
		return effect;
	}

	public void setEffect(VisualEffect effect) {
		this.effect = effect;
	}

}
