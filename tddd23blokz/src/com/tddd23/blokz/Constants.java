package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

/*
 * Class used to store constant variables used throughout the project.
 */
public class Constants {
	public static final float MAX_FPS = 60;
	public static final int SIZE = 16;
	public static final float MAX_FALLING_SPEED = 4.5f*MAX_FPS ;
	public static final float JUMPING_SPEED = 3.2f*MAX_FPS ;
	public static final float MAX_MOVING_SPEED = 100f*MAX_FPS ;
	public static final Vector2 WORLD_GRAVITY = new Vector2(0, -0.1f*MAX_FPS );
	public static final float SPEED = 1.5f*MAX_FPS ; // unit per second
	public final static int RENDER_DIST = 12;
//	static final float JUMP_VELOCITY = 11f*MAX_FPS ;

}
