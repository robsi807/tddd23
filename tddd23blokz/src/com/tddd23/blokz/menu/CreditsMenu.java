package com.tddd23.blokz.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.tddd23.blokz.Blokz;
import com.tddd23.blokz.font.FontHandler;

public class CreditsMenu extends Menu {

	public CreditsMenu(final Blokz game) {
		super(game);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Credits";
	}

	@Override
	public void goBack() {
		game.goToMainMenu();

	}

	public void renderSpecial() {
		drawSpecialText("Created by", 400, 2);
		drawSpecialText("Henrik Laurentz", 350, 3);
		drawSpecialText("Robin Silverhav", 300, 3);
		drawText("Press ESC to go back", 30, 1);
	}

	private void drawSpecialText(String str, int y, int fontSize) {
		batch.begin();
		font = FontHandler.font[fontSize];
		batch.setColor(Color.WHITE);
		font.draw(batch, str, Gdx.graphics.getWidth() / 2
				- font.getBounds(str).width / 2, y);
		batch.end();
	}

}
