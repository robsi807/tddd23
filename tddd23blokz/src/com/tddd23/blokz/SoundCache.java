package com.tddd23.blokz;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SoundCache {

	public static Sound jump;

	public static void load() {
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
	}
}