package com.tddd23.blokz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.collision.Ray;

public class WorldRenderer {

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;

	public OrthographicCamera getCam() {
		return cam;
	}

	private ShapeRenderer debugRenderer;

	
	private World world;

	/** for debug rendering **/

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(Gdx.graphics.getWidth() / (16 / 9),
				Gdx.graphics.getHeight());
		cam.zoom = 0.33f;

		debugRenderer = new ShapeRenderer();

		renderer = new OrthogonalTiledMapRenderer(world.getMap());

		this.cam.update();
	}

	public void render() {

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
			debugRenderer.rect(object.position.x, object.position.y,
					object.bounds.width, object.bounds.height);
			debugRenderer.end();
		}
	}

	private void renderPlayer() {
		Player player = world.getPlayer();
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 0, 0, 1));
		debugRenderer.rect(player.position.x, player.position.y,
				player.bounds.width, player.bounds.height);
		debugRenderer.end();
	}

	private void moveCamera() {

		// får fina avrundningsfel när vi castar till int tror jag
		// cam.position.set((int) world.getPlayer().position.x,
		// (int) world.getPlayer().position.y, 0);
		cam.position.set(world.getPlayer().position.x,
				world.getPlayer().position.y, 0);

		// if (cam.position.x < world.getMapSize().width *
		// cam.zoom+((1-cam.zoom)*Constants.SIZE))
		// cam.position.x = world.getMapSize().width *
		// cam.zoom+((1-cam.zoom)*Constants.SIZE);
		// if (cam.position.x > world.getMapSize().width * (1 -
		// cam.zoom)-((1-cam.zoom)*Constants.SIZE))
		// cam.position.x = world.getMapSize().width * (1 -
		// cam.zoom)-((1-cam.zoom)*Constants.SIZE);
		// System.out.println("Cam X:" + cam.position.x + " Cam y:"
		// + cam.position.y + "    " + world.getMapSize().width);
		cam.update();
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public Ray getRay(int screenX, int screenY) {
		return cam.getPickRay(screenX, screenY);
	}

}
