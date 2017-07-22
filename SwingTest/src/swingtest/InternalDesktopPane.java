package swingtest;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

//import swingtest.DrawableDesktopPane;

class ScrollDesk extends JDesktopPane implements Scrollable {
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle r, int axis, int dir) {
		return 50;
	}

	public int getScrollableBlockIncrement(Rectangle r, int axis, int dir) {
		return 200;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
}

public class InternalDesktopPane extends JFrame {

	JDesktopPane jdpDesktop = new ScrollDesk();
	static int openFrameCount = 0;

	public InternalDesktopPane() {
		super("JInternalFrame Usage Demo");
		// Make the main window positioned as 50 pixels from each edge of the
		// screen.
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height
				- inset * 2);
		// Add a Window Exit Listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Create and Set up the GUI.
		// JDesktopPane jdpDesktop = new ScrollDesk(); // TODO: Remove!
		jdpDesktop.setPreferredSize(new Dimension(3000, 1000));
		getContentPane().add(new JScrollPane(jdpDesktop), "Center");
		// add(new JScrollPane(jdpDesktop), "Center");

		// A specialized layered pane to be used with JInternalFrames
		createFrame(); // Create first window
		// setContentPane(jdpDesktop); // Setting this does not allow adding a
		// JScrollPanel at the same time.
		setJMenuBar(createMenuBar());
		// Make dragging faster by setting drag mode to Outline
		jdpDesktop.putClientProperty("JDesktopPane.dragMode", "outline");
	}

	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Frame");
		JMenu menuDiverse = new JMenu("Diverse");
		JMenu menuTests = new JMenu("Tests");
		menuDiverse.setMnemonic(KeyEvent.VK_D);
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItem = new JMenuItem("New IFrame");
		menuItem.setMnemonic(KeyEvent.VK_N);
		JMenuItem menuItemInfo = new JMenuItem("Print Info");
		menuItemInfo.setMnemonic(KeyEvent.VK_P);
		JMenuItem menuItemLineTest = new JMenuItem("LineTest");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
			}
		});
		menuItemInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingTest st = new SwingTest();
				st.printInfo("foo");
			}
		});
		menuItemLineTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DrawableDesktopPane();
			}
		});
		menu.add(menuItem);
		menuDiverse.add(menuItemInfo);
		menuTests.add(menuItemLineTest);
		menuBar.add(menu);
		menuBar.add(menuDiverse);
		menuBar.add(menuTests);
		return menuBar;
	}

	protected JInternalFrame createFrame() {
		MyInternalFrame frame = new MyInternalFrame();
		frame.setVisible(true);
		// Every JInternalFrame must be added to content pane using JDesktopPane
		jdpDesktop.add(frame);
		try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
		return frame;
	}

	class MyInternalFrame extends JInternalFrame {

		static final int xPosition = 30, yPosition = 30;

		public MyInternalFrame() {
			super("IFrame #" + (++openFrameCount), true, // resizable
					true, // closable
					true, // maximizable
					true);// iconifiable
			setSize(300, 300);
			// Set the window's location.
			setLocation(xPosition * openFrameCount, yPosition * openFrameCount);
		}
	}
}
