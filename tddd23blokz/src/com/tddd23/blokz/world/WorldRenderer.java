package com.tddd23.blokz.world;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.Constants;
import com.tddd23.blokz.GameScreen;
import com.tddd23.blokz.MinMax;
import com.tddd23.blokz.Player.State;
import com.tddd23.blokz.Time;
import com.tddd23.blokz.blocks.Block;
import com.tddd23.blokz.font.FontHandler;
import com.tddd23.blokz.gfx.DebugWindow;
import com.tddd23.blokz.gfx.ImageCache;
import com.tddd23.blokz.gfx.VisualEffect;
import com.tddd23.blokz.gfx.TextureHandler;
import com.tddd23.blokz.gfx.VisualEffectHandler;
import com.tddd23.blokz.triggers.DeathTrigger.Facing;
import com.tddd23.blokz.triggers.FireTrigger;
import com.tddd23.blokz.triggers.GravityTrigger;
import com.tddd23.blokz.triggers.PlayerTrigger;

public class WorldRenderer {

	private static final int HUD_HEIGHT = 128, HUD_BLOCK_SCALE = 5,
			HUD_BLOCK_HIGHTLIGHT_SIZE = 3;

	private OrthographicCamera cam;

	private DebugWindow debugWindow;
	private boolean debugMode = false;

	private ShapeRenderer debugRenderer;
	private ShapeRenderer triggerRenderer;
	private ShapeRenderer unprojectedRenderer;

	private GameScreen screen;

	private World world;

	private SpriteBatch renderBatch;
	private SpriteBatch hudBatch;
	private SpriteBatch unprojectedBatch;

	private VisualEffectHandler visualEffHand;

	private MinMax relevantBlocks;

	private Sprite playerSprite;
	private int playerAngle;

	private TextureRegion tempRegion;

	private BitmapFont hudFont;
	private String hudStr;

	private Block helpBlock;
	private Point clickPoint;

	private Rectangle triggerRect;
	private Rectangle fireTriggerRect;

	private Time time;

	float nrOfUpdates;

	boolean running = false;

	/** for debug rendering **/

	public WorldRenderer(World world, GameScreen screen) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
		cam.zoom = 0.33f;

		time = new Time();
		this.screen = screen;
		playerAngle = 0;

		relevantBlocks = new MinMax();
		visualEffHand = new VisualEffectHandler(world);

		ImageCache.load();
		renderBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		hudFont = new BitmapFont();
		hudFont = FontHandler.courier[2];
		unprojectedBatch = new SpriteBatch();

		TextureHandler.init();
		unprojectedRenderer = new ShapeRenderer();
		debugRenderer = new ShapeRenderer();
		triggerRenderer = new ShapeRenderer();
		debugWindow = new DebugWindow(world, Gdx.graphics, debugRenderer);

		// init visualEff

		this.cam.update();

	}

	public void render(float delta) {
		if (running)
			time.addTime(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveCamera(); // moves the camera to the player location

		hudBatch.setProjectionMatrix(cam.combined);
		renderBatch.setProjectionMatrix(cam.combined);
		debugRenderer.setProjectionMatrix(cam.combined);
		triggerRenderer.setProjectionMatrix(cam.combined);

		updateHelpBlock(delta);
		renderDynamicObjects(delta);
		renderBlocks(delta);
		renderPlayer(delta);
		renderEffects(delta);
		renderHelpBlock(delta);
		renderHud(delta);
	}

	private void renderEffects(float delta) {
		for (VisualEffect effect : visualEffHand.getVisualEffects()) {
			if (effect.getParent() == null || !effect.getParent().isHidden()) {
				effect.updateEffect(delta);
				renderBatch.begin();
				effect.getEffect().draw(renderBatch);
				renderBatch.end();
			}
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void renderHud(float delta) {
		unprojectedBatch.begin();
		unprojectedBatch.setColor(1f, 1f, 1f, 0.6f);
		unprojectedBatch.draw(TextureHandler.hudbg, 0, 0);
		unprojectedBatch.end();

		Point drawHudBlock = new Point(20, HUD_HEIGHT / 2
				- (int) ((HUD_BLOCK_SCALE * Constants.SIZE / 2)));

		for (int i = 0; i < world.getAllowedBlocks().length; i++) {
			if (world.getAllowedBlocks()[i] != -1) {
				unprojectedBatch.begin();
				if (world.getPlayer().getSelectedBlockType().ordinal() == i) {
					unprojectedBatch.setColor(1f, 1f, 1f, 1f);
					if (world.getAllowedBlocks()[i] == 0) {
						hudFont.setColor(1, 0, 0, 1f);
					} else {
						hudFont.setColor(1, 1, 1, 1f);
					}
				} else {
					if (world.getAllowedBlocks()[i] == 0) {
						hudFont.setColor(1, 0, 0, 0.25f);
					} else {
						hudFont.setColor(1, 1, 1, 0.25f);
					}
					unprojectedBatch.setColor(1f, 1f, 1f, .25f);
				}
				unprojectedBatch.draw(TextureHandler.hud_blocks[i],
						drawHudBlock.x, drawHudBlock.y, 0, 0, Constants.SIZE,
						Constants.SIZE, HUD_BLOCK_SCALE, HUD_BLOCK_SCALE, 0);

				drawHudBlock.x += 10 + HUD_BLOCK_SCALE * Constants.SIZE;

				hudStr = "x" + world.getAllowedBlocks()[i];
				hudFont.draw(unprojectedBatch, hudStr, drawHudBlock.x,
						HUD_HEIGHT / 2 + hudFont.getBounds(hudStr).height / 2);

				drawHudBlock.x += hudFont.getBounds(hudStr).width + 20;
				hudFont.setColor(1, 1, 1, 1f);
				unprojectedBatch.end();

			}
		}

		unprojectedBatch.begin();
		hudStr = "Time: " + time;

		hudFont.draw(unprojectedBatch, hudStr, Gdx.graphics.getWidth()
				- hudFont.getBounds(hudStr).width - 20, HUD_HEIGHT / 2
				+ hudFont.getBounds(hudStr).height / 2);
		unprojectedBatch.end();

	}

	public void zoomIn() {
		if (cam.zoom > 0.01)
			cam.zoom -= 0.01f;
	}

	public void zoomOut() {
		if (cam.zoom < 0.33)
			cam.zoom += 0.01f;
	}

	private void renderBlocks(float delta) {
		tempRegion = null;

		relevantBlocks.setRelevantCoordinates(Constants.RENDER_DIST, world
				.getPlayer().getPosition(), world);

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
					case SPIKE:
						if (world.getBlocks()[x][y].getFacing() != null) {
							if (world.getBlocks()[x][y].getFacing() == Facing.DOWN) {
								tempRegion = TextureHandler.block_spike_down;
							} else if (world.getBlocks()[x][y].getFacing() == Facing.RIGHT) {
								tempRegion = TextureHandler.block_spike_right;
							} else if (world.getBlocks()[x][y].getFacing() == Facing.LEFT) {
								tempRegion = TextureHandler.block_spike_left;
							} else if (world.getBlocks()[x][y].getFacing() == Facing.UP) {
								tempRegion = TextureHandler.block_spike_up;
							}
						}

						break;
					case GRAVITY:
						tempRegion = TextureHandler.block_gravity.getKeyFrame(
								world.getPlayer().getStateTime(), true);
						break;
					case GOAL:
						tempRegion = TextureHandler.block_goal;
						break;
					case FIRE:
						tempRegion = TextureHandler.block_fire.getKeyFrame(
								world.getPlayer().getStateTime(), true);
						break;
					case JUMP:
						tempRegion = TextureHandler.block_jump;
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

	private void renderHelpBlock(float delta) {

		if (world.getAllowedBlocks()[world.getPlayer().getSelectedBlockType()
				.ordinal()] <= 0)
			return;

		setOpacity(0.3f);
		if (helpBlock == null)
			return;
		renderBatch.begin();

		switch (helpBlock.getType()) {
		case DIRT:
			tempRegion = TextureHandler.block_dirt;
			break;
		case JUMP:
			tempRegion = TextureHandler.block_jump;
			break;
		case GRAVITY:
			tempRegion = TextureHandler.block_gravity.getKeyFrame(0);
			break;
		}

		renderBatch.draw(tempRegion,
				helpBlock.getPosition().x * Constants.SIZE,
				helpBlock.getPosition().y * Constants.SIZE);
		renderBatch.end();
	}

	private void renderDynamicObjects(float delta) {
		for (PlayerTrigger t : world.getTriggers()) {
			if (t instanceof FireTrigger && t.isActive()) {
			} else if (t instanceof GravityTrigger) {
				float offset = 0;
				for (int y = (int) t.getBounds().y; y <= (int) t.getBounds().y
						+ (int) t.getBounds().height; y += Constants.SIZE) {

					for (int x = (int) t.getBounds().x; x < (int) t.getBounds().x
							+ (int) t.getBounds().width; x += Constants.SIZE) {
						offset++;
						tempRegion = TextureHandler.effect_gravityfield
								.getKeyFrame(world.getPlayer().getStateTime()
										+ offset, true);
						renderBatch.begin();
						renderBatch.draw(tempRegion, x, y);
						renderBatch.end();
					}
				}

			}
		}

		if (debugMode) {
			debugWindow.render();

			for (PlayerTrigger t : world.getTriggers()) {
				if (t.isActive()) {
					triggerRect = t.getBounds();
					triggerRenderer.begin(ShapeType.Line);
					triggerRenderer.rect(triggerRect.x, triggerRect.y,
							triggerRect.width, triggerRect.height);
					triggerRenderer.end();
				}
			}
		}
	}

	private void renderPlayer(float delta) {
		if (world.getPlayer().isHidden())
			return;

		if (playerSprite == null) {
			playerSprite = new Sprite(TextureHandler.player_falling_right);
			playerSprite.setOrigin(8, 14);
		}

		if (!world.getPlayer().isGrounded()) { // is in the air

			if (world.getPlayer().getVelocity().y >= 0
					|| world.getPlayer().isInvertGravity()) {
				playerSprite
						.setRegion(world.getPlayer().isFacingLeft() ? TextureHandler.player_jump_right
								: TextureHandler.player_jump_left);
			} else if (world.getPlayer().getVelocity().y < 0
					|| !world.getPlayer().isInvertGravity()) {
				playerSprite
						.setRegion(world.getPlayer().isFacingLeft() ? TextureHandler.player_falling_right
								: TextureHandler.player_falling_left);
			}
		} else { // Is on the ground
			playerSprite
					.setRegion(world.getPlayer().isFacingLeft() ? TextureHandler.player_left_idle
							.getKeyFrame(world.getPlayer().getStateTime(), true)
							: TextureHandler.player_right_idle.getKeyFrame(
									world.getPlayer().getStateTime(), true));

			if (world.getPlayer().getState().equals(State.WALKING)) {
				playerSprite
						.setRegion(world.getPlayer().isFacingLeft() ? TextureHandler.player_left_animation
								.getKeyFrame(world.getPlayer().getStateTime(),
										true)
								: TextureHandler.player_right_animation
										.getKeyFrame(world.getPlayer()
												.getStateTime(), true));
			}

		}
		playerSprite.setSize(playerSprite.getRegionWidth(),
				playerSprite.getRegionHeight());

		// if inverted, we want to turn the sprite
		if (world.getPlayer().isInvertGravity()) {
			if (playerAngle != 180 && nrOfUpdates % 2 == 0) {
				playerAngle += 45;
			} else {
				playerSprite.flip(true, false);
			}
		} else {
			if (playerAngle != 0 && nrOfUpdates % 2 == 0) {
				playerAngle -= 45;
			}
		}
		playerSprite.setRotation(playerAngle);

		// moving the sprite to the playerlocation
		playerSprite.setPosition(world.getPlayer().getPosition().x, world
				.getPlayer().getPosition().y);

		// draw the player
		renderBatch.begin();
		playerSprite.draw(renderBatch);
		renderBatch.end();

		if (debugMode) {
			triggerRenderer.begin(ShapeType.Line);
			triggerRenderer.setColor(Color.RED);
			triggerRenderer.rect(world.getPlayer().getPosition().x, world
					.getPlayer().getPosition().y,
					world.getPlayer().getBounds().width, world.getPlayer()
							.getBounds().height);
			triggerRenderer.end();
		}

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

	public Time getTime() {
		return time;
	}

	public void drawGetReady() {
		unprojectedBatch.begin();
		hudFont = FontHandler.courier[13];
		hudFont.draw(unprojectedBatch, "Get ready!", 110, 650);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press space to start", 100, 100);
		unprojectedBatch.end();
	}

	public void drawPause() {
		unprojectedBatch.begin();
		hudFont = FontHandler.courier[13];
		hudFont.draw(unprojectedBatch, "Paused", 120, 650);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press space to continue playing", 150,
				150);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press ESC to enter menu", 150, 100);
		unprojectedBatch.end();
	}

	public void drawDeath() {
		unprojectedBatch.begin();
		hudFont = FontHandler.courier[13];
		hudFont.draw(unprojectedBatch, "You died", 120, 650);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press space to restart level", 150, 150);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press ESC to enter menu", 150, 100);
		unprojectedBatch.end();
	}

	public void drawNextMap() {
		unprojectedBatch.begin();
		hudFont = FontHandler.courier[13];
		hudFont.draw(unprojectedBatch, "Finished!", 120, 650);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Time: " + time, 130, 550);
		hudFont = FontHandler.courier[3];
		hudFont.draw(unprojectedBatch, "Press SPACE to load next map", 110, 100);
		unprojectedBatch.end();
	}

	public void setOpacity(float amount) {
		renderBatch.setColor(1f, 1f, 1f, amount);
	}

	public void updateHelpBlock(float delta) {
		if (!world.getGameMap().isAllowPlacingBlocks())
			return;
		int screenX = Gdx.input.getX();
		int screenY = Gdx.input.getY();

		clickPoint = new Point((int) getRay(screenX, screenY).origin.x,
				(int) getRay(screenX, screenY).origin.y);
		clickPoint.x = (int) (clickPoint.x / Constants.SIZE);
		clickPoint.y = (int) (clickPoint.y / Constants.SIZE);

		if (clickPoint.y < 0 || clickPoint.x < 0
				|| clickPoint.x >= world.getMapSize().width
				|| clickPoint.y >= world.getMapSize().height) {

			return;
		}
		if (!world.isPlaceable(clickPoint.x, clickPoint.y)) {
			setHelpBlock(null);
			return;

		}
		setHelpBlock(new Block(new Vector2(clickPoint.x, clickPoint.y), world,
				world.getPlayer().getSelectedBlockType()));
	}

	public VisualEffectHandler getVisualEffHand() {
		return visualEffHand;
	}

}
