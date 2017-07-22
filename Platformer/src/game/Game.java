package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import entity.Player;

public class Game extends Canvas implements Runnable {
	private static final String NAME = "Platformer";
	private boolean running;
	private int tickCount;
	private Player player;
	private InputHandler inputHandler = new InputHandler(this);
	private static final int WIDTH = 160;
	private static final int HEIGHT = 120;
	private static final int SCALE = 3;
	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public Graphics g;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		this.run();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		init();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks - " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		player.render();
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(player.x + 10, player.y, 10, 10);
		
		player.draw(g);
		
		g.dispose();
		bs.show();
	}
	
	private void tick() {
		inputHandler.tick();
		tickCount++;
	}

	private void init() {
		player = new Player(this, inputHandler);
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.run();

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		game.start();
	}

	private void start() {
		running = true;
		new Thread(this).start();
	}
}
