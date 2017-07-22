package game;

import graphics.SpriteSheet;

import java.util.Random;

public class Screen {
	public int width;
	public int height;
	public int xOffset;
	public int yOffset;
	public int[] pixels;
	SpriteSheet spriteSheet;

	public Screen(int width, int height, SpriteSheet spriteSheet) {
		this.width = width;
		this.height = height;
		this.spriteSheet = spriteSheet;
		pixels = new int[width * height];

		fillPixels();
	}

	public void draw(Screen screen, int xOffs, int yOffs) {
		int yPix;
		int xPix;

		for (int y = 0; y < screen.height; y++) {
			yPix = y + yOffs;
			for (int x = 0; x < screen.height; x++) {
				xPix = x + xOffs;

				pixels[xPix + yPix * width] = screen.pixels[x + y
						* screen.width];
			}
		}
	}

	public void fillPixels() {
		Random random = new Random();
		for (int i = 0; i < width * height; i++) {
			pixels[i] = random.nextInt();
		}
	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void stripes(int step) {
		int counter = 0;

		for (int i = 0; i < pixels.length; i++) {
			counter++;
			if (counter < 3 * step) {
				pixels[i] = 100;
			} else {
				counter = 0;
			}
		}
	}

	public void render(int xx, int yy, int w, int h) {
		// stripes(10);

		xx -= xOffset;
		yy -= yOffset;

		Random random = new Random();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pixels[(x + xx) + (y + yy) * width] = random.nextInt(255);
			}
		}
	}

	public void drawPixel(int xx, int yy) {
		xx -= xOffset;
		yy -= yOffset;

		if (xx > 0 && yy > 0 && xx < width && yy < height) {
			pixels[xx + yy * width] = 255;
		}
	}
	
	private void swap(int a, int b) {
		int tmp;
		tmp = a;
		a = b;
		b= tmp;
	}
	
	public void drawLine(int x0, int y0, int x1, int y1) {
	    int w = x1 - x0 ;
	    int h = y1 - y0 ;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
	    
	    if (w < 0) dx1 = -1; else if (w < 0) dx1 = 1; 
	    if (h < 0) dy1 = -1; else if (h > 0) dy1 = 1 ;
	    if (w < 0) dx2 = -1; else if (w > 0) dx2 = 1 ;
	    
	    int longest = Math.abs(w) ;
	    int shortest = Math.abs(h) ;
	    
	    if (!(longest > shortest)) {
	        longest = Math.abs(h) ;
	        shortest = Math.abs(w) ;
	        if (h<0) {
	        	dy2 = -1;
	        } else if (h>0) {
	        	dy2 = 1 ;
	        }
	        
	        dx2 = 0 ;            
	    }
	    
	    int numerator = longest >> 1 ;
		
	    for (int i=0; i <= longest;i++) {
	        drawPixel(x0, y0);
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x0 += dx1 ;
	            y0 += dy1 ;
	        } else {
	            x0 += dx2 ;
	            y0 += dy2 ;
	        }
	    }
	}
	
	public void drawCircle(int xx, int yy, int radius) {
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * radius;
		int x = 0;
		int y = radius;

		drawPixel(xx, yy + radius);
		drawPixel(xx, yy - radius);
		drawPixel(xx + radius, yy);
		drawPixel(xx - radius, yy);

		while (x < y) {
			if (f >= 0) {
				y--;
				ddF_y += 2;
				f += ddF_y;
			}
			x++;
			ddF_x += 2;
			f += ddF_x;
			drawPixel(xx + x, yy + y);
			drawPixel(xx - x, yy + y);
			drawPixel(xx + x, yy - y);
			drawPixel(xx - x, yy - y);
			drawPixel(xx + y, yy + x);
			drawPixel(xx - y, yy + x);
			drawPixel(xx + y, yy - x);
			drawPixel(xx - y, yy - x);
		}
	}
}
