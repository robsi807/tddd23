package com.tddd23.blokz.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicCache {

	public static Music level1;
	public static boolean muted = false;

	public static void load() {
		level1 = Gdx.audio.newMusic(Gdx.files.internal("music/caves.mp3"));
	}

	public static void muteAndUnmute() {
		if (!muted) {
			muted = true;
			level1.setVolume(0);
		} else {
			muted = false;
			level1.setVolume(getVolume());
		}
	}

	public static float getVolume() {
		return (muted) ? 0 : .2f;
	}

	public static void setMuteUnmute() {
		if (muted) {
			MusicCache.muted = false;
		} else {
			MusicCache.muted = true;
		}
	}

	public static String getMuteString() {
		return (muted) ? "Muted" : "";
	}
}