package com.tddd23.blokz;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

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

		renderPlayer();
		renderDynamicObjects();

		for (Rectangle r : world.getCollisionRects()) {
			debugRenderer.begin(ShapeType.Filled);
			debugRenderer.setColor(new Color(1, 1, 1, 0));
			debugRenderer.rect(r.x, r.y,
					r.width, r.height);
			debugRenderer.end();
		}
		debugRenderer.end();
	}

	private void renderDynamicObjects() {
		ArrayList<GameObject> dynamicObjects = world.getDynamicObjects();
		for (GameObject object : dynamicObjects) {
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

	private void doTestZoom() {
		if (zoom < 1) {
			zoom += 0.05;
		} else {
			zoomDone = true;
		}

		cam.setToOrtho(true, 40 + 80 - 80 * zoom, 28 + 56 - 56 * zoom);

	}
}
