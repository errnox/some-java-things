package numberplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GraphicalAnalyzer extends JPanel{
	private JFrame frame;
	private char ch;
	
	private int x = 0;
	private int y = 0;
	private int width = 10;
	private int height = 200;
	
	private int drawingAreaWidth = 500;
	private int drawingAreaHeight = 500;
	
	private final int RED = 0xff0000; 
	
	public GraphicalAnalyzer() {
//		paintComponent(this.getGraphics());
		this.ch = ch;
		new JScrollPane(this);
		frame = new JFrame();
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setVisible(true);
		setVisible(true);
	}
	
	public void setCh(char ch) {
		this.ch = ch;
	}
	
	public void paintComponent(Graphics g) {
		this.repaint();
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(RED));
		Line2D l = new Line2D.Double(x, 20, 300, 0);
		g2d.draw(l);
		drawBar(g2d);
	}
	
	public void drawBar(Graphics2D g2d) {
		int newY = (drawingAreaHeight - 100) - height;
		Rectangle2D r = new Rectangle2D.Double(x, newY, width, height);
		g2d.drawString("" + ch, x, newY - 10);
		g2d.fill(r);
		this.repaint();
	}
	
	public void update(int height) {
		this.height = height;
		this.x += 20;
		paintComponent(this.getGraphics());
		repaint();
	}
}
