package com.tddd23.blokz.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontHandler {
	
	public static BitmapFont courier25;

	public static void init(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/COUR.ttf"));
		courier25 = generator.generateFont(40); // font size 25 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
}
