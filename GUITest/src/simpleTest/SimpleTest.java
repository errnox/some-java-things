package simpleTest;

import guitest.ExitHandler;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SimpleTest {
	private JFrame frame;
	private JPanel panel;
	private JButton infoButton;
	private JButton cancelButton;

	public SimpleTest() {
		frame = new JFrame();
		panel = new JPanel();
		infoButton = new JButton("Push");
		cancelButton = new JButton("Cancel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Handle container components
		panel.add(infoButton, BorderLayout.SOUTH);
		panel.add(cancelButton, BorderLayout.SOUTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		// Handle events
		addAction();

		// Finalize
		frame.pack();
		frame.setVisible(true);
	}

	public void addAction() {
		// infoButton
		infoButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(frame,
						"You just clicked the button.", "Info",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		// cancelButton
		cancelButton.addActionListener(new ExitHandler());
	}

	public static void main(String[] args) {
		new SimpleTest();
	}
}
