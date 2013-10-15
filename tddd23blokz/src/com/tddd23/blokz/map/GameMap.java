package com.tddd23.blokz.map;

import java.util.Calendar;

import com.tddd23.blokz.Time;

public class GameMap {

	private String name, location;
	private Time time;
	private boolean allowPlacingBlocks, mapUnlocked;

	public GameMap(String name, String location, long millis,
			int allowPlacingBlocks, int mapUnlocked) {
		this.name = name;
		this.location = location;
		this.time = new Time(millis);
		this.allowPlacingBlocks = (allowPlacingBlocks == 1) ? true : false;
		this.mapUnlocked = (mapUnlocked == 1) ? true : false;
	}

	public String getName() {
		return name;
	}

	public boolean isAllowPlacingBlocks() {
		return allowPlacingBlocks;
	}

	public boolean isMapUnlocked() {
		return mapUnlocked;
	}

	public void setMapUnlocked(boolean mapUnlocked) {
		this.mapUnlocked = mapUnlocked;
	}

	public String getLocation() {
		return location;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time t) {
		this.time = t;
	}

	public String getTimeString() {
		return time.toString();
	}

	public String getTextForFile() {
		return name + ";" + location + ";" + time.getMillis() + ";"
				+ ((allowPlacingBlocks) ? "1" : "0") + ";"
				+ ((mapUnlocked) ? "1" : "0");
	}

	public boolean isRecordSet(){
		return (time.getMillis() >=0);
	}
}
