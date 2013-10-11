package com.tddd23.blokz.audio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicCache {

	public static Music level1;

	public static void load() {
		level1 = Gdx.audio.newMusic(Gdx.files.internal("music/maintheme.mp3"));
	}
}