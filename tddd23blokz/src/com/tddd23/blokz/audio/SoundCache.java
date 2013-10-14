package com.tddd23.blokz.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SoundCache {

	public static Sound jump;
	public static Sound place_block;
	public static Sound menu_beep_up;
	public static Sound menu_beep_down;
	public static Sound player_death;

	public static void load() {
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		place_block = Gdx.audio.newSound(Gdx.files
				.internal("sounds/place_block.wav"));
		menu_beep_up = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep_up.wav"));

		menu_beep_down = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_beep_down.wav"));

		player_death = Gdx.audio.newSound(Gdx.files
				.internal("sounds/death.wav"));
	}
}