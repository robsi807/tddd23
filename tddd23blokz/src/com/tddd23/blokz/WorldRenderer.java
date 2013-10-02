package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.Player.State;
import com.tddd23.blokz.gfx.DebugWindow;
import com.tddd23.blokz.gfx.ImageCache;
import com.tddd23.blokz.gfx.TextureHandler;

public class WorldRenderer {

	private final static int RENDER_DIST = 12;

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;

	private DebugWindow debugWindow;
	private boolean debugMode = false;

	private ShapeRenderer debugRenderer;

	private World world;

	private SpriteBatch spriteBatch;
	private Texture sourceTexture;

	private MinMax relevantBlocks;

	private TextureRegion playerRegion;

	/** for debug rendering **/

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
		cam.zoom = 0.33f;

		relevantBlocks = new MinMax();

		ImageCache.load();
		spriteBatch = new SpriteBatch();

		TextureHandler.init();
		debugRenderer = new ShapeRenderer();
		debugWindow = new DebugWindow(world, Gdx.graphics, debugRenderer);
		renderer = new OrthogonalTiledMapRenderer(world.getMap());

		this.cam.update();
	}

	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveCamera(); // moves the camera to the player location

		// renderer.setView(cam);
		// renderer.render();

		spriteBatch.setProjectionMatrix(cam.combined);
		debugRenderer.setProjectionMatrix(cam.combined);
		renderBlocks();
		renderPlayer();
		renderDynamicObjects();

	}

	private void renderBlocks() {

		relevantBlocks.setRelevantCoordinates(RENDER_DIST, world.getPlayer()
				.getPosition(), world);

		for (int y = relevantBlocks.minY; y < relevantBlocks.maxY; y++) {
			for (int x = relevantBlocks.minX; x < relevantBlocks.maxX; x++) {
				if (world.getBlocks()[x][y] != null) {
					spriteBatch.begin();
					spriteBatch.draw(TextureHandler.block_dirt,
							world.getBlocks()[x][y].getPosition().x,
							world.getBlocks()[x][y].getPosition().y);
					spriteBatch.end();
				}
			}
		}
	}

	private void renderDynamicObjects() {
		for (GameObject object : world.getDynamicObjects()) {
			spriteBatch.begin();
			spriteBatch.draw(TextureHandler.block_dirt,
					object.getPosition().x, object.getPosition().y);
			spriteBatch.end();
		}

		if (debugMode)
			debugWindow.render();
	}

	private void renderPlayer() {

		if (!world.getPlayer().grounded) { // is in the air
			playerRegion = world.getPlayer().facingLeft ? TextureHandler.player_jump_right
					: TextureHandler.player_jump_left;
		} else {

			playerRegion = world.getPlayer().facingLeft ? TextureHandler.player_left_idle
					: TextureHandler.player_right_idle;

			if (world.getPlayer().getState().equals(State.WALKING)) {
				playerRegion = world.getPlayer().facingLeft ? TextureHandler.player_left_animation
						.getKeyFrame(world.getPlayer().getStateTime(), true)
						: TextureHandler.player_right_animation.getKeyFrame(
								world.getPlayer().getStateTime(), true);
			}

		}
		spriteBatch.begin();
		spriteBatch.draw(playerRegion, world.getPlayer().getPosition().x, world
				.getPlayer().getPosition().y, playerRegion.getRegionWidth(),
				playerRegion.getRegionHeight());

		spriteBatch.end();
	}

	private void moveCamera() {

		cam.position.set(world.getPlayer().getPosition().x, world.getPlayer()
				.getPosition().y, 0);

		cam.update();
	}

	public ShapeRenderer getDebugRenderer() {
		return debugRenderer;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public Ray getRay(int screenX, int screenY) {
		return cam.getPickRay(screenX, screenY);
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void switchDebugMode() {
		if (debugMode)
			debugMode = false;
		else
			debugMode = true;

	}

	public void debug(String text) {
		debugWindow.addText(text);
	}

}
