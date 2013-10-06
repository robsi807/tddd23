package com.tddd23.blokz.menu;


public abstract class  AbstractMenuItem implements MenuItem {

	private String title;

	public String getTitle() {
		return title;
	}

	public AbstractMenuItem(String title) {
		this.title = title;
	}

	public abstract void trigger();

}
