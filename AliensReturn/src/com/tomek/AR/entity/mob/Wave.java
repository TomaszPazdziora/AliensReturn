package com.tomek.AR.entity.mob;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import com.tomek.AR.entity.Entity;
import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;
import com.tomek.AR.level.Level;

public class Wave {

	public static final int ALIENS_IN_ROW = 12;
	
	public Alien [][]alienVec;
	public boolean [][]alive;
	public Alien []lastLine;
	private int attackSpeed = 200;
	public int rows;
	
	private static final Random random = new Random();
	public Vector<Bullet> bulletVec = new Vector<>();
	
	private final static int WAVE_lATENCY = 3;
	private int latency = 0;
	
	public Wave(int rows, int attackSpeed, int[] alienTypes) {
		this.rows = rows;
		this.attackSpeed = attackSpeed;
		this.alive = new boolean[rows][ALIENS_IN_ROW];
		this.alienVec = new Alien[rows][ALIENS_IN_ROW];
		this.lastLine = new Alien[ALIENS_IN_ROW];
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < ALIENS_IN_ROW; x++) {
				Sprite sprite;
				if (alienTypes[y] == 1) sprite = Sprite.purpleAlien;
				else sprite = Sprite.greenAlien;
				Alien a = new Alien((x + 1) * Entity.ALIEN_SIZE, y * Entity.ALIEN_SIZE, sprite);
				alienVec[y][x] = a;
				alive[y][x] = true;
			}
		}
	}
	 /*------------------- Aliens render methods  ----------------------------*/
	
	public void updateBullets() {
		for (int i = 0; i < bulletVec.size(); i++) {
			bulletVec.get(i).update();
		}
	}
	
	public void renderBullets(Screen screen) {
		for (int i = 0; i < bulletVec.size(); i++) {
			bulletVec.get(i).render(screen);
		}
	}
	
	public Point getBulletPosition(int counter) {
		int x = bulletVec.get(counter).x;
		int y = bulletVec.get(counter).y;
		
		return new Point(x,y);
	}
	
	/*---------------------------------------------------------------------------------------*/
	
	public boolean isAllAliensKilled() {
		for (int x = 0; x < ALIENS_IN_ROW - 1; x++) {
			for (int y = 0; y < rows; y++) {
				if(alive[y][x] == true)
					return false;
			}
		}
		return true;
	}
	
	// find bounding Aliens (needed in synchronization)
	
	public Point findLeftAlien() {
		for (int x = 0; x < ALIENS_IN_ROW - 1; x++) {
			for (int y = 0; y < rows; y++) {
				if(alive[y][x] == true)
					return new Point(x, y);
			}
		}
		return null;
	}
	
	public Point findRightAlien() {
		for (int x = ALIENS_IN_ROW - 1; x >= 0; x--) {
			for (int y = 0; y < rows; y++) {
				if(alive[y][x] == true)
					return new Point(x, y);
			}
		}
		return null;
	}
	
	
	public Alien findLastAlienInColumn(int column) {
		for (int y = rows - 1; y >= 0; y--) {
			if(alive[y][column] == true)
				return alienVec[y][column];
		}
		return null;
	}
	
	public void updateAlienLastLine() {
		for (int x = 0; x < ALIENS_IN_ROW; x++) {
			lastLine[x] = findLastAlienInColumn(x);
		}
	}
	
	public void alienAttack() {
		
		int []isShooting = new int[ALIENS_IN_ROW]; 
		for (int x = 0; x < ALIENS_IN_ROW; x++) {
			isShooting[x] = random.nextInt(attackSpeed);
		}
		
		for (int x = 0; x < ALIENS_IN_ROW; x++) {
			if(lastLine[x] != null &&
			   isShooting[x] == 0) {
				Sprite bs;
				if (this.lastLine[x].sprite == Sprite.greenAlien) bs = Sprite.greenAlienBullet;
				else bs = Sprite.purpleAlienBullet;
					
				Bullet b = new Bullet(lastLine[x].x, lastLine[x].y, 5, bs);
				bulletVec.add(b);
			}
		}
		
	}
	
	// change direction in Y axis 
	
	public void changeDirection() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < ALIENS_IN_ROW; x++) {
				
				if (alienVec[y][x].direction == Entity.RIGHT) {
					alienVec[y][x].direction = Entity.LEFT;
					alienVec[y][x].move(0, Level.ALIEN_Y_JUMP);
				}
				else {
					alienVec[y][x].direction = Entity.RIGHT;
					alienVec[y][x].move(0, Level.ALIEN_Y_JUMP);
				}
			}
		}
	}
	
	public void updateAllIfAlive() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < ALIENS_IN_ROW; x++) {
				if (alive[y][x])
					alienVec[y][x].update();
			}
		}
	}
	
	// synchronizacja wszystkich obcych
	
	public void synchronize() {
		
		Point left = findLeftAlien();
		Point right = findRightAlien();
		
		if (alienVec[left.y][left.x].x <= Level.MAP_X0_BOUND
		 || alienVec[right.y][right.x].x >= Level.MAP_X1_BOUND) {
			changeDirection();
		}
	}
	
	public void update() {
		
		alienAttack();
		updateBullets();
	
		if (latency == WAVE_lATENCY) {
			synchronize();
			updateAllIfAlive();
			updateAlienLastLine();
			latency = 0;
		}
		
		else
			latency++;
	}
	
	public void render(Screen screen) {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < ALIENS_IN_ROW; x++) {
				if (alive[y][x])
					alienVec[y][x].render(screen);
			}
		}
		renderBullets(screen);
	}
	
	public void killAlien(Point p) {
		alive[p.y][p.x] = false;
	}
	
	public Point getAlienPosition(Point p) {
		int x = alienVec[p.y][p.x].x;
		int y = alienVec[p.y][p.x].y;
		
		return new Point(x, y);
	}
	
	
}
