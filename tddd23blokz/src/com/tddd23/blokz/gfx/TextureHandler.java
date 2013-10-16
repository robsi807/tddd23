package com.tddd23.blokz.gfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHandler {

	private static Texture player;
	private static Texture blocks;
	private static Texture effects;
	public static Texture background;

	// hud
	public static Texture hudSelection;
	public static Texture hudbg;
	public static Sprite[] hud_blocks;

	// player graphics
	public static Animation player_left_idle;
	public static Animation player_right_idle;

	public static TextureRegion player_jump_right;
	public static TextureRegion player_jump_left;

	public static TextureRegion player_falling_right;
	public static TextureRegion player_falling_left;

	public static Animation player_left_animation;
	public static Animation player_right_animation;

	// blocks
	public static TextureRegion block_dirt;
	public static TextureRegion block_stone;
	public static TextureRegion block_jump;
	public static TextureRegion block_spike_up;
	public static TextureRegion block_spike_down;
	public static TextureRegion block_spike_right;
	public static TextureRegion block_spike_left;
	public static Animation block_gravity;
	public static TextureRegion block_goal;
	public static Animation block_fire;

	// effects
	public static Animation effect_gravityfield;

	// framecollections for animation creation
	private static TextureRegion[] frameCollection;
	private static TextureRegion[] frameCollection2;

	public static void init() { // varför är denna static?
		// placed_Block = ImageCache.getTexture("placed_block");

		initPlayer();
		initBlocks();
		initOther();
		initEffects();

	}

	private static void initOther() {

		background = new Texture("images/background.png");

		hudbg = new Texture("images/hudbg.png");
		hud_blocks = new Sprite[3];
		Sprite addSprite = new Sprite(block_stone);
		addSprite.scale(5);
		hud_blocks[0] = addSprite;

		addSprite = new Sprite(block_jump);
		addSprite.scale(5);
		hud_blocks[1] = addSprite;

		addSprite = new Sprite(block_gravity.getKeyFrame(0));
		addSprite.scale(5);
		hud_blocks[2] = addSprite;

		hudSelection = new Texture("images/hudSelector.png");

	}

	private static void initPlayer() {
		player = new Texture("images/playersheet3.png");

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
		player_right_animation = new Animation(.08f, frameCollection);
		player_left_animation = new Animation(.08f, frameCollection2);

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
		block_stone = new TextureRegion(blocks, 16, 0, 16, 16);
		block_jump = new TextureRegion(blocks, 32, 0, 16, 16);
		block_goal = new TextureRegion(blocks, 3 * 16, 0, 16, 16);

		block_spike_up = new TextureRegion(blocks, 7 * 16, 16, 16, 16);
		block_spike_down = new TextureRegion(blocks, 5 * 16, 16, 16, 16);
		block_spike_right = new TextureRegion(blocks, 4 * 16, 16, 16, 16);
		block_spike_left = new TextureRegion(blocks, 6 * 16, 16, 16, 16);

		// gravity block
		frameCollection = new TextureRegion[3];
		for (int i = 0; i < frameCollection.length; i++) {
			frameCollection[i] = new TextureRegion(blocks, (i + 4) * 16, 0, 16,
					16);
		}
		block_gravity = new Animation(.2f, frameCollection);

		// fireblock
		frameCollection = new TextureRegion[4];
		for (int i = 0; i < frameCollection.length; i++) {
			frameCollection[i] = new TextureRegion(blocks, i * 16, 16, 16, 16);
		}
		block_fire = new Animation(.2f, frameCollection);

	}

	private static void initEffects() {
		effects = new Texture("images/effects.png");

		frameCollection = new TextureRegion[6];
		for (int i = 0; i < frameCollection.length; i++) {
			frameCollection[i] = new TextureRegion(effects, i * 16, 0, 16, 16);
		}
		effect_gravityfield = new Animation(.1f, frameCollection);

	}

}
