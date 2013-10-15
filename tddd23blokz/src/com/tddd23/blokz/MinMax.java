package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.world.World;

public class MinMax {
	public int minX;
	public int maxX;
	public int minY;
	public int maxY;

	public void setRelevantCoordinates(int distance, Vector2 position,
			World world) {

		minX = (int) position.x / 16 - distance-7;
		maxX = (int) position.x / 16 + distance+7;
		minY = (int) position.y / 16 - distance;
		maxY = (int) position.y / 16 + distance;

		if (minX < 0)
			minX = 0;

		if (maxX > world.getMapSize().width / 16)
			maxX = world.getMapSize().width / 16;

		if (minY < 0)
			minY = 0;

		if (maxY > world.getMapSize().height / 16)
			maxY = world.getMapSize().height / 16;

	}
}
