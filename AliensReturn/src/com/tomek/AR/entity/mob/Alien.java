package com.tomek.AR.entity.mob;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;

	public class Alien extends Mob {
		
		public Sprite sprite;
		private int speed = 1;
		public int direction = RIGHT;
		
		public Alien(int x, int y, Sprite sprite) {
			this.x = x;
			this.y = y;
			this.sprite = sprite;
		}
		
	public void update() {
			if (direction == RIGHT) move(speed, 0);
			else move(-speed, 0);
		}
	
	public void render(Screen screen) {
			screen.renderMob(x - 8, y, ALIEN_SIZE, this.sprite);
		}
}
