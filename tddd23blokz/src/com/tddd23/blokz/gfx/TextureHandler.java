package com.tddd23.blokz.gfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHandler {

	private static Texture player;

	public static TextureRegion placed_Block;
	public static TextureRegion player_left_idle;
	public static TextureRegion player_right_idle;

	public static TextureRegion player_jump_right;
	public static TextureRegion player_jump_left;

	public static Animation player_left_animation;
	public static Animation player_right_animation;

	public static void init() { // varf�r �r denna static?
		placed_Block = ImageCache.getTexture("placed_block");
		player = new Texture("images/playersheet.png");

		// player_right_idle = ImageCache.getTexture("right_idle");
		player_right_idle = new TextureRegion(player, 0, 0, 16, 28);

		player_left_idle = new TextureRegion(player, 0, 0, 16, 28);
		player_left_idle.flip(true, false);

		// the walk animation

		TextureRegion[] walkRightFrames = new TextureRegion[3];
		for (int i = 0; i < walkRightFrames.length; i++) {
			walkRightFrames[i] = new TextureRegion(player, i * 16, 0, 16, 28);
			// walkRightFrames[i] = ImageCache.getTexture("right_running" + i);
		}
		player_right_animation = new Animation(.1f, walkRightFrames);

		TextureRegion[] walkLeftFrames = new TextureRegion[3];

		for (int i = 0; i < walkLeftFrames.length; i++) {
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		player_left_animation = new Animation(.1f, walkLeftFrames);

		// jump frame
		player_jump_left = new TextureRegion(player, 3 * 16, 0, 16, 32);

		player_jump_right = new TextureRegion(player, 3 * 16, 0, 16, 32);
		player_jump_right.flip(true, false);

	}
}
