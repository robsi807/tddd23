package com.tddd23.blokz.world;

import java.awt.Point;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.triggers.JumpTrigger;

public class WorldFactory {

	public static World createMap(GameMap gmap) {

		System.out.println("maps/" + gmap.getLocation() + ".tmx");
		TiledMap map = new TmxMapLoader().load("maps/" + gmap.getLocation()
				+ ".tmx");

		TiledMapTileLayer blocks = (TiledMapTileLayer) map.getLayers().get(
				"blocks");
		MapProperties prop = map.getProperties();

		int mapHeight = Integer.parseInt(prop.get("width", String.class));
		int mapWidth = Integer.parseInt(prop.get("height", String.class));
		World world = new World(mapWidth * Constants.SIZE, (int) mapHeight
				* Constants.SIZE);

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
