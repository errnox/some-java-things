package entity;

import game.Game;
import game.InputHandler;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity {
	public int x;
	public int y;
	public int oldX;
	public int oldY;
	public boolean isActive;
	private InputHandler inputHandler;
	private Game game;
	private float speed;
	private Graphics g;
	private static final int LEFT = 0;
	private static final int UP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	private int direction = LEFT;
	private Thread animator = null;
	int radius;
	int rotation;
	boolean animate;
	
	public Player(Game game, InputHandler inputHandler) {
		this.game = game;
		this.inputHandler = inputHandler;
		x = 0;
		y = 0;
		isActive = false;
		speed = 1;
		radius = 5;
		rotation = 0;
		animate = false;

		this.inputHandler = inputHandler;
		g = game.getGraphics();
	}

	public void render() {
		if (inputHandler.left.down) {
			x -= 1 * speed;
		}

		if (inputHandler.right.down) {
			x += 1 * speed;
		}

		if (inputHandler.up.down) {
			y -= 1 * speed;
		}

		if (inputHandler.down.down) {
			y += 1 * speed;
		}

		if (inputHandler.spacebar.down) {
			rotate(true);
		}

		if (!inputHandler.spacebar.down) {
			isActive = false;
		}
	}

	public void animate() {
		animator = new Thread();
		animator.start();

		if (animator != null) {
			if (!isActive) {
				direction = RIGHT;
				if (direction == RIGHT) {
					for (int i = 0; i < 30; i++) {
						x++;
						// draw(g);

						try {
							animator.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						game.render();

					}
					direction = DOWN;
				}

				if (direction == DOWN) {
					for (int i = 0; i < 20; i++) {
						y++;

						try {
							animator.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						game.render();
					}
					direction = LEFT;
				}
				animator = null;
			}
		}
	}

	public void rotate(boolean clockwise) {
		animator = new Thread();
		animator.start();
		animate = true;
		int direction = 0;
		
		if (clockwise) {
			direction = 1;
		} else {
			direction = -1;
		}

		if (animator != null) {
			if (!isActive) {
//				while (rotation < 50) {
				while (animate) {
					System.out.println(rotation);
					if (rotation < 45) {
						rotation++;
						try {
							animator.sleep(15);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						rotation = 0;
						animate = false;
					}

					x = (int) (x + direction * (radius * Math.sin(rotation * 2400)));
					y = (int) (y + radius * Math.cos(rotation * 2400));
					
					game.render();
				}
				rotation = 0;
				animator = null;
			}
		}
	}

	public void draw(Graphics g) {
		this.g = g;
		g.setColor(new Color(0, 255, 0));
		g.fillRect(x, y, 10, 10);
	}
}
