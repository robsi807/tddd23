package com.tddd23.blokz.world;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.blocks.Block.BlockType;
import com.tddd23.blokz.map.GameMap;
import com.tddd23.blokz.triggers.DeathTrigger;
import com.tddd23.blokz.triggers.DeathTrigger.Facing;
import com.tddd23.blokz.triggers.FireTrigger;
import com.tddd23.blokz.triggers.GoalTrigger;
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

		int mapHeight = Integer.parseInt(prop.get("height", String.class));
		int mapWidth = Integer.parseInt(prop.get("width", String.class));
		World world = new World(mapWidth * Constants.SIZE, (int) mapHeight
				* Constants.SIZE, screen, gmap);

		String[] nrOfBlocks = ((String) prop.get("max_blocks")).split(":");
		int[] intBlocks = new int[3];
		for (int i = 0; i < 3; i++) {
			intBlocks[i] = Integer.parseInt(nrOfBlocks[i]);
		}

		world.setMaxNrOfBlocks(intBlocks);

		RectangleMapObject rectObj = null;
		EllipseMapObject circleObj = null;
		MapProperties properties = null;

		String type = null;
		BlockType blockType = null;
		Facing face = null;
		ArrayList<PlayerTrigger> connectedTriggers;
		// looping throu all the blocks in the map
		for (int y = 0; y < world.getMapSize().height; y++) {
			for (int x = 0; x < world.getMapSize().width; x++) {
				connectedTriggers = new ArrayList<PlayerTrigger>();
				face = null;
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
						} else if (type.equals("fire")) {
							blockType = BlockType.FIRE;

							// SÄTT NEDANSTÅENDE KOD I BLOCK CONSTRUCTORN
							connectedTriggers.add(new FireTrigger(null, x, y,
									Facing.LEFT, screen));
							connectedTriggers.add(new FireTrigger(null, x, y,
									Facing.RIGHT, screen));
							connectedTriggers.add(new FireTrigger(null, x, y,
									Facing.UP, screen));
							connectedTriggers.add(new FireTrigger(null, x, y,
									Facing.DOWN, screen));
						} else if (type.equals("spike")) {
							blockType = BlockType.SPIKE;

							String facing = (String) blocks.getCell(x, y)
									.getTile().getProperties().get("facing");
							if (facing.equals("up")) {
								face = Facing.UP;
							} else if (facing.equals("down")) {
								face = Facing.DOWN;
							} else if (facing.equals("left")) {
								face = Facing.LEFT;
							} else if (facing.equals("right")) {
								face = Facing.RIGHT;
							}

							connectedTriggers.add(new DeathTrigger(null, x, y,
									face, screen));
						} else if (type.equals("goal")) {
							blockType = BlockType.GOAL;
							connectedTriggers.add(new GoalTrigger(null, x, y,
									screen));
						} else if (type.equals("jump")) {
							blockType = BlockType.JUMP;
							connectedTriggers.add(new JumpTrigger(null, x, y,
									screen));
						}

						if (face == null) {
							world.getBlocks()[x][y] = new Block(new Vector2(x
									* Constants.SIZE, y * Constants.SIZE),
									world, blockType, screen);
						} else {
							world.getBlocks()[x][y] = new Block(new Vector2(x
									* Constants.SIZE, y * Constants.SIZE),
									world, blockType, face);
						}

						if (connectedTriggers != null) {
							world.addTrigger(connectedTriggers);
							world.getBlocks()[x][y]
									.addTrigger(connectedTriggers);
						}

					}
				}
			}

		}

		// looping all the objects finding the interesting ones and adding them
		// to the world object
		for (MapObject obj : map.getLayers().get("objects").getObjects()) {
			properties = obj.getProperties();
			if (obj instanceof RectangleMapObject) {
				rectObj = (RectangleMapObject) obj;

				// all objects
				if (properties.containsKey("spawn_point")) {
					world.setSpawnPoint(new Point(
							(int) rectObj.getRectangle().x, (int) rectObj
									.getRectangle().y));
					world.createPlayer();
				} else if (properties.containsKey("gravity")) {
					world.addTrigger(new GravityTrigger((int) rectObj
							.getRectangle().x, (int) rectObj.getRectangle().y,
							(int) rectObj.getRectangle().width, (int) rectObj
									.getRectangle().height, null, screen));
				}

			} else if (obj instanceof EllipseMapObject) {
				circleObj = (EllipseMapObject) obj;

				world.addWorldText(new WorldText(properties
						.get("text").toString(), Integer.parseInt(properties.get("size").toString()),
						(int) circleObj.getEllipse().x, (int) circleObj
								.getEllipse().y));

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
