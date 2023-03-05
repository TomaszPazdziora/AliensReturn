package com.tomek.AR.graphics;

public class Sprite {

	public final int SIZE_X;
	public final int SIZE_Y;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite stars0 = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite stars1 = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite stars2 = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x000000);
	
	public static Sprite purpleAlien = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite greenAlien = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite player = new Sprite(32, 0, 7, SpriteSheet.tiles);
	public static Sprite bigAlien = new Sprite(128, 1, 0, SpriteSheet.tiles);
	

	public static Sprite playerBullet = new Sprite(2, 6, 0x0000ffff );
	public static Sprite purpleAlienBullet = new Sprite(2, 6, 0x00ff00ff );
	public static Sprite greenAlienBullet = new Sprite(2, 6, 0x0000ff00 );
	
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE_X = size;
		SIZE_Y = size;
		pixels = new int [SIZE_X * SIZE_Y];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int size, int colour) {
		SIZE_X = size;
		SIZE_Y = size;
		pixels = new int[SIZE_X * SIZE_Y];
		setColour(colour);
	}
	
	public Sprite(int sizeX, int sizeY, int colour) {
		SIZE_X = sizeX;
		SIZE_Y = sizeY;
		pixels = new int[SIZE_X * SIZE_Y];
		setColour(colour);
	}
	
	private void setColour(int colour) {
		for (int i = 0; i < SIZE_X * SIZE_Y; i++) {
			pixels[i] = colour;
		}
	}
	
	private void load() {
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				pixels[x+y*SIZE_Y] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}
