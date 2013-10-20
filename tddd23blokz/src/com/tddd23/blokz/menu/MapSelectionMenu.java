package com.tddd23.blokz.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.Time;
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

	@Override
	public void goBack() {
		game.setScreen(new MainMenu(game, worldmanager));
	}

	@Override
	public void renderSpecial() {
		int nrOfClearedMaps = worldmanager.getAmountsOfFinishedMaps();
		int nrOfMaps = worldmanager.getMapInfo().size();
		Time t = worldmanager.getTotalTime();
		String timeStr = "Total time: " + t;
		String unlockString = "Finished maps: " + nrOfClearedMaps + "/"
				+ nrOfMaps;
		rectRenderer.begin(ShapeType.Filled);
		rectRenderer.setColor(Color.WHITE);
		rectRenderer.rect(775, 420, 480, 100);
		rectRenderer.end();
		rectRenderer.begin(ShapeType.Filled);
		rectRenderer.setColor(Color.BLACK);
		rectRenderer.rect(780, 425, 470, 90);
		rectRenderer.end();
		drawText(unlockString, 800, 500, 3, Color.WHITE);
		drawText(timeStr, 800, 460, 3, Color.WHITE);

	}
}
