import processing.core.PApplet;


public class Game extends PApplet {
	public void settings() {
		size(640, 480);
	}

	public void setup() {
	}

	public void draw() {
		pushStyle();
		background(0, 0, 0);
		fill(255, 0, 0);
		noStroke();
		rect(50, 50, 50, 50);
		popStyle();
	}

	public static void main(String[] args) {
		PApplet.main("Game");
	}
}
