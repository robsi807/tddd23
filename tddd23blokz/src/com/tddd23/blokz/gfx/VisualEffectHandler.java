package com.tddd23.blokz.gfx;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.audio.SoundCache;
import com.tddd23.blokz.blocks.Block.BlockType;
import com.tddd23.blokz.triggers.FireTrigger;
import com.tddd23.blokz.triggers.PlayerTrigger;
import com.tddd23.blokz.world.World;

/**
 * @author Bobban
 * 
 */
public class VisualEffectHandler {

	private ArrayList<VisualEffect> visualEffects;
	private World world;

	private VisualEffect death;
	private ArrayList<VisualEffect> fire;

	private boolean showingDeath;

	public VisualEffectHandler(World world) {
		this.world = world;
		this.showingDeath = false;
		visualEffects = new ArrayList<VisualEffect>();
		fire = new ArrayList<VisualEffect>();
		initEffects();
	}

	private void initEffects() {
		// first adding all the continous effects

		// wand effect
		visualEffects.add(new VisualEffect(world.getPlayer(),
				"visualeffects/wand.p", "images", 16, 17, true));

		for (VisualEffect v : visualEffects)
			v.start();
		loadFire();
		death = new VisualEffect("visualeffects/death.p", "images", 0, 0, false);
		visualEffects.add(death);
	}

	private void loadFire() {
		VisualEffect addFire = null;
		Rectangle rect = null;
		for (int y = 0; y < world.getBlocks()[0].length; y++) {
			for (int x = 0; x < world.getBlocks().length; x++) {
				if (world.getBlocks()[x][y] != null
						&& world.getBlocks()[x][y].getType() == BlockType.FIRE) {

					// bot
					rect = world.getBlocks()[x][y].getConnectedTriggers()
							.get(3).getBounds();

					addFire = new VisualEffect("visualeffects/fire.p",
							"images", (int) rect.x + (int) rect.width / 2,
							(int) rect.y + (int) rect.height, false);
					addFire.getEffect().flipY();
					visualEffects.add(addFire);
					((FireTrigger) world.getBlocks()[x][y]
							.getConnectedTriggers().get(3)).setEffect(addFire);

					// top
					rect = world.getBlocks()[x][y].getConnectedTriggers()
							.get(2).getBounds();
					addFire = new VisualEffect("visualeffects/fire.p",
							"images", (int) rect.x + (int) rect.width / 2,
							(int) rect.y, false);
					visualEffects.add(addFire);
					((FireTrigger) world.getBlocks()[x][y]
							.getConnectedTriggers().get(2)).setEffect(addFire);

					// right
					rect = world.getBlocks()[x][y].getConnectedTriggers()
							.get(1).getBounds();
					addFire = new VisualEffect("visualeffects/fire2.p",
							"images", (int) rect.x, (int) rect.y
									+ (int) rect.height / 2, false);
					visualEffects.add(addFire);
					((FireTrigger) world.getBlocks()[x][y]
							.getConnectedTriggers().get(1)).setEffect(addFire);

					rect = world.getBlocks()[x][y].getConnectedTriggers()
							.get(0).getBounds();
					addFire = new VisualEffect("visualeffects/fire3.p",
							"images", (int) rect.x + (int) rect.width,
							(int) rect.y + (int) rect.height / 2, false);
					visualEffects.add(addFire);
					((FireTrigger) world.getBlocks()[x][y]
							.getConnectedTriggers().get(0)).setEffect(addFire);

				}
			}
		}
	}

	public void showDeath() {

		if (!showingDeath) {

			death.getEffect().setPosition(
					world.getPlayer().getPosition().x
							+ world.getPlayer().getBounds().width / 2,
					world.getPlayer().getPosition().y
							+ world.getPlayer().getBounds().width / 2);
			world.getPlayer().hide();
			death.start();
			SoundCache.player_death.play();
			showingDeath = true;
		}

	}

	public ArrayList<VisualEffect> getVisualEffects() {
		return visualEffects;
	}

	public boolean isShowingDeath() {
		return showingDeath;
	}

}
