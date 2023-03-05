package com.tomek.AR.entity.mob;

import com.tomek.AR.entity.Entity;
import com.tomek.AR.graphics.Sprite;
import com.tomek.AR.level.Level;

public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	protected int dir = LEFT;
	protected boolean moving = false;
	
	public void move(int xa, int ya) {
		if (xa > 0) dir = LEFT;
		if (xa < 0) dir = RIGHT;
		if (ya > 0) dir = DOWN;
		if (ya < 0) dir = UP;
		
		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}
	
	private boolean collision(int xa, int ya) {
		if (x + xa >= Level.MAP_X0_BOUND && x + xa <= Level.MAP_X1_BOUND)
			return false;
		return true;
	}
	
	
	public void update() {
	}
	
	
	
	public void render() {
	}
}
