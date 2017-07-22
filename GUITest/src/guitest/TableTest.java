package guitest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class TableTest extends JPanel implements ListSelectionListener {
	private boolean DEBUG = false;
	private static JPanel mainPanel;
	private JFrame frame;
	private JScrollPane tableScrollPane;
	private JScrollPane mainScrollPane;
	private JButton confirmSelectionButton;
	private JTable table;

	private String[] columnNames = { "First Name", "Last Name", "Sport",
			"# of Years", "Vegetarian" };

	private Object[][] data = {
			{ "Kathy", "Smith", "Snowboarding", new Integer(5),
					new Boolean(false) },
			{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
			{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
			{ "Jane", "White", "Speed reading", new Integer(20),
					new Boolean(true) },
			{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

	public TableTest() {
		super(new GridLayout(1, 0));

		// Create and set up the window.
		frame = new JFrame("SimpleTableDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		confirmSelectionButton = new JButton("Select");
		confirmSelectionButton.addMouseListener(new MouseListener() {

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
				Object value = null;
				int[] rows = table.getSelectedRows();
				int[] columns = table.getSelectedRows();
				// for (int i = 0; i < rows.length; i++) {
				// for (int j = 0; j < columns.length; j++) {
				// value = table.getModel().getValueAt(rows[i], columns[j]);
				// System.out.println(value);
				// }
				// }
				System.out.println("---------------------------------");
				for (int i = 0; i < columns.length; i++) {
					value = table.getModel().getValueAt(rows[i], table.columnAtPoint(e.getPoint()));
					System.out.println(value);
				}
			}
		});

		// Create and set up the content pane.
		// SimpleTableDemo newContentPane = new SimpleTableDemo();
		// newContentPane.setOpaque(true); // content panes must be opaque
		// frame.setContentPane(newContentPane);

		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		table.setColumnSelectionAllowed(true);

		// Set column width
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 3) {
				column.setPreferredWidth(100);
			} else {
				column.setPreferredWidth(50);
			}

		}

		// Debug help
		if (DEBUG) {
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					printDebugData(table);
				}
			});
		}

		// Create the scroll pane and add the table to it
		tableScrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel
		add(tableScrollPane);

		mainScrollPane = new JScrollPane(mainPanel);
		add(mainScrollPane);

		mainPanel.add(tableScrollPane, BorderLayout.NORTH);
		frame.add(new JTree(), BorderLayout.WEST);
		frame.add(confirmSelectionButton, BorderLayout.SOUTH);
		mainPanel.setVisible(true);
		frame.getContentPane().add(mainScrollPane, BorderLayout.CENTER);

		// Colorize the table
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(0xaaee00));
		tableHeader.setForeground(new Color(0x0011ff));
		table.setBackground(new Color(0x9999ff));
		table.setForeground(new Color(0xffffff));

		// Display the window.
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

	// Debug help
	private void printDebugData(JTable table) {
		int numRows = table.getRowCount();
		int numCols = table.getColumnCount();
		javax.swing.table.TableModel model = table.getModel();

		System.out.println("Value of data: ");
		for (int i = 0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j = 0; j < numCols; j++) {
				System.out.print("  " + model.getValueAt(i, j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TableTest();
			}
		});
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}