package com.tddd23.blokz;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldRenderer {

	private OrthographicCamera cam;

	private World world;
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(20, 14);
		this.cam.position.set(5, 3.5f, 0);
		this.cam.update();
	}

	public void render() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
			
		debugRenderer.setColor(new Color(1, 0, 0, 1));
		debugRenderer.rect(world.getPlayer().position.x, world.getPlayer().position.y, 2, 2);
		debugRenderer.end();
	}
}
