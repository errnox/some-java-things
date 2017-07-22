package game;

import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener {
	public ArrayList<Key> keys = new ArrayList<Key>();

	public Key left= new Key();
	public Key up = new Key();
	public Key right = new Key();
	public Key down = new Key();
	public Key spacebar= new Key();

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public class Key {
		public int presses;
		public int absorbs;
		public boolean down;
		public boolean clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}

			if (pressed) {
				presses++;
			}
		}
		
		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}
	
	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	public void toggle(KeyEvent e, boolean pressed) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) spacebar.toggle(pressed);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
