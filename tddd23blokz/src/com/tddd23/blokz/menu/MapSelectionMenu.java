package com.tddd23.blokz.menu;

import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.world.WorldManager;

public class MapSelectionMenu extends Menu {
	private WorldManager worldmanager;

	public MapSelectionMenu(final Blokz game, final WorldManager worldmanager) {
		super(game);
		this.worldmanager = worldmanager;

		for (final GameMap map : worldmanager.getMapInfo()) {
			addMenuItem(new AbstractMenuItem(map.getName(),
					map.getTimeString(), map.isMapUnlocked()) {
				public void trigger() {
					game.startGame(map);
				}
			});
		}

		addMenuItem(new AbstractMenuItem("Back", true) {
			public void trigger() {
				game.goToMainMenu();
			}
		});
	}

	public String getTitle() {
		return "Select map";
	}
}
