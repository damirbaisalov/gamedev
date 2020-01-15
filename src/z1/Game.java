package z1;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Game extends Canvas implements Runnable{
	private boolean running ;
	
	private static int x = 0;
	private static int y = 0;

	public static Sprite hero;
	private boolean leftPressed = false;
	private boolean rightPressed = false; 
	private boolean downPressed = false;
	private boolean upPressed = false;
	
	private static final long serialVersionUID = 1L;
	

	
	public void run() {
		long lastTime = System.currentTimeMillis();
		long delta;
		
		init();
		
		while(running) {
			delta = System.currentTimeMillis()-lastTime;
			lastTime = System.currentTimeMillis();
			update(delta);
			render();
		}
	}
	
	public void init() {
		hero = getSprite("man.png");
		addKeyListener(new KeyInputHandler());
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); //BUFFERSTRATEGY
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics(); //WHAT IS GRAPHICS
		
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		hero.draw(g, x, y);//newone drawing hero on graphics g
		g.dispose();
		bs.show();
		
	}
	
	
	public void update(long delta) {
		if (leftPressed == true) {
			x--;
			if (x < 0) {
				x=WIDTH;
			}
		}
		if (rightPressed == true) {
			x++;
			if (x >= WIDTH) {
				x=0;
			}
		}
		if (downPressed == true) {
			y++;
			if (y >= HEIGHT) {
				y=0;
			}
		}
		if (upPressed == true) {
			y--;
			if (y < 0) {
				y=HEIGHT;
			}
		}
		
	}
	
	
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public Sprite getSprite(String path) { //WHAT THE HELL IS THIS FUNCTION?????
		BufferedImage sourceImage = null;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(path);
			sourceImage = ImageIO.read(url);
			}
		catch (IOException e) { 
			e.printStackTrace();
		}
		
		Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
	
		return sprite;
	}
	
	public static int WIDTH = 1000;
	public static int HEIGHT = 800;
	public static String NAME = "TUTORIAL 1";
	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH,HEIGHT)); //DIMENSION
		
		JFrame frame = new JFrame(Game.NAME); // WHAT IS JFRAME
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		game.start();
	}
	
	private class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN){
				downPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			}
		} 
		
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
		}
	}
	
}
