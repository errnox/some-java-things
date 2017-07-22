package swingtest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OldSetPositionDialog extends JPanel {
	int x = 0;
	int y = 0;

	JTextField xTextField = new JTextField();
	JTextField yTextField = new JTextField();
	JButton setButton = new JButton("Set");
	JButton cancelButton = new JButton("Cancel");
	JPanel buttonPanel = new JPanel();

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

	public OldSetPositionDialog(final JFrame frame) {
		buttonPanel.add(cancelButton);
		buttonPanel.add(setButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JComponent[] inputs = new JComponent[] {
				new JLabel("X Position"), xTextField, new JLabel("Y Position"),
				yTextField, buttonPanel };
		setButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setX(Integer.parseInt(xTextField.getText()));
				setY(Integer.parseInt(yTextField.getText()));
				System.out.println("this: " + this.getClass());
				closeWindow();
			}
		});
		JOptionPane.showOptionDialog(null, inputs, null,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				new Object[] {}, null);
	}

	public void closeWindow() {
		OldSetPositionDialog.this.setVisible(false);
//		SetPositionDialog.this.dispose();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.add(new JLabel("This is a test"));
		frame.pack();
		frame.setVisible(true);
		OldSetPositionDialog spd = new OldSetPositionDialog(frame);
		System.out.println("x: " + spd.getX() + "\ny: " + spd.getY());
	}
}

// package swingtest;
//
// import java.awt.BorderLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
//
// import javax.swing.JButton;
// import javax.swing.JFormattedTextField;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.event.DocumentEvent;
// import javax.swing.event.DocumentListener;
// import javax.swing.text.BadLocationException;
//
// public class SetPositionDialog extends JFrame {
// private int x = 0;
// private int y = 0;
// private boolean returnable = false; // x and y value can be returned
//
// public int getX() {
// return x;
// }
//
// public void setX(int x) {
// this.x = x;
// }
//
// public int getY() {
// return y;
// }
//
// public void setY(int y) {
// this.y = y;
// }
//
// private JLabel xString = new JLabel("X Position:");
// private JLabel yString = new JLabel("Y Position");
// private JFormattedTextField xTextField = new JFormattedTextField();
// private JFormattedTextField yTextField = new JFormattedTextField();
// private JButton setButton = new JButton("Set");
// private JButton cancelButton = new JButton("Cancel");
// private JPanel xPanel = new JPanel();
// private JPanel yPanel = new JPanel();
// private JPanel buttonPanel = new JPanel();
//
// public SetPositionDialog(final JFrame frame) {
// super();
// frame.setTitle("Move to...");
//
// xTextField.setValue(new Integer(frame.getWidth()));
// xTextField.setColumns(2);
// // xTextField.addPropertyChangeListener("x", this);
// xPanel.add(xString, BorderLayout.PAGE_START);
// xPanel.add(xTextField, BorderLayout.CENTER);
// frame.getContentPane().add(xPanel, BorderLayout.PAGE_START);
//
// yPanel.add(yString, BorderLayout.PAGE_START);
// yPanel.add(yTextField, BorderLayout.CENTER);
// frame.getContentPane().add(yPanel, BorderLayout.CENTER);
//
// buttonPanel.add(cancelButton);
// buttonPanel.add(setButton);
// frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
//
// xString.setVisible(true);
// xPanel.setVisible(true);
// yPanel.setVisible(true);
//
// xTextField.getDocument().addDocumentListener(new DocumentListener() {
// @Override
// public void removeUpdate(DocumentEvent e) {
//
// }
//
// @Override
// public void insertUpdate(DocumentEvent e) {
// try {
// String input = e.getDocument().getText(0,
// e.getDocument().getLength());
// setX((Integer) xTextField.getValue());
// System.out.println("x: " + getX());
// } catch (BadLocationException e1) {
// e1.printStackTrace();
// }
// }
//
// @Override
// public void changedUpdate(DocumentEvent e) {
//
// }
// });
//
// setButton.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// frame.dispose();
// }
// });
//
// frame.pack();
// // frame.setResizable(false);
// }
//
// public static void main(String[] args) {
// JFrame frame = new JFrame();
// SetPositionDialog spd = new SetPositionDialog(frame);
// System.out.println("x: " + spd.getX());
// frame.setVisible(true);
// }
// }

// package swingtest;
//
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;
// import java.awt.event.WindowAdapter;
// import java.awt.event.WindowEvent;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;
//
// import javax.swing.JDialog;
// import javax.swing.JFrame;
// import javax.swing.JOptionPane;
// import javax.swing.JTextField;
// import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
//
// public class SetPositionDialog extends JDialog implements ActionListener {
// private int x = 0;
// public int getX() {
// return x;
// }
//
// public void setX(int x) {
// this.x = x;
// }
//
// public int getY() {
// return y;
// }
//
// public void setY(int y) {
// this.y = y;
// }
//
// private int y = 0;
//
// JOptionPane optionPane;
// JTextField xTextField;
// JTextField yTextField;
//
// public SetPositionDialog(JFrame frame) {
// super(frame, true);
// setTitle("Move to location...");
//
// String xPositionString = "X Postion:";
// String yPositionString = "Y Postion:";
//
// xTextField = new JTextField(3);
// yTextField = new JTextField(3);
//
// xTextField.addActionListener(this);
// yTextField.addActionListener(this);
//
// Object[] array = { xPositionString, xTextField, yPositionString,
// yTextField };
//
// Object[] options = { "Cancel", "Set" };
//
// optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE,
// JOptionPane.YES_NO_OPTION, null, options, options[0]);
//
// setContentPane(optionPane);
//
// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
// addWindowListener(new WindowAdapter() {
// public void windowClosing(WindowEvent we) {
// optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
// }
// });
//
// addComponentListener(new ComponentAdapter() {
// public void componentShown(ComponentEvent ce) {
// xTextField.requestFocusInWindow();
// }
// });
//
//
// this.pack();
// setDefaultCloseOperation(DISPOSE_ON_CLOSE);
// this.setVisible(true);
// }
//
//
// @Override
// public void actionPerformed(ActionEvent e) {
// int xPos = Integer.parseInt(xTextField.getText());
// int yPos = Integer.parseInt(yTextField.getText());
//
// setX(xPos);
// setY(yPos);
// }
// }
