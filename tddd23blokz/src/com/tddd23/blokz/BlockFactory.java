package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

/*
 * Class for creating blocks.
 * 
 * Har inte stenkoll på hur factory-pattern:et fungerar, vi får kika på't
 */
public class BlockFactory {

	public static Block createBlock(int posX, int posY, World world){
		return new Block(new Vector2(posX * Constants.SIZE,
				posY * Constants.SIZE), world);
	}
}
