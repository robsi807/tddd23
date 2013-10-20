package com.tddd23.blokz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tddd23.blokz.world.World;

public class DebugWindow {

	private OrthographicCamera consoleCam;
	private World world;
	private Graphics graphics;
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	public DebugWindow(World world, Graphics graphics, ShapeRenderer renderer) {
		consoleCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.world = world;
		this.graphics = graphics;
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

	}

	public void render() {
		spriteBatch.setProjectionMatrix(consoleCam.combined);
		consoleCam.position.set(graphics.getWidth() / 2,
				graphics.getHeight() / 2, 0.0f);
		consoleCam.update();

		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, 0);
		font.draw(spriteBatch, "X: " + (int) world.getPlayer().getPosition().x
				+ " Y: " + (int) world.getPlayer().getPosition().y,
				graphics.getWidth() / 2, graphics.getHeight() / 2- 15);
		font.draw(spriteBatch, "dX: " + world.getPlayer().getAcceleration().x
				+ " dY: " + world.getPlayer().getAcceleration().y,
				graphics.getWidth() / 2, graphics.getHeight() / 2 - 30);
		
		
		font.draw(spriteBatch, "FPS: "+graphics.getFramesPerSecond(),
				5, Gdx.graphics.getHeight()-5);
		spriteBatch.end();

		consoleCam.update();
	}
}
