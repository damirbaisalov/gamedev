package a1;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
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
	public static Sprite hero;
	private static final long serialVersionUID = 1;
	
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
		hero = getSprite("assets\\man.png");
	}
	
	public void update(long delta) {
		
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
		hero.draw(g, 20, 20);//newone drawing hero on graphics g
		g.dispose();
		bs.show();
		
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
	
	public static int WIDTH = 400;
	public static int HEIGHT = 300;
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
}
