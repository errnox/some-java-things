package scrapbook;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final int WIDTH = 32 * 10;
	private static final int HEIGHT = 32 * 5;
	private static final int SCALE = 3;
	private Random random = new Random();
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private BufferedImage spritesheetImage = null;
	private int[] spritesheet = null;
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private boolean running = false;

	private BufferedImage img = null;
	
	public Game() {
		try {
			ImageIO.setUseCache(false);
			spritesheetImage = ImageIO.read(getClass().getResourceAsStream("/spritesheet.png"));
			spritesheet = spritesheetImage.getRGB(0, 0, spritesheetImage.getWidth(), spritesheetImage.getHeight(), null, 0, spritesheetImage.getWidth());
			for (int i = 0; i < spritesheet.length; i++) {
				if (spritesheet[i] == 0) {
					spritesheet[i] = 0x000000;
				}
			}
		} catch (Exception e) {
			System.err.println("Spritesheet could not be loaded: " + e);
		}
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFF00FF;
		}

		JFrame jframe = new JFrame("Game");
		jframe.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLayout(new BorderLayout());
		jframe.add(this, BorderLayout.CENTER);
		jframe.pack();
		jframe.setResizable(true);
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		double frames = 0;
		double ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed = (now - lastTime) / nsPerTick;
			lastTime = now;

			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			render();
			frames++;

			clear();
			int w = 20;
			int h = 40;
			int xp = (int) (w * 3 + Math.sin(System.currentTimeMillis() % 2000.0 / 2000 * Math.PI * 4) * 60);
			int yp = (int) (h * 2 + -Math.cos(System.currentTimeMillis() % 2000.0 / 2000 * Math.PI * 4) * 60);
			drawRect(xp, yp, w, h, 0xFF00AF);
			drawRect(xp + 30, yp + 40, w + 10, h - 20, 0x12AF21);

			manipulateSpritesheet();
			manipulatePixels();

			if (System.currentTimeMillis() - lastTimer > 10000) {
				lastTimer += 1000;
				printFPS(ticks, frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void printFPS(double ticks, double frames) {
		System.out.println("Ticks: " + ticks + " - FPS: " + frames);
	}

	private void tick() {
	}

	public void start() {
		this.running = true;
		new Thread(this).start();
	}

	private void manipulateImage() {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rand = random.nextInt(image.getWidth());
				if (rand != 0) {
					image.setRGB(x, y, 0xFF0000 / rand);
				}
			}
		}
	}

	private void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

	private void drawRect(int xp, int yp, int w, int h, int color) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				if (x >= xp && y >= yp && x <= (xp + w) && y <= (yp + h)) {
					pixels[y * image.getWidth() + x] = color;
				}
			}
		}
	}

	private void manipulatePixels() {
		int[] colors = {0xFF00FF, 0xFFFF00, 0x00FFF, 0xFF0000, 0x000000, 0xFFFFFF,};
		int choice = 0;
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				choice = random.nextInt(colors.length);
				// if (x % 2 == 0 && y % 2 == 0) {
				// if (pixels[y * image.getWidth() + x] != 0x000000) {
				// try {
				// choice = random.nextInt(colors.length);
				// pixels[y * image.getWidth() + x] = colors[choice];
				// } catch (Exception e) {
				// // Ignore it.
				// }
				// }
				// }
				if (Integer.toHexString(pixels[y * image.getWidth() + x]).matches("ff00ff00") && System.currentTimeMillis() % 16 == 0) {
					pixels[y * image.getWidth() + x] = colors[choice];
				}
			}
		}
	}

	private void manipulateSpritesheet() {
		int xp = (int) (10 + System.currentTimeMillis() % 2000 / 100 * Math.PI);
		int yp = (int) (30 + System.currentTimeMillis() % 2000 / 100 * random.nextGaussian() / 10);
		ArrayList<Integer> original = new ArrayList<Integer>();
		ArrayList<Integer> tile = new ArrayList<Integer>();
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				original.add(spritesheet[y * spritesheetImage.getWidth() + x]);
			}
		}
		int[] row = new int[16];
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				for (int i = 0; i < 16; i++) {
					row[i] = original.get(y * 16 + i);
				}
				// tile.add(original.get(y * 16 + x));
			}
			for (int i = row.length - 1; i >= 0; i--) {
				tile.add(row[i]);
			}
		}
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				if (tile.get(y * 16 + x) != 0x000000) {
					try {
						pixels[y * image.getWidth() + x + yp * image.getWidth() + xp] = tile.get(y * 16 + x);
					} catch (Exception e) {
						// Ignore it.
					}
				}
			}
		}

		int[] imagePixels = new int[16 * 16];
		for (int i = 0; i < original.size(); i++) {
			if (imagePixels[i] != 0x000000) {
				imagePixels[i] = 0;
			} else {
				imagePixels[i] = original.get(i);
			}
		}
		img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, 16, 16, imagePixels, 0, 16);
	}
	private void render() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics g = bufferStrategy.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth() * SCALE, image.getHeight() * SCALE, null);
		
//		double rot = Math.sin(45);
//		double xr = image.getWidth() / 2;
//		double yr = image.getHeight() / 2;
//		AffineTransform tx = AffineTransform.getRotateInstance(rot, xr, yr);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		g.drawImage(op.filter(image, null), 20, 30 ,null);

		// g.drawImage(spritesheetImage, 0, 0, image.getWidth() * SCALE,
		// image.getHeight() * SCALE, null);
		
		int randX = 0;
		int randY = 0;
		int mask = 0x88FF0000;
		int color = (112 << 24) | (0 << 16) | (0 << 8) | 0;
//		int color = (120 << 16) | (120 << 8) | 120;
//		color &= mask;
//		System.out.println("BEFORE: " + Integer.toBinaryString(color));
		color += (255 << 16) + (255 << 0) + (((color >> 8) + 100) << 24);
		System.out.println((color >> 16) & 0xFF);
//		System.out.println("AFTER: " + Integer.toBinaryString(color));
		for (int i = 0; i < 200; i++) {
			randX = (int) (System.currentTimeMillis() % 8000 * 2 / 4000 + random.nextInt(getWidth() - 16));
			randY = (int) (System.currentTimeMillis() % 8000 * 2 / 4000 + random.nextInt(getWidth() - 16));
			g.setColor(new Color(color, true));
//			g.setColor(new Color(color));
			g.fillRect(randX, randY, 16 * SCALE, 16 * SCALE);
			g.drawImage(img, randX, randY, 16 * SCALE, 16 * SCALE, null);
		}
		
		g.dispose();
		bufferStrategy.show();
	}
}
