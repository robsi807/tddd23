package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

/*
 * Class used to store constant variables used throughout the project.
 */
public class Constants {
	public static final float MAX_FPS = 60;
	public static final float SIZE = 16f;
	public static final float MAX_FALLING_SPEED = 5f*MAX_FPS ;
	public static final float JUMPING_SPEED = 3f*MAX_FPS ;
	public static final float MAX_MOVING_SPEED = 1f*MAX_FPS ;
	public static final Vector2 WORLD_GRAVITY = new Vector2(0, -0.07f*MAX_FPS );
	static final float SPEED = 0.5f*MAX_FPS ; // unit per second
//	static final float JUMP_VELOCITY = 11f*MAX_FPS ;

}
