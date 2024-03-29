package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Wizards Tower";
		cfg.useGL20 = true;
		cfg.width = 1280 ;
		cfg.height = 720;
		cfg.resizable = false;
		cfg.addIcon("images/icon.png", FileType.Internal);
		
		cfg.vSyncEnabled = true;
		new LwjglApplication(new Blokz(), cfg);
	}
}
