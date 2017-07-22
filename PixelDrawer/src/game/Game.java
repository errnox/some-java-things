package game;

import graphics.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final int WIDTH = 160;
	private static final int HEIGHT = 120;
	private static final int SCALE = 3;
	private boolean running;
	private int tickCounter = 0;
	private Graphics g;
	private Screen screen;
	private BufferedImage image;
	private int[] pixels;
	private int[] colors;

	public Game() {
		this.run();
	}

	private void start() {
		running = true;
		new Thread(this).start();
	}

	public void init() {
		SpriteSheet spriteSheet = null;
		try {
			spriteSheet = new SpriteSheet(ImageIO.read((this.getClass()
					.getResourceAsStream("/spriteSheet.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		screen = new Screen(WIDTH, HEIGHT, spriteSheet);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		colors = new int[256];
	}

	@Override
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
				System.out.println("ticks: " + ticks + " fps: " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		screen.clear(0);

		screen.render(10, 20, 10, 20);
		screen.render(30, 80, 30, 20);

		Random random = new Random();

		for (int i = 0; i < 15; i += random.nextInt(5)) {
			screen.drawCircle(50, 20, i);
		}

		screen.drawLine(180, 60, 30, 10);

		// screen.render();

		g = bs.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(10, 20, 100, 200);

		// int ww = WIDTH * 3;
		// int hh = HEIGHT * 3;
		// int xx = (getWidth() - ww) / 2;
		// int yy = (getHeight() - hh) / 2;
		// g.drawImage(image, xx, yy, ww, hh, null);

		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		g.dispose();
		bs.show();
	}

	private void tick() {
		tickCounter++;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.run();

		JFrame frame = new JFrame();
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
}
