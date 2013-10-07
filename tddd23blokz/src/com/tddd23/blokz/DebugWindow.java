package com.tddd23.blokz;

import java.util.Iterator;
import java.util.Stack;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tddd23.blokz.world.World;

public class DebugWindow {

	private OrthographicCamera consoleCam;
	private static String[] lines;
	private World world;
	private Graphics graphics;
	private ShapeRenderer renderer;
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	public DebugWindow(World world, Graphics graphics, ShapeRenderer renderer) {
		consoleCam = new OrthographicCamera(1024, 768);
		lines = new String[12];
		for (int x = 0; x < 12; x++)
			lines[x] = "";
		this.world = world;
		this.renderer = renderer;
		this.graphics = graphics;
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

	}

	public void render() {
		spriteBatch.setProjectionMatrix(consoleCam.combined);

		consoleCam.position.set(graphics.getWidth() / 2,
				graphics.getHeight() / 2, 0.0f);
		// update camera
		consoleCam.update();
		// set the projection matrix
		renderer.setProjectionMatrix(consoleCam.combined);
		// draw something
		renderer.begin(ShapeType.Filled);
		renderer.setColor(new Color(0, 0, 0, 0));
		renderer.rect(0, 0, graphics.getWidth(), 200);
		renderer.end();

		renderer.begin(ShapeType.Line);
		renderer.setColor(new Color(1, 1, 1, 0));
		renderer.line(0, 200, graphics.getWidth(), 200);
		renderer.end();

		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, 0);

		for (int pos = 0; pos < 12; pos++)
			font.draw(spriteBatch, lines[pos], 10, 190 - pos * 15);

		spriteBatch.end();

		spriteBatch.begin();
		font.draw(spriteBatch, "X: " + (int) world.getPlayer().getPosition().x
				+ " Y: " + (int) world.getPlayer().getPosition().y,
				graphics.getWidth() / 2, graphics.getHeight() / 2);
		font.draw(spriteBatch, "dX: " + world.getPlayer().getAcceleration().x
				+ " dY: " + world.getPlayer().getAcceleration().y,
				graphics.getWidth() / 2, graphics.getHeight() / 2 - 15);
		spriteBatch.end();

		consoleCam.update();
	}

	public static void addText(String str) {
		for (int pos = 11; pos > 0; pos--) {
			lines[pos] = lines[pos - 1];
		}
		lines[0] = str;

	}
}
