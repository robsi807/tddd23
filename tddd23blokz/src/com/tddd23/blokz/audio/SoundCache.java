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
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
		place_block = Gdx.audio.newSound(Gdx.files
				.internal("sounds/place_block.mp3"));
		menu_beep = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep.mp3"));
		menu_beep_select = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep_select.mp3"));

		player_death = Gdx.audio.newSound(Gdx.files
				.internal("sounds/death.mp3"));
	}
}