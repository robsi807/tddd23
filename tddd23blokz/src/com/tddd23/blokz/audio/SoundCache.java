package com.tddd23.blokz.audio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SoundCache {

	public static Sound jump;
	public static Sound place_block;

	public static void load() {
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		place_block = Gdx.audio.newSound(Gdx.files.internal("sounds/place_block.wav"));
	}
}