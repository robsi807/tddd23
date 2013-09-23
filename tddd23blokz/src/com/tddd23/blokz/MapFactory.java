package com.tddd23.blokz;

import java.awt.Point;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapFactory {

	// FIXA EN JÄVLA PARSE HÄR

	public static World createMap(String mapname) {
		TiledMap map = new TmxMapLoader().load("maps/" + mapname + ".tmx");
		MapProperties prop = map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		

		World world = new World(mapWidth, mapHeight);

		world.setMap(map);

		world.setMaxNrOfBlocks(Integer.parseInt((String) prop.get("max_blocks")));

		RectangleMapObject rectObj = null;
		MapProperties properties = null;

		// looping all the objects finding the interesting ones and adding them
		// to the world object
		for (MapObject obj : map.getLayers().get("objects").getObjects()) {
			properties = obj.getProperties();
			rectObj = (RectangleMapObject) obj;
			
			if (properties.containsKey("spawn_point")) {
				world.setSpawnPoint(new Point((int) rectObj.getRectangle().x,
						(int) rectObj.getRectangle().y));
			}
			
		}

		world.createPlayer();

		return world;
	}

}
