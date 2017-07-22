package swingtest;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalIconFactory;

class FramesListCellRenderer extends DefaultListCellRenderer {
	// final static ImageIcon longIcon = new ImageIcon("long.gif");
	// final static ImageIcon shortIcon = new ImageIcon("short.gif");
	final static Icon longIcon = MetalIconFactory.getTreeComputerIcon();
	final static Icon shortIcon = MetalIconFactory
			.getFileChooserDetailViewIcon();

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean listAndCellHaveFocus) {
		// Render the font using the DefaltListCellRenderer
		super.getListCellRendererComponent(list, value, index, isSelected,
				listAndCellHaveFocus);

		// Set the JLabel's icon property
		String s = value.toString();
		if (s.length() > 10) {
			setIcon(longIcon);
		} else if (s.length() == 0) {
			setIcon((Icon) new JButton("Push"));
		} else {
			setIcon(shortIcon);
		}
		// setIcon((s.length() > 10) ? longIcon : shortIcon);

		return this;

		/*
		 * A JButton could be added like so
		 * 
		 * JPanel panel = new JPanel(); JButton button = new JButton("Push");
		 * panel.add(button);
		 * 
		 * return panel;
		 */
	}

	public static void main(String[] args) {
		String[] data = { "one", "two", "three", "four",
				"This is a very long entry." };
		JFrame frame = new JFrame();
		JList dataList = new JList(data);
		dataList.setCellRenderer(new FramesListCellRenderer());
		frame.getContentPane().add(dataList, BorderLayout.CENTER);
		dataList.setVisible(true);
		frame.pack();
		frame.setVisible(true);
	}
}
