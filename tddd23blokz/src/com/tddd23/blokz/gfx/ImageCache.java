package com.tddd23.blokz.gfx;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageCache {

	public static Texture sheet;
	public static TextureAtlas atlas;

	public static void load() {
		String textureFile = "images/SpriteSheet.txt";
		atlas = new TextureAtlas(textureFile);
	}

	public static TextureRegion getTexture(String name) {
		return atlas.findRegion(name);
	}

	public static TextureRegion getFrame(String name, int index) {
		return atlas.findRegion(name, index);
	}
}