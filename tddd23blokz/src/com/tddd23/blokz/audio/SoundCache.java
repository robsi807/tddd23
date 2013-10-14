package com.tddd23.blokz.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundCache {

	public static Sound jump;
	public static Sound place_block;
	public static Sound menu_beep;
	public static Sound menu_beep_select;
	public static Sound player_death;

	public static void load() {
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		place_block = Gdx.audio.newSound(Gdx.files
				.internal("sounds/place_block.wav"));
		menu_beep = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep.wav"));
		menu_beep_select = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep_select.wav"));

		player_death = Gdx.audio.newSound(Gdx.files
				.internal("sounds/death.wav"));
	}
}