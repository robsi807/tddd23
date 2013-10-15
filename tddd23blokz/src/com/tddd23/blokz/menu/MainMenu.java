package com.tddd23.blokz.menu;

import com.badlogic.gdx.Game;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.world.WorldManager;

public class MainMenu extends Menu {
	
	public MainMenu(final Blokz game, final WorldManager worldmanager){
		super(game);
		
		
		addMenuItem(new AbstractMenuItem("Map selection",true) {
			public void trigger() {
				game.setScreen(new MapSelectionMenu(game, worldmanager));
			}
		});
		addMenuItem(new AbstractMenuItem("Credits",false) {
			public void trigger() {
				//Fin credits
			}
		});
		addMenuItem(new AbstractMenuItem("Exit",true) {
			public void trigger() {
				game.exitGame();
			}
		});
	}

	@Override
	public String getTitle() {
		return "Main menu";
	}

}
