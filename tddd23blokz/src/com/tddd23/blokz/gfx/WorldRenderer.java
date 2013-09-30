package com.tddd23.blokz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.tddd23.blokz.GameObject;
import com.tddd23.blokz.Player;
import com.tddd23.blokz.World;

public class WorldRenderer {

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;

	// this is for drawing all the dynamic objects, as they are not tiles they
	// can not be animated using tile animation
	private SpriteBatch batch;

	private DebugWindow debugWindow;
	private boolean debugMode = false;

	private ShapeRenderer debugRenderer;

	private World world;

	/** for debug rendering **/

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
//		cam.zoom = 10f; The fuck hände här då :o

		debugRenderer = new ShapeRenderer();
		debugWindow = new DebugWindow(world, Gdx.graphics, debugRenderer);
		renderer = new OrthogonalTiledMapRenderer(world.getMap());

		batch = new SpriteBatch();
		this.cam.update();
	}

	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveCamera(); // moves the camera to the player location

		renderer.setView(cam);
		renderer.render();

		debugRenderer.setProjectionMatrix(cam.combined);

		renderPlayer();
		renderDynamicObjects();
		// renderBlocks();

	}

	private void renderDynamicObjects() {

		for (GameObject object : world.getDynamicObjects()) {
			debugRenderer.begin(ShapeType.Line);
			debugRenderer.setColor(new Color(1, 1, 1, 0));
			debugRenderer.rect(object.getPosition().x, object.getPosition().y,
					object.getBounds().width, object.getBounds().height);
			debugRenderer.end();
		}

		if (debugMode)
			debugWindow.render();
	}

	private void renderPlayer() {

		batch.begin();
		
		batch.end();

		Player player = world.getPlayer();
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 0, 0, 1));
		debugRenderer.rect(player.getPosition().x, player.getPosition().y,
				player.getBounds().width, player.getBounds().height);
		debugRenderer.end();
	}

	private void moveCamera() {

		// får fina avrundningsfel när vi castar till int tror jag
		// cam.getPosition().set((int) world.getPlayer().getPosition().x,
		// (int) world.getPlayer().getPosition().y, 0);
		cam.position.set(world.getPlayer().getPosition().x,
				world.getPlayer().getPosition().y, 0);

		// if (cam.getPosition().x < world.getMapSize().width *
		// cam.zoom+((1-cam.zoom)*Constants.SIZE))
		// cam.getPosition().x = world.getMapSize().width *
		// cam.zoom+((1-cam.zoom)*Constants.SIZE);
		// if (cam.getPosition().x > world.getMapSize().width * (1 -
		// cam.zoom)-((1-cam.zoom)*Constants.SIZE))
		// cam.getPosition().x = world.getMapSize().width * (1 -
		// cam.zoom)-((1-cam.zoom)*Constants.SIZE);
		// System.out.println("Cam X:" + cam.getPosition().x + " Cam y:"
		// + cam.getPosition().y + "    " + world.getMapSize().width);
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
