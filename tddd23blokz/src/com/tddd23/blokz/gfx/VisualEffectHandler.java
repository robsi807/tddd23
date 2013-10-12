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

		death = new VisualEffect(world.getPlayer(), "visualeffects/death.p",
				"images", 8, 14, false);
		visualEffects.add(death);
	}

	public void showDeath() {
		if (death.getEffect().isComplete())
			world.resetMap();

		if (!showingDeath) {
			world.getPlayer().hide();
			death.start();
			showingDeath = true;
		}

	}

	public ArrayList<VisualEffect> getVisualEffects() {
		return visualEffects;
	}

}
