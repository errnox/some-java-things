package swingtest;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

class InternalFrame extends JInternalFrame {
	private ArrayList<JInternalFrame> links = new ArrayList<JInternalFrame>();
	private String title;
	private JDesktopPane parent;
	private UUID uniqueID;

	InternalFrame(String name, int x, int y, final JDesktopPane parent) {
		super(name, true, true, true, true);
		this.uniqueID = UUID.randomUUID();
		this.parent = parent;
		// this.setSize(150, 150);
		this.setResizable(true);
		this.setLocation(x, y);
		this.setVisible(true);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent e) {
				parent.repaint();
				// ((DrawableDesktopPane) parent).connectFrames();
				// Graphics2D g2d = (Graphics2D) parent.getGraphics();
				// ((DrawableDesktopPane)
				// InternalFrame.this.parent).drawConnectors(g2d);
				repaint();
			}
		});

		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				DrawableDesktopPane.connectors
						.removeAll(DrawableDesktopPane.connectors);
				DrawableDesktopPane.connectFrames();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public UUID getUniqueID() {
		return uniqueID;
	}

	public ArrayList getLinks() {
		return links;
	}

	public void addLink(JInternalFrame frame) {
		links.add(frame);
	}

	public String toString() {
		return title;
	}

}