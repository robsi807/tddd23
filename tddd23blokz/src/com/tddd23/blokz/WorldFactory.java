package com.tddd23.blokz;

import java.awt.Point;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.triggers.DeathTrigger;
import com.tddd23.blokz.triggers.JumpTrigger;

public class WorldFactory {

	public static World createMap(String mapname) {

		TiledMap map = new TmxMapLoader().load("maps/" + mapname + ".tmx");
		TiledMapTileLayer blocks = (TiledMapTileLayer) map.getLayers().get(
				"blocks");
		MapProperties prop = map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);

		World world = new World(mapWidth * 16, mapHeight * 16);

		world.setMap(map);
		world.setMaxNrOfBlocks(Integer.parseInt((String) prop.get("max_blocks")));

		RectangleMapObject rectObj = null;
		MapProperties properties = null;

		// looping throu all the blocks in the map
		for (int y = 0; y < world.getMapSize().height; y++) {
			for (int x = 0; x < world.getMapSize().width; x++) {

				// if a block exists and is solid
				if (blocks.getCell(x, y) != null
						&& blocks.getCell(x, y).getTile().getProperties()
								.containsKey("solid")) {
					world.getBlocks()[x][y] = new Block(new Vector2(x
							* Constants.SIZE, y * Constants.SIZE), world);
				}
			}
		}
		

		// looping all the objects finding the interesting ones and adding them
		// to the world object
		for (MapObject obj : map.getLayers().get("objects").getObjects()) {
			properties = obj.getProperties();
			rectObj = (RectangleMapObject) obj;

			// all objects
			if (properties.containsKey("spawn_point")) {
				world.setSpawnPoint(new Point((int) rectObj.getRectangle().x,
						(int) rectObj.getRectangle().y));
				world.createPlayer();
			}
			if (properties.containsKey("trigger_player_jump")) {
				world.addTrigger(new JumpTrigger(world.getPlayer(), rectObj
						.getRectangle()));
			}

		}

	
		return world;
	}

}
