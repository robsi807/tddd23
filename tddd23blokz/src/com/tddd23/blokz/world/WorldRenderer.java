package com.tddd23.blokz.world;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameObject;
import com.tddd23.blokz.MinMax;
import com.tddd23.blokz.Player.State;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.gfx.DebugWindow;
import com.tddd23.blokz.gfx.ImageCache;
import com.tddd23.blokz.gfx.TextureHandler;

public class WorldRenderer {

	private final static int RENDER_DIST = 12;

	private OrthographicCamera cam;

	private DebugWindow debugWindow;
	private boolean debugMode = false;

	private ShapeRenderer debugRenderer;

	private World world;

	private SpriteBatch renderBatch;
	private SpriteBatch hudBatch;
	private Texture sourceTexture;

	private MinMax relevantBlocks;

	private TextureRegion playerRegion;
	private TextureRegion tempRegion;

	private BitmapFont font;

	private Block helpBlock;

	private Point clickPoint;

	/** for debug rendering **/

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
		cam.zoom = 0.33f;

		relevantBlocks = new MinMax();

		ImageCache.load();
		renderBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		font = new BitmapFont();

		TextureHandler.init();
		debugRenderer = new ShapeRenderer();
		debugWindow = new DebugWindow(world, Gdx.graphics, debugRenderer);

		this.cam.update();
	}

	public void render() {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveCamera(); // moves the camera to the player location

		hudBatch.setProjectionMatrix(cam.combined);
		renderBatch.setProjectionMatrix(cam.combined);
		debugRenderer.setProjectionMatrix(cam.combined);

		updateHelpBlock();
		renderBlocks();
		renderPlayer();
		renderDynamicObjects();
		renderHelpBlock();
	}

	public void zoomIn() {
		if (cam.zoom > 0.01)
			cam.zoom -= 0.01f;
	}

	public void zoomOut() {
		if (cam.zoom < 0.33)
			cam.zoom += 0.01f;
	}

	private void renderBlocks() {
		tempRegion = null;

		relevantBlocks.setRelevantCoordinates(RENDER_DIST, world.getPlayer()
				.getPosition(), world);

		for (int y = relevantBlocks.minY; y < relevantBlocks.maxY; y++) {
			for (int x = relevantBlocks.minX; x < relevantBlocks.maxX; x++) {
				if (world.getBlocks()[x][y] != null) {
					renderBatch.begin();
					switch (world.getBlocks()[x][y].getType()) {
					case DIRT:
						tempRegion = TextureHandler.block_dirt;
						break;
					case STONE:
						tempRegion = TextureHandler.block_stone;
						break;
					}

					renderBatch.draw(tempRegion,
							world.getBlocks()[x][y].getPosition().x,
							world.getBlocks()[x][y].getPosition().y);

					renderBatch.end();
				}
			}
		}
	}

	public void setHelpBlock(Block helpBlock) {
		this.helpBlock = helpBlock;
	}

	private void renderHelpBlock() {
		setOpacity(0.3f);
		if (helpBlock == null)
			return;
		renderBatch.begin();
		renderBatch.draw(TextureHandler.block_dirt, helpBlock.getPosition().x
				* Constants.SIZE, helpBlock.getPosition().y * Constants.SIZE);
		renderBatch.end();
	}

	private void renderDynamicObjects() {
		for (GameObject object : world.getDynamicObjects()) {
			renderBatch.begin();
			renderBatch.draw(TextureHandler.block_dirt, object.getPosition().x,
					object.getPosition().y);
			renderBatch.end();
		}

		if (debugMode)
			debugWindow.render();
	}

	private void renderPlayer() {

		if (!world.getPlayer().isGrounded()) { // is in the air
			if (world.getPlayer().getVelocity().y >= 0) {
				playerRegion = world.getPlayer().isFacingLeft() ? TextureHandler.player_jump_right
						: TextureHandler.player_jump_left;
			} else {
				playerRegion = world.getPlayer().isFacingLeft() ? TextureHandler.player_falling_right
						: TextureHandler.player_falling_left;
			}
		} else {
			playerRegion = world.getPlayer().isFacingLeft() ? TextureHandler.player_left_idle
					.getKeyFrame(world.getPlayer().getStateTime(), true)
					: TextureHandler.player_right_idle.getKeyFrame(world
							.getPlayer().getStateTime(), true);
			if (world.getPlayer().getState().equals(State.WALKING)) {
				playerRegion = world.getPlayer().isFacingLeft() ? TextureHandler.player_left_animation
						.getKeyFrame(world.getPlayer().getStateTime(), true)
						: TextureHandler.player_right_animation.getKeyFrame(
								world.getPlayer().getStateTime(), true);
			}

		}
		renderBatch.begin();
		renderBatch.draw(playerRegion, world.getPlayer().getPosition().x, world
				.getPlayer().getPosition().y, playerRegion.getRegionWidth(),
				playerRegion.getRegionHeight());

		renderBatch.end();
	}

	private void moveCamera() {

		cam.position.set(world.getPlayer().getPosition().x, world.getPlayer()
				.getPosition().y, 0);

		cam.update();
	}

	public ShapeRenderer getDebugRenderer() {
		return debugRenderer;
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

	public void drawGetReady() {
		hudBatch.begin();
		hudBatch.draw(TextureHandler.ready,
				world.getPlayer().getPosition().x - 100, world.getPlayer()
						.getPosition().y - 50, 200, 100);
		hudBatch.end();
	}

	public void drawPause() {
		hudBatch.begin();
		hudBatch.draw(TextureHandler.paused,
				world.getPlayer().getPosition().x - 100, world.getPlayer()
						.getPosition().y - 50, 200, 100);
		hudBatch.end();
	}

	public void setOpacity(float amount) {
		renderBatch.setColor(1f, 1f, 1f, amount);
	}

	public void updateHelpBlock() {

		int screenX = Gdx.input.getX();
		int screenY = Gdx.input.getY();

		clickPoint = new Point((int) getRay(screenX, screenY).origin.x,
				(int) getRay(screenX, screenY).origin.y);
		clickPoint.x = (int) (clickPoint.x / Constants.SIZE);
		clickPoint.y = (int) (clickPoint.y / Constants.SIZE);

		if (clickPoint.y < 0 || clickPoint.x < 0
				|| clickPoint.x >= world.getMapSize().width
				|| clickPoint.y >= world.getMapSize().height) {

			return; // Utanför
		}
		if (!world.isPlaceable(clickPoint.x, clickPoint.y)) {
			setHelpBlock(null);
			return;

		}
		setHelpBlock(new Block(new Vector2(clickPoint.x, clickPoint.y), world,
				world.getPlayer().getSelectedBlockType()));
	}

}
