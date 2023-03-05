package com.tomek.AR.level;

import java.util.Random;

import com.tomek.AR.graphics.Screen;
import com.tomek.AR.input.Keyboard;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height, int attackSpeed, Screen screen, Keyboard key) {
		
		super(width, height, attackSpeed, screen, key);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++ ) {
			for (int x = 0; x < width; x++ ) {
				tiles[x + y * width] = random.nextInt(8);
			}
		}
	}

}
