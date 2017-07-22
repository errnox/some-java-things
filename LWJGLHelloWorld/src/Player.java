import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;


public class Player {
	private int x;
	private int y;
	private int w;
	private int h;
	private double speed;
	
	public Player(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		speed = 12.0;
	}
	
	public void render() {
		glBegin(GL_QUADS);
		glColor3f(0.85f, 0.15f, 0.2f);
		glVertex2f(x, y + h);
		glVertex2f(x + w, y + h);
		glVertex2f(x + w, y);
		glVertex2f(x, y);
		glEnd();
		
//		glBegin(GL_LINE_LOOP);
//		glColor3f(0.85f, 0.15f, 0.2f);
//		glVertex3f(x, y, 0);
//		glVertex3f(x, y-h, 0);
//		glVertex3f(x+w, y-h, 0);
//		glVertex3f(x+w, y, 0);
//		glEnd();
}
	
	public void moveLeft() {
		x -= 1 * speed;
	}
	
	public void moveRight() {
		x += 1 * speed;
	}
	
	public void moveUp() {
		y += 1 * speed;
	}
	
	public void moveDown() {
		y -= 1 * speed;
	}

	public void update() {
		if(x + w < 0) x = Game.WIDTH + w * 2 - 1;
		if(x > Game.WIDTH + w * 2) x = 0;
		if(y + h < 0) y = Game.HEIGHT+ h * 2 - 1;
		if(y > Game.HEIGHT+ h* 2) y = 0;
	}
}
