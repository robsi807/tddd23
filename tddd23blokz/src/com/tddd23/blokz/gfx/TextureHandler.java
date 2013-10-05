package com.tddd23.blokz.gfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHandler {

	private static Texture player;
	private static Texture blocks;
	public static Texture paused;

	// player graphics
	public static Animation player_left_idle;
	public static Animation player_right_idle;

	public static TextureRegion player_jump_right;
	public static TextureRegion player_jump_left;
	
	public static TextureRegion player_falling_right;
	public static TextureRegion player_falling_left;

	public static Animation player_left_animation;
	public static Animation player_right_animation;

	public static TextureRegion block_dirt;
	public static TextureRegion block_indestructable;
	public static TextureRegion block_jump;
	


	private static TextureRegion[] frameCollection;
	private static TextureRegion[] frameCollection2;

	public static void init() { // varför är denna static?
		// placed_Block = ImageCache.getTexture("placed_block");

		initPlayer();
		initBlocks();
		initOther();

	}

	private static void initOther() {
		paused = new Texture("images/paused.png");
		
	}

	private static void initPlayer() {
		player = new Texture("images/playersheet2.png");

		// player_right_idle = ImageCache.getTexture("right_idle");

		// player_right_idle = new TextureRegion(player, 0, 0, 16, 28);
		// player_left_idle = new TextureRegion(player, 0, 0, 16, 28);
		// player_left_idle.flip(true, false);

		// the idle animation
		frameCollection = new TextureRegion[3];
		frameCollection2 = new TextureRegion[3];
		for (int i = 0; i < frameCollection.length; i++) {
			frameCollection[i] = new TextureRegion(player, i * 16, 0, 16, 28);
			frameCollection2[i] = new TextureRegion(frameCollection[i]);
			frameCollection2[i].flip(true, false);

		}
		player_right_idle = new Animation(.1f, frameCollection);
		player_left_idle = new Animation(.1f, frameCollection2);

		// the walk animation
		frameCollection = new TextureRegion[3];
		frameCollection2 = new TextureRegion[3];
		for (int i = 0; i < frameCollection.length; i++) {
			frameCollection[i] = new TextureRegion(player, (i + 3) * 16, 0, 16,
					28);
			frameCollection2[i] = new TextureRegion(frameCollection[i]);
			frameCollection2[i].flip(true, false);
			// walkRightFrames[i] = ImageCache.getTexture("right_running" + i);
		}
		player_right_animation = new Animation(.1f, frameCollection);
		player_left_animation = new Animation(.1f, frameCollection2);

		// jump frame
		player_jump_left = new TextureRegion(player, 6 * 16, 0, 16, 32);
		player_jump_right = new TextureRegion(player, 6 * 16, 0, 16, 32);
		player_jump_right.flip(true, false);
		
		player_falling_left = new TextureRegion(player, 7 * 16, 0, 16, 32);
		player_falling_right = new TextureRegion(player, 7 * 16, 0, 16, 32);
		player_falling_right.flip(true, false);
	}

	private static void initBlocks() {
		blocks = new Texture("images/blockssheet.png");
		block_dirt = new TextureRegion(blocks, 0, 0, 16, 16);
		block_indestructable = new TextureRegion(blocks, 16, 0, 16, 16);
	}
}
