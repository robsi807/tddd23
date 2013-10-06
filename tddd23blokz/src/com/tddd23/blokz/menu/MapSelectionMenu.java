package com.tddd23.blokz.menu;

import com.badlogic.gdx.Game;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.GameScreen;

public class MapSelectionMenu extends Menu {
	
	public MapSelectionMenu(final Blokz game){
		super(game);
		addMenuItem(new AbstractMenuItem("Level 1") {
			public void trigger() {
				game.startGame();
			}
		});
		addMenuItem(new AbstractMenuItem("Level 2") {
			public void trigger() {
				
			}
		});
		addMenuItem(new AbstractMenuItem("Level 3") {
			public void trigger() {
			}
		});
		addMenuItem(new AbstractMenuItem("Back to main menu") {
			public void trigger() {
				game.setScreen(new MainMenu(game));
			}
		});
	}
}
