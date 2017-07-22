package swingtest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SwingTest extends JFrame {

	public SwingTest() {

	}

	public void printInfo(String info) {
		JLabel swingTest = new JLabel(info);
		add(swingTest);
		this.setSize(100, 100);
		// pack();
		setVisible(true);
	}

	public void makeFrame() {
		JFrame frame = new JFrame("JFrame demo");
		// Add window listener
		frame.addWindowStateListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Content area
		JLabel jlabel = new JLabel("A JLabel...");
		jlabel.setPreferredSize(new Dimension(100, 100));
		frame.getContentPane().add(jlabel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public void runTests() {
		// Open simple window
		// this.printInfo("Hello there!");
		// this.makeFrame();

		// Open JDesktopPane
		// InternalDesktopPane frame = new InternalDesktopPane();
		// frame.setVisible(true);

		// Open ScrollableDesktop
		// new ScrollableDesktop();

		// Line drawing test
		 new DrawableDesktopPane();

		// Open JInternalFrame
		// new SimpleInternalFrame();

		// Open LinkedComponents
		// LinkedComponents test = new LinkedComponents();
		// JFrame f = new JFrame();
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// f.getContentPane().add(test.getContent());
		// f.setSize(400,400);
		// f.setLocation(200,200);
		// f.setVisible(true);

		// Open TransparentLinks
		// TransparentLinks test1 = new TransparentLinks();
		// JFrame f1 = new JFrame();
		// f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// f1.getContentPane().add(test1.getContent());
		// f1.setSize(400,400);
		// f1.setLocation(200,200);
		// f1.setVisible(true);

		// Open JDPTest
		// EventQueue.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		// new JDPTest().display();
		// }
		// });
	}

	public static void main(String args[]) {
		SwingTest st = new SwingTest();
		st.runTests();
	}
}
