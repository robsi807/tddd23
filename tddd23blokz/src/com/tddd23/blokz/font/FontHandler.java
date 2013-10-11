package com.tddd23.blokz.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontHandler {
	
	public static BitmapFont[] courier;

	public static void init(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/COUR.TTF"));
		courier = new BitmapFont[20];
		for(int x=0;x<20;x++)
			courier[x] = generator.generateFont((x+1)*10); 
		generator.dispose(); 
	}
}
