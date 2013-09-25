package com.tddd23.blokz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/*
 * Class used to store constant variables used throughout the project.
 */
public class Constants {
	public static final float SIZE = 16f;
	public static final float MAX_FALLING_SPEED = 5f;
	public static final float JUMPING_SPEED = 3f;
	public static final float MAX_MOVING_SPEED = 1f;
	public static final Vector2 WORLD_GRAVITY = new Vector2(0, -0.07f);
	public static final Texture BLOCK_DIRT = new Texture(
			"images/hej.png");
}
