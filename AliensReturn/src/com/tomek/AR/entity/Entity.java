package com.tomek.AR.entity;

import java.util.Random;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.level.Level;

public abstract class Entity {

	public static final int LEFT = 4;
	public static final int RIGHT = 6;
	public static final int UP = 8;
	public static final int DOWN = 5;
	
	public static final int PLAYER_SIZE = 32;
	public static final int ALIEN_SIZE = 16;
	public static final int BULLET_SIZE = 16;
	
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
}
