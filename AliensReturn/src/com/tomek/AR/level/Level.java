package com.tomek.AR.level;

import java.awt.Point;

import com.tomek.AR.entity.Entity;
import com.tomek.AR.entity.mob.Alien;
import com.tomek.AR.entity.mob.Bullet;
import com.tomek.AR.entity.mob.Player;
import com.tomek.AR.entity.mob.Wave;
import com.tomek.AR.graphics.Screen;
import com.tomek.AR.input.Keyboard;
import com.tomek.AR.level.tile.Tile;

public class Level {

	private Player player;
	public Wave wave;
	
	// width i height are counted in tiles
	protected int width, height; 
	protected int[] tiles;
	
	public static final int MAP_X0_BOUND = 10;
	public static final int MAP_X1_BOUND = 214;
	public static final int ALIEN_Y_JUMP = 10;
	
	public static final int X_SHOOT_OFFSET = 12;
	public static final int Y_SHOOT_OFFSET = 8;
	
	public Level(int width, int height, int attackSpeed, Screen screen, Keyboard key) {
		this.width = width;
		this.height = height;
		tiles = new int [width * height];

		player = new Player(width * 16 / 2, height * 16 - 48, key);
		int []arr = {1,0,1,0,1};
		wave = new Wave(5, attackSpeed, arr);
		
		generateLevel();
	}

	public Level(String path) {
		loadLevel();
	}
	
	protected void generateLevel() {
	}
	
	private void loadLevel() {
	}
	
	public void update() {
		player.update();
		wave.update();
		isAlienKilled();
		isPlayerKilled();
	}
	
	public void render(Screen screen) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
			player.render(screen);
			wave.render(screen);
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0) return Tile.stars0;
		if (tiles[x + y * width] == 1) return Tile.stars1;
		if (tiles[x + y * width] == 2) return Tile.stars2;
		return Tile.voidTile;
	}
	
	// to consider
	// too big computational complexity
	
	public void isAlienKilled() {
		for (int i = 0; i < player.bulletVec.size(); i++) {
			for (int y = 0; y < wave.rows; y++) {
				for (int x = 0; x < Wave.ALIENS_IN_ROW; x++) {
					if (!wave.alive[y][x])
						continue;
					Point p = new Point(x, y);
					if (isShooted(wave.alienVec[y][x], player.bulletVec.get(i))) {
						if (!player.bulletVec.get(i).used) {
							wave.killAlien(p);
							player.bulletVec.get(i).used = true;
						}
					}
				}
			}
		}
	}
	
	public boolean isPlayerKilled() {
		for (int i = 0; i < wave.bulletVec.size(); i++) {
			if (isShooted(player, wave.bulletVec.get(i))) {
				return true;
			}
		}
		return false;
	}
		
	public boolean isShooted(Entity a, Bullet b) {
			if((b.y - a.y <= Y_SHOOT_OFFSET  && b.y - a.y >= -Y_SHOOT_OFFSET) &&
			   (b.x - a.x <= X_SHOOT_OFFSET && b.x - a.x >= - X_SHOOT_OFFSET))
				return true;
		return false;
	}
	
	public boolean areAlienReturn() {
		for (int i = 0; i < wave.lastLine.length; i++) {
			if (readLastLineY(i) == player.y)
				return true;
		}
		return false;
	}
	
	public int readLastLineY(int counter) {
		try {
			return wave.lastLine[counter].y;
		} catch (Exception e) {
			return 1024;
		}
	}
}
