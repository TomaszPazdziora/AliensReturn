package com.tomek.AR.entity.mob;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;

public class Bullet extends Mob{
	
	public Sprite sprite;
	public boolean used = false;
	private int direction = UP;
	private final int speed = 3;
	
	public Bullet(int x, int y, int direction, Sprite bulletSprite) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		sprite = bulletSprite;
	}
	
public void update() {
		if (direction == UP) move(0, -speed);
		else move(0, speed);
	}

public void render(Screen screen) {
		if (!used) 
			screen.renderBullet(x, y, 2, 6, sprite);
	}

}
