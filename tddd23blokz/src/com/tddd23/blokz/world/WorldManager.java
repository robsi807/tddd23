package com.tddd23.blokz.world;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.tddd23.blokz.Time;
import com.tddd23.blokz.map.GameMap;

public class WorldManager {
	private ArrayList<GameMap> mapInfo;
	private ArrayList<String> comments;
	FileHandle file;

	public WorldManager() {

		file = Gdx.files.local("mapdata.txt");
		mapInfo = new ArrayList<GameMap>();
		comments = new ArrayList<String>();
		Scanner br = null;
		
		String[] splitString = null; // index: 0 = Map name, 1 = maplocation, 2
										// = score, 3 = time
		try {
			br = new Scanner(file.read());
			String line = "";
			while (br.hasNext()) {
				line = br.nextLine();
				if (line.charAt(0) == '%'){
					comments.add(line);
					continue;
				}
				splitString = line.split(";");
				mapInfo.add(new GameMap(splitString[0], splitString[1], Long
						.parseLong(splitString[2]), Integer
						.parseInt(splitString[3]), Integer
						.parseInt(splitString[4])));
			}
		} finally {
			br.close();
		}
	}
	
	public void saveCurrentData(){
		PrintWriter writer = new PrintWriter(file.write(false));
		for(String str : comments)
			writer.write(str+"\n");
		for(GameMap gmap : mapInfo){
			writer.write(gmap.getTextForFile()+"\n");
		}
		writer.close();
	}
	
	public void unlockMap(GameMap map){
		mapInfo.get(mapInfo.indexOf(map)).setMapUnlocked(true);
		saveCurrentData();
	}
	
	public void setNewTimeOnMap(GameMap map, Time time){
		mapInfo.get(mapInfo.indexOf(map)).setTime(time);
		saveCurrentData();
	}

	public ArrayList<GameMap> getMapInfo() {
		return mapInfo;
	}

	public GameMap getNextMap(GameMap currentMap) {
		if (mapInfo.indexOf(currentMap) == mapInfo.size() - 1)
			return mapInfo.get(0);
		return mapInfo.get(mapInfo.indexOf(currentMap) + 1);
	}

}