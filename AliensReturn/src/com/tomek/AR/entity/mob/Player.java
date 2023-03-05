package com.tomek.AR.entity.mob;

import java.awt.Point;
import java.util.Vector;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;
import com.tomek.AR.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	
	public Vector<Bullet> bulletVec = new Vector<>();
	
	public boolean alive = true;
	private boolean shoot = false;
	private int playerSpeed = 2;
	
	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player;
	}
	
	public Player(int x, int y, Keyboard input) {	
		this.x = x;
		this.y = y;
		this.input = input;
	}
	
	 /*------------------- Metody do renderowania pocisków gracza ----------------------------*/
	
	public void updateBullets() {
		for (int i = 0; i < bulletVec.size(); i++) {
			bulletVec.get(i).update();
		}
	}
	
	public void renderBullets(Screen screen) {
		for (int i = 0; i < bulletVec.size(); i++) {
//			if (bulletVec.get(i).y < 0)
//				bulletVec.remove(i);
			
			bulletVec.get(i).render(screen);
		}
	}
	
	public Point getBulletPosition(int counter) {
		int x = bulletVec.get(counter).x;
		int y = bulletVec.get(counter).y;
		
		return new Point(x,y);
	}
	
	/*---------------------------------------------------------------------------------------*/
	
	public void update() {
		
		int xa = 0;
		if (input.left) xa -= playerSpeed;
		if (input.right) xa += playerSpeed;
		if (xa != 0) move(xa, 0);
		
		if (input.space) 
			shoot = true;
		
		if (!input.space && shoot) {
			Bullet b = new Bullet(this.x, this.y, UP, Sprite.playerBullet);
			bulletVec.add(b);
			shoot = false;
		}
			
		updateBullets();
	}
	
	public void render(Screen screen) {
		if (dir == LEFT) sprite = Sprite.player;
		if (dir == RIGHT) sprite = Sprite.player;
		screen.renderMob(x - 16, y - 16, PLAYER_SIZE, sprite);
		renderBullets(screen);
	}
	
}
