package com.tddd23.blokz.world;

import java.awt.Point;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.blocks.Block.BlockType;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.triggers.DeathTrigger;
import com.tddd23.blokz.triggers.GravityTrigger;
import com.tddd23.blokz.triggers.JumpTrigger;
import com.tddd23.blokz.triggers.PlayerTrigger;
import com.tddd23.blokz.triggers.Triggerable;

public class WorldFactory {

	public static World createMap(GameMap gmap, GameScreen screen) {

		TiledMap map = new TmxMapLoader().load("maps/" + gmap.getLocation()
				+ ".tmx");

		TiledMapTileLayer blocks = (TiledMapTileLayer) map.getLayers().get(
				"blocks");
		MapProperties prop = map.getProperties();

		int mapHeight = Integer.parseInt(prop.get("width", String.class));
		int mapWidth = Integer.parseInt(prop.get("height", String.class));
		World world = new World(mapWidth * Constants.SIZE, (int) mapHeight
				* Constants.SIZE, screen);

		world.setMaxNrOfBlocks(Integer.parseInt((String) prop.get("max_blocks")));

		RectangleMapObject rectObj = null;
		MapProperties properties = null;

		String type = null;
		BlockType blockType = null;

		// looping throu all the blocks in the map
		for (int y = 0; y < world.getMapSize().height; y++) {
			for (int x = 0; x < world.getMapSize().width; x++) {

				// if a block exists and is solids
				if (blocks.getCell(x, y) != null) {
					type = (String) blocks.getCell(x, y).getTile()
							.getProperties().get("type");
					if (type != null) {

						// checking the types of blocks
						if (type.equals("dirt")) {
							blockType = BlockType.DIRT;
						} else if (type.equals("stone")) {
							blockType = BlockType.STONE;
						} else if (type.equals("spike")) {
							blockType = BlockType.SPIKE;
							world.getTriggers().add(
									new DeathTrigger(null, new Rectangle(
											(x * Constants.SIZE),
											(y * Constants.SIZE)
													+ Constants.SIZE,
											Constants.SIZE, 3)));
						} else if (type.equals("gravity")) {
							blockType = BlockType.GRAVITY;
							world.getTriggers().add(
									new GravityTrigger(null, new Rectangle(
											(x * Constants.SIZE) - 2
													* Constants.SIZE,
											(y * Constants.SIZE)
													- (2 * Constants.SIZE),
											Constants.SIZE * 5,
											Constants.SIZE * 5)));
						}

						world.getBlocks()[x][y] = new Block(new Vector2(x
								* Constants.SIZE, y * Constants.SIZE), world,
								blockType);

					}
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

		for (Triggerable t : world.getTriggers()) {
			if (t instanceof PlayerTrigger) {
				((PlayerTrigger) t).setPlayer(world.getPlayer());
			}
		}

		return world;
	}
}
