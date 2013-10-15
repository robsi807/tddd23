package com.tddd23.blokz.menu;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractMenuItem implements MenuItem {

	private String title;
	private Rectangle bounds;
	private String title2;

	public AbstractMenuItem(String title) {
		this.title = title;
	}
	public AbstractMenuItem(String title, String title2) {
		this.title = title;
		this.title2 = title2;
	}
	public String getTitle() {
		return title;
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
