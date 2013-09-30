package com.tddd23.blokz;

import com.badlogic.gdx.math.Vector2;

public class MinMax {
	int minX, maxX, minY, maxY;

	public void getRelevantCoordinates(Vector2 position, World world) {
		MinMax value = new MinMax();

		minX = (int) position.x - 3;
		maxX = (int) position.x + 3;
		minY = (int) position.y - 3;
		maxY = (int) position.y + 3;

		if (minX < 0)
			minX = 0;

		if (maxX > world.getMapSize().width)
			maxX = world.getMapSize().width;

		if (minY < 0)
			minY = 0;

		if (maxY > world.getMapSize().height)
			maxY = world.getMapSize().height;
		System.out.println("minX = "+ minX + ", maxX = "+ maxX + ", minY = " + minY + ", maxY = " + maxY);
	}
}
