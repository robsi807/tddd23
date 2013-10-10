package com.tddd23.blokz.menu;

import com.badlogic.gdx.math.Rectangle;


public abstract class  AbstractMenuItem implements MenuItem {

	private String title;
	private Rectangle bounds;
	private boolean hovered;
	
	public AbstractMenuItem(Rectangle r){
		bounds = r;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public String getTitle() {
		return title;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public AbstractMenuItem(String title) {
		this.title = title;
	}

	public abstract void trigger();

}
