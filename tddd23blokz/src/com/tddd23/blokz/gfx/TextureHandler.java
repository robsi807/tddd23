package com.tddd23.blokz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHandler {
	public static TextureRegion placed_Block;
	public static TextureRegion player_left_idle;
	public static TextureRegion player_right_idle;
	
	public static Animation player_left_animation;
	public static Animation player_right_animation;
	
	public static void init() {
		placed_Block = ImageCache.getTexture("placed_block");
		
		player_right_idle = ImageCache.getTexture("right_idle");
	
		player_left_idle = new TextureRegion(player_right_idle);
		player_left_idle.flip(true, false);

		TextureRegion[] walkRightFrames = new TextureRegion[3];
		for (int i = 0; i < walkRightFrames.length; i++) {
			walkRightFrames[i] = ImageCache.getTexture("right_running" + i);
		}
		player_right_animation = new Animation(.1f, walkRightFrames);

		TextureRegion[] walkLeftFrames = new TextureRegion[3];

		for (int i = 0; i < walkLeftFrames.length; i++) {
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		player_left_animation = new Animation(.1f, walkLeftFrames);
	}
}
