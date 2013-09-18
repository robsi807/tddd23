package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

/*
 * Class for creating blocks.
 * 
 * Har inte stenkoll p� hur factory-pattern:et fungerar, vi f�r kika p�'t
 */
public class BlockFactory {

	public static Block createBlock(int posX, int posY, World world){
		return new Block(new Vector2(posX * Constants.SIZE,
				posY * Constants.SIZE), world);
	}
}
