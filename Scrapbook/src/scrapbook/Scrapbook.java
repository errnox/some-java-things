package scrapbook;

import java.awt.BorderLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Scrapbook {
	private static String name = "Test Scrapbook";

	public static void main(String[] args) {
		Scrapbook scrapbook = new Scrapbook();
		// scrapbook.colors();
		// scrapbook.showMehods();
		// scrapbook.array();
		// scrapbook.test_math();

		Game game = new Game();
		game.start();
		game.run();
		
//		scrapbook.testShifting();
	}

	private void testShifting() {
		int x = 20;
		int y = 4;
		int w = 20;
		int h = 600;
		int[] values = null;
		int value = 333;
//		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
		for (int i = 0; i < 20; i++) {
			System.out.println(i + ": " + (x & i));
		}
//		System.out.println((x & (w - 1)) + (y & (h - 1)) * w);
	}
	
	private void colors() {
		int pp = 0;
		int[] colors = new int[256];
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
					colors[pp++] = r1 << 16 | g1 << 8 | b1;

				}
			}
		}

		for (int i = 0; i < colors.length; i++) {
			System.out.println(colors[i]);
		}
	}

	private void array() {
		ArrayList<String> array = new ArrayList<String>();
		array.add("one");
		array.add("two");
		array.add("three");
		array.add("four");
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
		}
	}

	private void showMehods() {
		Class cls = null;
		try {
			cls = Class.forName("scrapbook.Scrapbook");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i]);
		}
	};

	private void test_math() {
		System.out.println("0 ^ 1: " + (0 ^ 1));
		System.out.println("1 ^ 1: " + (1 ^ 1));
		System.out.println("2 ^ 1: " + (2 ^ 1));
		System.out.println("3 ^ 1: " + (3 ^ 1));

		for (int i = 0; i < 1000; i += 100) {
			System.out.println("======= " + i + " ========");
			System.out.println(i + " >> 0: " + (i >> 0));
			System.out.println(i + " >> 1: " + (i >> 1));
			System.out.println(i + " >> 2: " + (i >> 2));
			System.out.println(i + " >> 3: " + (i >> 3));
			System.out.println(i + " >> 4: " + (i >> 4));
		}
	}
}
