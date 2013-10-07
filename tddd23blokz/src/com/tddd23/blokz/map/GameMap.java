package com.tddd23.blokz.map;

import java.util.Calendar;

public class GameMap {

	private String name, location;
	private int score;
	private int timeInMillis;

	public GameMap(String name, String location, int score, int timeInMillis) {
		this.name = name;
		this.location = location;
		this.score = score;
		this.timeInMillis = timeInMillis;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getScore() {
		return score;
	}

	public int getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(int timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
