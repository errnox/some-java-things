import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game {
    public static int WIDTH = 640;
    public static int HEIGHT= 480;
    private TrueTypeFont font;
    private Font awtFont;
    private Player player;
	Texture texture;
    
    public Game() {
    	player = new Player(340, 50, 80, 50);
    }

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
            
            awtFont = new Font("Monospaced", Font.PLAIN, 24);
            font = new TrueTypeFont(awtFont, false);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/spritesheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        long now = System.nanoTime();
        long last= System.nanoTime();
        double delta = 0f;
        double ns = 1000000000.0 / 60;
        int fps = 0;
        int ups = 0;
        long timer = System.currentTimeMillis();
      
        while (!Display.isCloseRequested()) {
            now = System.nanoTime();
            delta += (now - last) / ns;
            last = now;

            while(delta >= 1) {
                ups++;
                update();
                delta -= 1;
            }

            fps++;
            render();

            if (System.currentTimeMillis() - timer > 1000) {
//                System.out.println(fps + " fps, " + ups + " ups");
//                Display.setTitle(fps + " fps, " + ups + " ups");
                timer += 1000;
                fps = 0;
                ups = 0;
            }
        }

        Display.destroy();
    }

    public void render() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        glColor3f(0.5f, 0.5f, 1.0f);

        glBegin(GL_QUADS);
        glVertex2f(100, 100);
        glVertex2f(100+200, 100);
        glVertex2f(100+200, 100+200);
        glVertex2f(100, 100+200);
        glEnd();
        
        glBegin(GL_QUADS);
//      GLU.glutStrokeString(GLU.GLUT_STROKE_MONO_ROMAN, "foo");
        glEnd();

        glColor3f(0.4f, 0.8f, 0.3f);
        glBegin(GL_LINE);
        glTranslatef(150, 100, -30);
        Sphere s = new Sphere();
        s.draw(40, 10, 30);
        glTranslatef(-150, -100, 30);
        glEnd();

        font.drawString(200,  250, "This is a test.", Color.yellow);

        player.render();

//        texture.bind();

        BufferedImage image = null;
        try {
			image = ImageIO.read(new File("res/spritesheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
            	int pixel = pixels[y * image.getWidth() + x];
            	buffer.put((byte) ((pixel >> 16) & 0xFF));
            	buffer.put((byte) ((pixel >> 8) & 0xFF));
            	buffer.put((byte) (pixel & 0xFF));
            	buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
		}
        buffer.flip();
        glColor3f(0f, 0f, 255f);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, glGenTextures());
        glPixelStoref(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) image.getWidth(), (int) image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBegin(GL_QUADS);
        glTexCoord3f(0f, 0f, 0f);
        glVertex3f(320f, 200f, 0f);
        glTexCoord3f(1f, 0f, 0f);
        glVertex3f(420f+image.getWidth(), 200f, 0f);
        glTexCoord3f(1f, 1f, 0f);
        glVertex3f(420f+image.getWidth(), 280f+image.getHeight(), 0f);
        glTexCoord3f(0f, 1f, 0f);
        glVertex3f(320, 280f+image.getHeight(), 0f);
        glEnd();

//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) texture.getWidth(), (int) texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
//        glColor3f(0f, 0f, 255f);
//        glBegin(GL_QUADS);
//        glTexCoord3f(0f, 0f, 0f);
//        glVertex3f(320f, 200f, 0f);
//        glTexCoord3f(1f, 0f, 0f);
//        glVertex3f(420f+texture.getTextureWidth(), 200f, 0f);
//        glTexCoord3f(1f, 1f, 0f);
//        glVertex3f(420f+texture.getTextureWidth(), 280f+texture.getTextureHeight(), 0f);
//        glTexCoord3f(0f, 1f, 0f);
//        glVertex3f(320f, 280f+texture.getTextureHeight(), 0f);
//        glEnd();

        Display.update();
    }

    public void update() {
    	if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) player.moveLeft();
    	if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) player.moveRight();
    	if(Keyboard.isKeyDown(Keyboard.KEY_UP)) player.moveUp();
    	if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) player.moveDown();
    	
    	player.update();
    }

    public static void main(String[] argv) {
        Game displayExample = new Game();
        displayExample.start();
    }
}
