package com.tddd23.blokz.world;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.tddd23.blokz.map.GameMap;

public class WorldManager {
	private ArrayList<GameMap> mapInfo;
	FileHandle file;

	public WorldManager() {

		file = Gdx.files.local("maps/mapinfo.txt");

		mapInfo = new ArrayList<GameMap>();
		Scanner br = null;
		String[] splitString = null; // index: 0 = Map name, 1 = maplocation, 2
										// = score, 3 = time

		try {
			br = new Scanner(file.read());
			String line = "";
			while (br.hasNext()) {
				line = br.nextLine();
				if (line.charAt(0) == '%')
					continue;
				splitString = line.split(";");
				mapInfo.add(new GameMap(splitString[0], splitString[1], Integer
						.parseInt(splitString[2]), Integer
						.parseInt(splitString[3]), Integer
						.parseInt(splitString[4])));
			}

		} finally {
			br.close();
		}

	}

	public ArrayList<GameMap> getMapInfo() {
		return mapInfo;
	}

	public GameMap getMextMap(GameMap currentMap) {
		if (mapInfo.indexOf(currentMap) == mapInfo.size() - 1)
			return mapInfo.get(0);
		return mapInfo.get(mapInfo.indexOf(currentMap) + 1);
	}

}
