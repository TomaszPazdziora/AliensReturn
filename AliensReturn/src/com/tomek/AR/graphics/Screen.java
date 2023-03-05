package com.tomek.AR.graphics;
import java.util.Random;

import com.tomek.AR.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	public int xOffset, yOffset;
	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for (int i = 0; i< MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] =  random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0; 
		}
	}
	

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE_Y; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE_X; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE_X || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE_X]; 
			}
		}
	}
	
	public void renderMob(int xp, int yp, int size, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < size; y++) {
			int ya = y + yp;
			for (int x = 0; x < size; x++) {
				int xa = x + xp;
				if (xa < - size || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * size];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col; 
			}
		}
	}
	
	public void renderBullet(int xp, int yp, int sizeX, int sizeY, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sizeY; y++) {
			int ya = y + yp;
			for (int x = 0; x < sizeX; x++) {
				int xa = x + xp;
				if (xa < - sizeX || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sizeX];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col; 
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}









