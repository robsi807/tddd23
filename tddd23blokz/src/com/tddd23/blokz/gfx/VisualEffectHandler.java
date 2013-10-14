package com.tddd23.blokz.gfx;

import java.util.ArrayList;

import com.tddd23.blokz.world.World;

/**
 * @author Bobban
 * 
 */
public class VisualEffectHandler {

	private ArrayList<VisualEffect> visualEffects;
	private World world;

	private VisualEffect death;
	private boolean showingDeath;

	public VisualEffectHandler(World world) {
		this.world = world;
		this.showingDeath = false;
		visualEffects = new ArrayList<VisualEffect>();
		initEffects();
	}

	private void initEffects() {
		// first adding all the continous effects

		// wand effect
		visualEffects.add(new VisualEffect(world.getPlayer(),
				"visualeffects/wand.p", "images", 16, 17, true));

		for (VisualEffect v : visualEffects)
			v.start();

		death = new VisualEffect("visualeffects/death.p", "images", 0, 0, false);
		visualEffects.add(death);
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
