package com.tddd23.blokz.world;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class WorldManager {
	
	TiledMap map = new TmxMapLoader().load("maps/" + mapname + ".tmx");

	public ArrayList<String> getMapNames() {
		
	}
}
