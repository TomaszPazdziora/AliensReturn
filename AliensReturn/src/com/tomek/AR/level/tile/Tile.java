package com.tomek.AR.level.tile;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile stars0 = new StarTile(Sprite.stars0);
	public static Tile stars1 = new StarTile(Sprite.stars1);
	public static Tile stars2 = new StarTile(Sprite.stars2);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
	}
}
