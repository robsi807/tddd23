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
import com.tddd23.blokz.triggers.FireTrigger;
import com.tddd23.blokz.triggers.GravityTrigger;
import com.tddd23.blokz.triggers.PlayerTrigger;

public class WorldRenderer {

	private OrthographicCamera cam;

	private DebugWindow debugWindow;
	private boolean debugMode = false;

	private ShapeRenderer debugRenderer;
	private ShapeRenderer triggerRenderer;
	private ShapeRenderer unprojectedRenderer;

	private World world;

	private SpriteBatch renderBatch;
	private SpriteBatch hudBatch;
	private SpriteBatch unprojectedBatch;

	private VisualEffectHandler visualEffHand;

	private MinMax relevantBlocks;

	private Sprite playerSprite;
	private int playerAngle;

	private TextureRegion tempRegion;

	private BitmapFont font;

	private Block helpBlock;
	private Point clickPoint;

	private Rectangle triggerRect;
	private Rectangle fireTriggerRect;

	private Time time;

	float nrOfUpdates;

	boolean running = false;

	/** for debug rendering **/

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
		cam.zoom = 0.33f;

		time = new Time();

		playerAngle = 0;

		relevantBlocks = new MinMax();
		visualEffHand = new VisualEffectHandler(world);

		ImageCache.load();
		renderBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		font = new BitmapFont();
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
			effect.updateEffect(delta);
			renderBatch.begin();
			effect.getEffect().draw(renderBatch);
			renderBatch.end();
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void renderHud(float delta) {
		unprojectedBatch.begin();
		unprojectedBatch.draw(TextureHandler.hud, 100, 678);
		font = FontHandler.courier[2];
		font.draw(unprojectedBatch, "Time: " + time, 130, 750);
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
						tempRegion = TextureHandler.block_spike;
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
		setOpacity(0.3f);
		if (helpBlock == null)
			return;
		renderBatch.begin();
		renderBatch.draw(TextureHandler.block_dirt, helpBlock.getPosition().x
				* Constants.SIZE, helpBlock.getPosition().y * Constants.SIZE);
		renderBatch.end();
	}

	private void renderDynamicObjects(float delta) {
		for (PlayerTrigger t : world.getTriggers()) {
			if (t instanceof FireTrigger && t.isActive()) {
				fireTriggerRect = t.getBounds();
				triggerRenderer.begin(ShapeType.Filled);
				triggerRenderer.setColor(Color.YELLOW);
				triggerRenderer.rect(fireTriggerRect.x, fireTriggerRect.y,
						fireTriggerRect.width, fireTriggerRect.height);
				triggerRenderer.end();
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
		if(world.getPlayer().isHidden())
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
		font = FontHandler.courier[13];
		font.draw(unprojectedBatch, "Get ready!", 110, 650);
		font = FontHandler.courier[3];
		font.draw(unprojectedBatch, "Press space to start", 100, 100);
		unprojectedBatch.end();
	}

	public void drawPause() {
		unprojectedBatch.begin();
		font = FontHandler.courier[13];
		font.draw(unprojectedBatch, "Paused", 120, 650);
		font = FontHandler.courier[3];
		font.draw(unprojectedBatch, "Press space to continue playing", 150, 150);
		font = FontHandler.courier[3];
		font.draw(unprojectedBatch, "Press ESC to enter menu", 150, 100);
		unprojectedBatch.end();
	}

	public void drawNextMap() {
		unprojectedBatch.begin();
		font = FontHandler.courier[13];
		font.draw(unprojectedBatch, "Finished!", 120, 650);
		font = FontHandler.courier[3];
		font.draw(unprojectedBatch, "Time: " + time, 130, 550);
		font = FontHandler.courier[3];
		font.draw(unprojectedBatch, "Press SPACE to load next map", 110, 100);
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
