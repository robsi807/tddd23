package com.tddd23.blokz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tddd23.blokz.GameObject;

public class VisualEffect {

	private ParticleEffect effect;
	private GameObject parent;
	private int xOffset, yOffset;

	public VisualEffect(String effectPath, String imagePath, int xPos, int yPos) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.local(effectPath), Gdx.files.local(imagePath));
		effect.setPosition(xPos, yPos);
	}

	public VisualEffect(GameObject parent, String effectPath, String imagePath,
			int xOffset, int yOffset) {
		this.parent = parent;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		effect = new ParticleEffect();
		effect.load(Gdx.files.local(effectPath), Gdx.files.local(imagePath));
		effect.setPosition(parent.getPosition().x + xOffset,
				parent.getPosition().y + yOffset);
	}

	public void updateEffect(float delta) {

		if (effect.isComplete())
			effect.start();

		if (parent != null) {
			if (!parent.isFacingLeft()) {
				effect.setPosition(parent.getPosition().x + xOffset,
						parent.getPosition().y + yOffset);
			} else {
				effect.setPosition(parent.getPosition().x,
						parent.getPosition().y + yOffset);
			}
		}
		effect.update(delta);
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public GameObject getParent() {
		return parent;
	}

	public ParticleEffect getEffect() {
		return effect;
	}

}
