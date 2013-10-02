package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class MinMax {
	int minX, maxX, minY, maxY;

	public void setRelevantCoordinates(int distance, Vector2 position,
			World world) {

		minX = (int) position.x / 16 - distance;
		maxX = (int) position.x / 16 + distance;
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
