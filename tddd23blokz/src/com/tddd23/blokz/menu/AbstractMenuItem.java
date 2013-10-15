package com.tddd23.blokz.menu;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractMenuItem implements MenuItem {

	private String title;
	private Rectangle bounds;
	private String title2;
	private boolean unlocked;

	public AbstractMenuItem(String title, boolean unlocked) {
		this. unlocked = unlocked;
		this.title = title;
	}
	public AbstractMenuItem(String title, String title2, boolean unlocked) {
		this. unlocked = unlocked;
		this.title = title;
		this.title2 = title2;
	}
	public String getTitle() {
		return title;
	}

	public boolean isUnlocked() {
		return unlocked;
	}
	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}
	public String getTitle2() {
		return title2;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public abstract void trigger();

}
