package com.tomek.AR.level.tile;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;

public class StarTile extends Tile {

	public StarTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
