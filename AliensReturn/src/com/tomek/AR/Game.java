package com.tomek.AR;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.Spring;

import com.tomek.AR.entity.mob.Bullet;
import com.tomek.AR.entity.mob.Player;
import com.tomek.AR.entity.mob.Wave;
import com.tomek.AR.graphics.Screen;
import com.tomek.AR.graphics.Sprite;
import com.tomek.AR.input.Keyboard;
import com.tomek.AR.level.Level;
import com.tomek.AR.level.RandomLevel;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int TILE_SIZE = 16;
	
	public static int width = 224;
	public static int height = width / 9 * 16;
	public static int scale = 3;
	public static String title = "Aliens Return";
	
	private Thread thread;
	private JFrame frame;
	private Level level;
	private boolean running = false;
	private Screen screen;
	private Keyboard key;
	
	
	private int gameState = 0;
	private final static int MENU_MODE = 0;
	private final static int LEVEL_MODE = 1;
	private final static int GAME_MODE = 2;
	private final static int INFO_MODE = 3;
	
	private boolean won = false;
	
	private boolean enterClicked = false;
	private boolean upClicked = false;
	private boolean downClicked = false;
	
	private int menuLoc = 0;
	
	private String gameTitle = "ALIENS RETURN";
	
	private String infoWin = "GOOD JOB, YOU STOPPED THEM!";
	
	private String infoLose = "YOU LOSE!";
	
	private String dificultyLevek = "CHOSE YOUR DIFICULTY LEVEL:";
	
	private String[] menuText = {"New Game",
								 "Exit"
	};
	
	private String[] menuInstuction = {"Use keys to navigate",
			 						   "Enter/space to select/shoot",
	};
	
	private String[] infoText = {"Try again",
			 					"Exit"
	};
	
	private String[] levelTxt = {"Easy",
				"Medium",
				"Difficult"
	};
	
	long lastTime = System.nanoTime();
	final double ns = 1000000000.0 / 60.0;
	double delta = 0;
	
	//private int lapce = 0;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		addKeyListener(key);
		level = new RandomLevel(width / TILE_SIZE, height / TILE_SIZE, 200, screen, key);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public void renderMenu(String title, String[] menuTx, int pos) {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		screen.renderMob(80, 50, 128, Sprite.bigAlien);
		
		int xTextShift = 100;
		int yTextShift = 500;
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image,0 ,0, getWidth(), getHeight(), null);
		g.setColor(Color.GREEN);
		
		g.setFont(new Font ("Verdana", 0, 30));
		g.drawString(title, xTextShift, yTextShift);
		yTextShift += 50;
		
		g.setFont(new Font ("Verdana", 0, 24));
		for (int i = 0; i < menuTx.length; i++) {
			if (pos == i) {
				g.drawString("> " + menuTx[i], xTextShift, yTextShift + i * 30);
				continue;
			}
			g.drawString(menuTx[i], xTextShift, yTextShift + i * 30);
		}
		yTextShift += menuTx.length * 30;
		
		g.setFont(new Font ("Verdana", 0, 15));
		for (int i = 0; i < menuInstuction.length; i++) {
			g.drawString(menuInstuction[i], xTextShift, yTextShift + i * 20);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void menuMode() {
		menuLoc = 0;
		requestFocus();
		
		while (true) {
			countTime();
			if (delta >= 1) {
				key.update();
				delta--;
				updateMenuPos(menuText);
				
				if (isChosen()) {
					if (menuLoc == 0)
						levelMode();
					else if (menuLoc == 1) 
						running = false;
					break;
				}
			}
			renderMenu(gameTitle ,menuText, menuLoc);
		}
	}
	
	public boolean isChosen() {
		if (key.space)
			enterClicked = true;
		else if (!key.space && enterClicked) {
			upClicked = false;
			downClicked = false;
			enterClicked = false;
			return true;
		}
		
		return false;
	}
	
	public void updateMenuPos(String[] options) {
		//check if clicked
		if (key.up && menuLoc > 0)
			upClicked = true;
		//update possition if button relesed
		else if (!key.up && menuLoc > 0 && upClicked) {
			menuLoc--;
			upClicked = false;
		}
		
		if (key.down && menuLoc < options.length - 1)
			downClicked = true;
		else if (!key.down && menuLoc < options.length - 1 && downClicked) {
			menuLoc++;
			downClicked = false;
		}
	}
	
	public void levelMode() {
		menuLoc = 0;
		requestFocus();
		
		while (true) {
			countTime();
			if (delta >= 1) {
				key.update();
				delta--;
				updateMenuPos(levelTxt);
				
				if (isChosen()) {
					if (menuLoc == 0)
						level = new RandomLevel(width / TILE_SIZE, height / TILE_SIZE, 300, screen, key);
					else if (menuLoc == 1) 
						level = new RandomLevel(width / TILE_SIZE, height / TILE_SIZE, 200, screen, key);
					else if (menuLoc == 2) 
						level = new RandomLevel(width / TILE_SIZE, height / TILE_SIZE, 100, screen, key);
					
					gameState = GAME_MODE;
					break;
				}
			}
			renderMenu(dificultyLevek, levelTxt, menuLoc);
		}
	}
	
	public void countTime() {
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
	}
	
	public void gameMode() {
		requestFocus();
		while (true) {
			
			countTime();
			
			if (delta >= 1) {
				update();
				delta--;
			}
			if (level.isPlayerKilled() ||
				level.wave.isAllAliensKilled() ||
				level.areAlienReturn()) {
				
				if (level.wave.isAllAliensKilled())
					won = true;
				else 
					won = false;
				gameState = INFO_MODE;
				break;
			}
			renderGame();
		}
	}
	
	
	public void infoMode() {
		menuLoc = 0;
		requestFocus();
		
			while(true) {
				countTime();
				if (delta >= 1) {
					key.update();
					delta--;
					updateMenuPos(infoText);
					
					if (isChosen()) {
						if (menuLoc == 0) {
							gameState = LEVEL_MODE;
						}
						else if (menuLoc == 1) 
							running = false;
						break;
					}
				}
				if(won)
					renderMenu(infoWin ,infoText, menuLoc);
				else
					renderMenu(infoLose ,infoText, menuLoc);
			}
		}

	@Override
	public void run() {
		while(running) {
			if(gameState == MENU_MODE)
				menuMode();
			
			if(gameState == LEVEL_MODE)
				levelMode();
			
			else if(gameState == GAME_MODE)
				gameMode();
			
			else if(gameState == INFO_MODE) {
				infoMode();
			}
		}
		frame.dispose();
	}
	
	
	public void update() {
		key.update();
		level.update();
	}

	public void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		level.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0 , 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}
