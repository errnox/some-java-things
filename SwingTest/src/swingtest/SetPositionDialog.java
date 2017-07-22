package swingtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SetPositionDialog extends JDialog implements CustomEventListener {
	// Custom EventListener setup 
	private ArrayList listeners = new ArrayList();
	
	public synchronized void addEventListener(CustomEventListener listener) {
		listeners.add(listener);
	}
	
	public synchronized void removeEventListener(CustomEventListener listener) {
		listeners.remove(listener);
	}
	
	private int x = 0;
	private int y = 0;

	private JLabel xLabel;
	private JLabel yLabel;
	private JFormattedTextField xTextField;
	private JFormattedTextField yTextField;
	private JButton cancelButton;
	private JButton setButton;

	public SetPositionDialog(JFrame parent) {
		super(parent, "Move to...", null);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		xLabel = new JLabel("X Position");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(xLabel, cs);

		xTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		xTextField.setColumns(3);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(xTextField, cs);

		yLabel = new JLabel("Y Position");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(yLabel, cs);

		yTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		yTextField.setColumns(3);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(yTextField, cs);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setButton = new JButton("Set");
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setX(getInputX());
					setY(getInputY());
					fireEvent();
					// Debuggin help
					// JOptionPane.showMessageDialog(MinimalDialog.this, "X: "
					// + getX() + "\nY: " + getY(), "Info",
					// JOptionPane.PLAIN_MESSAGE);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(SetPositionDialog.this,
							"Invalid input. Only numbers accepted",
							"Wrong input", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancelButton);
		buttonPanel.add(setButton);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}
	
	private synchronized void fireEvent() {
		CustomEvent event = new CustomEvent(this);
		Iterator i = listeners.iterator();
		while (i.hasNext()) {
			((CustomEventListener) i.next()).handleCustomEvent(event);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getInputX() {
		return Integer.parseInt(xTextField.getText());
	}

	public int getInputY() {
		return Integer.parseInt(yTextField.getText());
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel("Test"), BorderLayout.CENTER);
		JButton showButton = new JButton("Show");
		showButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SetPositionDialog md = new SetPositionDialog(frame);
				md.setVisible(true);
			}
		});
		frame.getContentPane().add(showButton, BorderLayout.PAGE_END);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	@Override
	public void handleCustomEvent(EventObject e) {
		// TODO Auto-generated method stub
		
	}
}