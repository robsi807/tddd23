package com.tddd23.blokz.menu;

import com.badlogic.gdx.Gdx;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.world.WorldManager;

public class OptionsMenu extends Menu {
	private WorldManager worldmanager;

	public OptionsMenu(final Blokz game, final WorldManager worldmanager) {
		super(game);
		this.worldmanager = worldmanager;

		addMenuItem(new AbstractMenuItem("Clear stats", true) {
			public void trigger() {
				worldmanager.resetStats();
				this.setTitle2("Stats cleared!");
			}
		});
		addMenuItem(new AbstractMenuItem((Gdx.graphics.isFullscreen()) ? "Set windowed mode": "Set fullscreen", true) {
			public void trigger() {
				game.switchScreenMode();
			}
		});
		
		
		addMenuItem(new AbstractMenuItem("Back", true) {
			public void trigger() {
				game.goToMainMenu();
			}
		});
	}

	public String getTitle() {
		return "Options";
	}

	@Override
	public void goBack() {
		game.setScreen(new MainMenu(game, worldmanager));
	}
}
