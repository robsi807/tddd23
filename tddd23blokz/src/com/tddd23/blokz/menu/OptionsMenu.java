package com.tddd23.blokz.menu;

import com.badlogic.gdx.Gdx;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.audio.MusicCache;
import com.tddd23.blokz.audio.SoundCache;
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
		addMenuItem(new AbstractMenuItem(
				(Gdx.graphics.isFullscreen()) ? "Set windowed mode"
						: "Set fullscreen", true) {
			public void trigger() {
				game.switchScreenMode();
				this.setTitle(Gdx.graphics.isFullscreen() ? "Set windowed mode"
						: "Set fullscreen");
			}
		});
		addMenuItem(new AbstractMenuItem("Mute sound", ""
				+ SoundCache.getMuteString(), true) {
			public void trigger() {
				SoundCache.setMuteUnmute();
				this.setTitle2(SoundCache.getMuteString());
			}
		});
		addMenuItem(new AbstractMenuItem("Mute music", ""
				+ MusicCache.getMuteString(), true) {
			public void trigger() {
				MusicCache.setMuteUnmute();
				this.setTitle2(MusicCache.getMuteString());
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

	@Override
	public void renderSpecial() {
		// TODO Auto-generated method stub
		
	}
}
