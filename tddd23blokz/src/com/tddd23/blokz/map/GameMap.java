package com.tddd23.blokz.map;

import java.util.Calendar;

import com.tddd23.blokz.Time;

public class GameMap {

	private String name, location;
	private int timeInMillis;
	private boolean allowPlacingBlocks, mapUnlocked;

	public GameMap(String name, String location, int timeInMillis,
			int allowPlacingBlocks, int mapUnlocked) {
		this.name = name;
		this.location = location;
		this.timeInMillis = timeInMillis;
		this.allowPlacingBlocks = (allowPlacingBlocks == 1) ? true : false;
		this.mapUnlocked = (mapUnlocked == 1) ? true : false;
	}

	public String getName() {
		return name;
	}

	public boolean isAllowPlacingBlocks() {
		return allowPlacingBlocks;
	}

	public String getLocation() {
		return location;
	}

	public int getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(int timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public String getTimeString() {
		float timeInSec = timeInMillis / 1000;
		return new Time(timeInSec).toString();
	}

}
