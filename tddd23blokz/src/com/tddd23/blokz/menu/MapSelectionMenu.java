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
			addMenuItem(new AbstractMenuItem(map.getName() + "  -  Score: "
					+ map.getScore() + "  -  Time: " + map.getTimeInMillis()) {
				public void trigger() {
					game.startGame(map);
				}
			});
		}
		addMenuItem(new AbstractMenuItem("Empty item1") {
			public void trigger() {
			
			}
		});

		addMenuItem(new AbstractMenuItem("Empty item2") {
			public void trigger() {
			
			}
		});

		addMenuItem(new AbstractMenuItem("Empty item3") {
			public void trigger() {
			
			}
		});
		addMenuItem(new AbstractMenuItem("Empty item4") {
			public void trigger() {
			
			}
		});
		addMenuItem(new AbstractMenuItem("Empty item5") {
			public void trigger() {
			
			}
		});
		addMenuItem(new AbstractMenuItem("Empty item6") {
			public void trigger() {
			
			}
		});
		addMenuItem(new AbstractMenuItem("Empty item7") {
			public void trigger() {
			
			}
		});
		addMenuItem(new AbstractMenuItem("Empty item8") {
			public void trigger() {
			
			}
		});

		addMenuItem(new AbstractMenuItem("Empty item9") {
			public void trigger() {
			
			}
		});

		addMenuItem(new AbstractMenuItem("Empty item10") {
			public void trigger() {
			
			}
		});

	}

	@Override
	public String getTitle() {
		return "Select map";
		
	}
}
