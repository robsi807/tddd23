package com.tddd23.blokz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldRenderer {

	private OrthographicCamera cam;
	private float zoom = 0;
	private boolean zoomDone = false;

	private World world;
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(40, 28);
		this.cam.position.set(10, -10, 0);
		this.cam.update();
	}

	public void render() {
		if (!zoomDone)
			doTestZoom();
		
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);

		debugRenderer.setColor(new Color(1, 0, 0, 1));
		debugRenderer.rect(world.getPlayer().position.x,
				world.getPlayer().position.y, 2, 2);
		debugRenderer.end();
	}

	private void doTestZoom() {
		if (zoom < 1) {
			zoom += 0.05;
		} else {
			zoomDone = true;
		}

		cam.setToOrtho(true, 40 + 80 - 80 * zoom, 28 + 56 - 56 * zoom);

	}
}
