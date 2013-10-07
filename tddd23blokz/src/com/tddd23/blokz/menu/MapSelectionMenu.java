package com.tddd23.blokz.menu;

import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.world.WorldManager;

public class MapSelectionMenu extends Menu {

	public MapSelectionMenu(final Blokz game, final WorldManager worldmanager) {
		super(game);

		for (final GameMap map : worldmanager.getMapInfo()) {
			addMenuItem(new AbstractMenuItem(map.getName() + "  -  Score: "
					+ map.getScore() + "  -  Time: " + map.getTimeInMillis()) {
				public void trigger() {
					game.startGame(map);
				}
			});
		}

		addMenuItem(new AbstractMenuItem("Back to main menu") {
			public void trigger() {
				game.setScreen(new MainMenu(game, worldmanager));
			}
		});
	}
}
