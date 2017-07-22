package swingtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

class DrawableDesktopPane extends JDesktopPane implements CustomEventListener,
		ActionListener {

	// JInternalFrame frames[] = new JInternalFrame[getComponentCount()];
	static LinkedList<JInternalFrame> frames = new LinkedList<JInternalFrame>();
	// List of connectors
	static LinkedList<Connector> connectors;
	// Control lists
	private static HashMap<JInternalFrame, JInternalFrame> connections;

	private JFrame frame = new JFrame();
	private JPanel desktopPanel = new JPanel();
	private JSplitPane splitPane = new JSplitPane();
	private SetPositionDialog dialog;
	private MouseListener leftListSingleClickListener;
	private JList leftList;
	private int red = 0xff0000;
	private int green = 0x00ff00;
	private int blue = 0x0000ff;
	private Color previousColor;
	private Point pointInList;

	public DrawableDesktopPane() {
		desktopPanel.add(this);
		frame.setSize(400, 400);
		frame.getContentPane().add(desktopPanel, BorderLayout.CENTER);
		frame.getContentPane().add(createMenuBar(), BorderLayout.PAGE_START);

		connections = new HashMap<JInternalFrame, JInternalFrame>();
		connectors = new LinkedList<Connector>();

		// JTree test
		// TODO: Remove or Improve!
		JTree leftTree = new JTree();
		// frame.getContentPane().add(leftTree, BorderLayout.WEST);

		// JList test
		// TODO: Remove!
		String[] listElements = new String[100]; // = {"red", "blue", "green",
													// "yellow", "pink"};
		for (int i = 0; i < getComponents().length; i++) {
			listElements[i] = getComponents()[i].getName(); // String.valueOf("Color No. "
															// + i);
		}
		String[] animals = { "dog", "cat", "bird", "monkey", "elephant" };
		leftList = new JList(listElements);
		leftList.setCellRenderer(new FramesListCellRenderer());
		// Set maximal width of the JList
		leftList.setPrototypeCellValue("xxxxxxxxxxxx");
		// Add a MouseListener for single clicking on a list item
		leftListSingleClickListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// changeFrameBackgroundColor(index);
				// removeFrameAtIndex(index);
			}
		};
		leftList.addMouseListener(leftListSingleClickListener);
		// Add a MouseListener for double clicking on a list item
		MouseListener leftListDoubleClickListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = leftList.locationToIndex(e.getPoint());
					// showInfo(index);
					isolateInternalFrame(index);
				}
			}
		};
		leftList.addMouseListener(leftListDoubleClickListener);

		// list
		ListModel listModel = generateNewListModel(animals);
		leftList.setModel(listModel);
		leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		leftList.setLayoutOrientation(JList.VERTICAL);
		leftList.setVisibleRowCount(-1);

		// Add JScrollPane to the center of the JBorderLayout
		JScrollPane leftScrollPane = new JScrollPane(leftList);
		splitPane.setLeftComponent(leftScrollPane);
		splitPane.setRightComponent(this);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		// JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// leftScrollPane, this);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		// Define some styles
		// Style def = StyleContext.getDefaultStyleContext().getStyle(
		// StyleContext.DEFAULT_STYLE);
		// Style regular = messageBox.addStyle("regular", def);
		// StyleConstants.setFontFamily(def, "SansSerif");
		// StyleConstants.setBold(def, true);
		// Style s = messageBox.addStyle("italic", regular);
		// StyleConstants.setItalic(s, true);
		// s = messageBox.addStyle("red", regular);
		// StyleConstants.setForeground(s, Color.red);
		// Document doc = messageBox.getStyledDocument();
		//
		// try {
		// doc.insertString(doc.getLength(), "Information: ",
		// messageBox.getStyle("red"));
		// doc.insertString(doc.getLength(), "something",
		// messageBox.getStyle("italic"));
		// } catch (NullPointerException e) {
		// } catch (BadLocationException e) {
		//
		// e.printStackTrace();
		// }
		// f3.add(messageBox);

		// Manual test
		// JInternalFrame f1 = createFrame();
		// f1.setLocation(10, 10);
		// f1.setBackground(new Color(blue));
		// JInternalFrame f2 = createFrame();
		// f2.setLocation(100, 250);
		// JInternalFrame f3 = createFrame();
		// f3.setLocation(30, 300);
		// JInternalFrame f4 = createFrame();
		// f4.setLocation(260, 200);
		// JInternalFrame f5 = createFrame();
		// f5.setLocation(110, 20);

		// Semi-automated test
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JInternalFrame frame = createFrame();
				frame.setLocation(i * 150, j * 150);
				frame.setSize(40, 40);
			}
		}

		addGlass();
		centerFrame();
		generatePopupMenu();
		new MainPopupMenu(this);
		// this.add(mainPopupMenu);

		buildAssociations();
		connectFrames();

		updateFrameList();
		frame.setVisible(true);
	}

	// Build a hashmap of JInternalFrame pairs that should be connected
	// TODO: Improve!
	public void buildEvenAssociations() {
		for (int i = 0; i < frames.size(); i++) {
			if (i % 2 == 1) {
				if (i < frames.size() - 2) {
					connections.put(frames.get(i), frames.get(i + 2));
				}
			}
		}
	}

	public void buildAssociations() {
		for (int i = 0; i < frames.size(); i++) {
			// Set node's connections here
			// TODO: Improve! Add an actual connections system
			int length = frames.size();
			Random random = new Random();
			int idx = random.nextInt(length);
			for (int j = 0; j < random.nextInt(5); j++) { // 5 is changable
				if (idx != i) {
					((InternalFrame) frames.get(i)).addLink(frames.get(idx));
				}
			}
		}
	}

	public void connectEvenFrames(Graphics2D g2d) {
		Iterator it = connections.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			connectComponents(pairs.getKey(), pairs.getValue(), red);
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public static void connectFrames() {
		Line2D line;
		Connector connector;
		JInternalFrame linkedFrame;
		for (int i = 0; i < frames.size(); i++) {
			JInternalFrame frame = frames.get(i);
			ArrayList links = ((InternalFrame) frames.get(i)).getLinks();
			for (int j = 0; j < links.size(); j++) {
				linkedFrame = (JInternalFrame) links.get(j);
				connections.put(frame, linkedFrame);
				// line = new Line2D.Double(frame.getX() + frame.getWidth() / 2,
				// frame.getY() + frame.getHeight() / 2,
				// linkedFrame.getX() + linkedFrame.getWidth() / 2,
				// linkedFrame.getY() + linkedFrame.getHeight() / 2);
				// connector = new Connector(line);
				// connectors.add(connector);
				// connector.setOrigin(frames.get(i));
				// connector.setTarget(linkedFrame);
			}
		}
	}

	public void cleanLinks(UUID uniqueID) {
		for (int i = 0; i < frames.size(); i++) {
			ArrayList links = ((InternalFrame) frames.get(i)).getLinks();
			for (int j = 0; j < links.size(); j++) {
				if (((InternalFrame) links.get(j)).getUniqueID() == uniqueID) {
					// links.remove(links.get(j));
					links.remove(j);
				}
			}
		}

		// Check origin and target
		// for (int i = 0; i < connectors.size(); i++) {
		// if (((InternalFrame) connectors.get(i).getOrigin()).getUniqueID() ==
		// uniqueID ||
		// ((InternalFrame) connectors.get(i).getTarget()).getUniqueID() ==
		// uniqueID) {
		// connectors.remove(connectors.get(i));
		// }
		// }
	}

	public void drawConnectors(Graphics2D g2d) {
		// for (int i = 0; i < connectors.size(); i++) {
		// Connector line = connectors.get(i);
		// Color c = new Color(red);
		// g2d.setColor(c);
		// g2d.draw(line.getLine());
		// // Control elements
		// // TODO: Remove!
		// g2d.drawString("To here", (int) line.getLine().getX2() - 100,
		// (int) line.getLine().getY2() - 100);
		// g2d.setColor(Color.green);
		// g2d.draw(new Line2D.Double(line.getLine().getX2() - 100, line
		// .getLine().getY2() - 100, line.getLine().getX2(), line
		// .getLine().getY2()));
		// }

		// HashMap based solution
		for (Map.Entry<JInternalFrame, JInternalFrame> entry : connections
				.entrySet()) {
			Color c = new Color(red);
			JInternalFrame key = entry.getKey();
			JInternalFrame value = entry.getValue();
			g2d.setColor(c);
			g2d.draw(new Line2D.Double(key.getX(), key.getY(), value.getX(),
					value.getY()));
			// Control elements
			// TODO: Remove!
			g2d.setColor(Color.green);
			g2d.drawString("To here", (int) key.getX() - 100,
					(int) key.getY() - 100);
			g2d.draw(new Line2D.Double(value.getX() - 100, value.getY() - 100,
					value.getX(), value.getY()));
		}
	}

	// Very experimental method!
	// TODO: Improve!
	public void addGlass() {
		JPanel glass = new JPanel() {
			public void paintComponent(Graphics g) {
				for (int i = 0; i < frames.size(); i++) {
					Rectangle r = frames.get(i).getBounds();
					// Point p =
					// frames.get(i).getRootPane().getContentPane().getLocationOnScreen();
					// double x = p.x;
					// double y = p.y;
					double x = r.getX() + splitPane.getRightComponent().getX();
					double y = r.getY() + splitPane.getY();
					double width = r.getWidth();
					double height = r.getHeight();
					g.setColor(Color.red);
					g.drawRect((int) x, (int) y, (int) width, (int) height);
					g.setColor(Color.green);
					g.fillRect((int) x + (int) width - (int) width / 10,
							(int) y + (int) height - (int) height / 10,
							(int) width / 10, (int) height / 10);
					frame.repaint();
				}
			}
		};
		glass.setOpaque(false);
		frame.setGlassPane(glass);
		// for (int i = 0; i < frames.size(); i++) {
		// frames.get(i).setGlassPane(glass);
		// }
		glass.setVisible(true);
	}

	public void removeFrameAtIndex(int index) {
		JInternalFrame target = frames.get(index);
		UUID uniqueID = ((InternalFrame) target).getUniqueID();
		frames.remove(index);
		updateFrameTitles();
		updateFrameList();
		cleanLinks(uniqueID);
		DrawableDesktopPane.this.remove(target);

		// for (Map.Entry<JInternalFrame, JInternalFrame> entry : connections
		// .entrySet()) {
		for (Iterator<Map.Entry<JInternalFrame, JInternalFrame>> itr = connections
				.entrySet().iterator(); itr.hasNext();) {
			Map.Entry<JInternalFrame, JInternalFrame> entry = itr.next();
			JInternalFrame key = entry.getKey();
			JInternalFrame value = entry.getValue();
			// if (((InternalFrame) key).getUniqueID() == uniqueID
			// || ((InternalFrame) value).getUniqueID() == uniqueID) {
			// connections.remove(key);
			// }
			if (uniqueID.equals(key)) {
				itr.remove();
				break;
			} else if (uniqueID.equals(value)) {
				itr.remove();
				break;
			}
		}

		// connectors.remove(index);
		//
		// // Manage the connectors
		// // System.out.println("Before: " + connectors.size());
		// connectors.removeAll(connectors);
		// connectFrames();
		// // System.out.println("After: " + connectors.size());
	}

	public void centerFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidht = frame.getWidth();
		int frameHeight = frame.getHeight();
		int x = (dimension.width - frameWidht) / 2;
		int y = (dimension.height - frameHeight) / 2;
		frame.setLocation(x, y);
	}

	public void updateFrameTitles() {
		for (int i = 0; i < frames.size(); i++) {
			frames.get(i).setTitle("Frame " + String.valueOf(i + 1));
		}
	}

	public void isolateInternalFrame(int index) {
		JFrame infoFrame = new JFrame();
		final JInternalFrame target = frames.get(index);
		updateFrameList();
		final int targetX = target.getX();
		final int targetY = target.getY();
		infoFrame.add(target);
		infoFrame.setSize(target.getWidth(), target.getHeight());
		WindowListener infoCloseListener = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DrawableDesktopPane.this.add(target);
				target.setLocation(targetX, targetY);
				// Debugging help
				// System.out.println("Frames: " + frames.size());
			}
		};
		infoFrame.addWindowListener(infoCloseListener);
		infoFrame.setVisible(true);
	}

	public void changeFrameBackgroundColor(int index) {
		JInternalFrame target = frames.get(index);
		if (previousColor == null) {
			previousColor = target.getBackground();
		}
		if (target.getBackground().getRGB() == previousColor.getRGB()) {
			target.setBackground(new Color(red));
		} else {
			target.setBackground(previousColor);
		}
		DrawableDesktopPane.this.repaint();
	}

	public void showInfo(int index) {
		JOptionPane.showMessageDialog(frame,
				"Double clicked on item: " + index, "Info",
				JOptionPane.PLAIN_MESSAGE);
	}

	public ListModel generateNewListModel(String[] list) {
		ListModel listModel = new DefaultListModel();
		for (int i = 0; i < list.length; i++) {
			((DefaultListModel) listModel).addElement(list[i]);
		}
		return listModel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		DrawableDesktopPane.this.repaint();
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		g2d.setColor(Color.red);
		// Draw connector line between the JInternalFrames
		// connectComponents(getF1(), getF2(), g2d);
		// Draw cubic connector curve between the JInternalFrames
		// connectComponentsWithCubicCurves(getF1(), getF2(), g2d);

		// connectAllComponents(g2d);
		// frame.getGlassPane().repaint();
		// colorizeEdges(g2d);

		// buildEvenAssociations();
		// connectEvenFrames(g2d);

		// if (nodesList != nodesListOld) {
		// buildAssociations();
		// connectNodes(g2d);
		// nodes.removeAll(nodes);
		// nodesListOld = nodesList;
		// }
		// // nodesListOld = nodesList;
		// nodes.removeAll(nodes);

		drawConnectors(g2d);
	}

	public void generatePopupMenu() {
		leftList.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent e) {
				dispatchClick(e);
			}

			public void mouseReleased(final MouseEvent e) {
				dispatchClick(e);
			}

			public void moveToLocation(JInternalFrame jInternalFrame, int x,
					int y) {
				jInternalFrame.setLocation(x, y);
			}

			public void dispatchClick(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					final int x = e.getX();
					final int y = e.getY();
					final int index = leftList.getSelectedIndex();
					leftList.setSelectedIndex(leftList.locationToIndex(e
							.getPoint()));
					pointInList = e.getPoint();

					JPopupMenu popup = new JPopupMenu();
					popup.add(new AbstractAction("Create Frame") {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							createFrame();
						}
					});
					popup.add(new AbstractAction("Remove Frame") {

						@Override
						public void actionPerformed(ActionEvent e) {
							// Remove last frame in list of frames
							// removeFrame();
							// Remove selected frame
							removeFrameAtIndex(leftList.getSelectedIndex());
						}
					});
					popup.add(new JPopupMenu.Separator());
					popup.add(new AbstractAction("Info") {

						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(frame, "Object: "
									+ frames.get(index).getTitle(), "Info",
									JOptionPane.PLAIN_MESSAGE);
						}
					});

					popup.add(new AbstractAction("Move") {

						@Override
						public void actionPerformed(ActionEvent e) {
							// handleCustomEvent(e);
							dialog = new SetPositionDialog(frame);
							dialog.addEventListener(DrawableDesktopPane.this);
							dialog.setVisible(true);
						}
					});
					// popup.setVisible(true);
					popup.show(leftList, x, y);
				}
			}
		});

	}

	public JMenuItem makeMenuItem(String label) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(this);
		return item;
	}

	public void updateFrameList() {
		// Update JList
		String[] frameNames = new String[frames.size()];

		for (int i = 0; i < frames.size(); i++) {
			frameNames[i] = frames.get(i).getTitle();
		}
		// Long way of updating the JList
		ListModel listModel = generateNewListModel(frameNames);
		leftList.setModel(listModel);
		// Short way of updating the JList
		// leftList.setListData(frameNames);
	}

	// TODO: Improve!
	public void colorizeEdges(Graphics g2d) {
		Component c[] = getComponents();
		for (int i = 0; i < c.length; i++) {
			// First rectangle
			Rectangle r1 = c[i].getBounds();
			double r1x = r1.getCenterX();
			double r1y = r1.getCenterY();
			// Second rectangle
			for (int j = 0; j < c.length; j++) {
				Rectangle r2 = c[j].getBounds();
				double r2x = r2.getCenterX();
				double r2y = r2.getCenterY();
				// Calculate the distance between the two rectangles
				double dx = Math.max(r1x, r2x) - Math.min(r1x, r2x);
				double dy = Math.max(r1y, r2y) - Math.min(r1y, r2y);
				double d = Math.sqrt((Math.pow(dx, 2) - Math.pow(dy, 2)));
				if (d > 100) {
					connectComponents(c[i], c[j], red);
				} else {
					connectComponents(c[i], c[j], blue);
				}
			}
			// if (c[i].isVisible()) {
			// c[i].setSize(20, 100);
			// }
			// if (c[i].isinstance(InternalFrame)) {
			// System.out.println("It's a line.");
			// }
		}
	}

	// Test method; TODO: Remove!
	public void drawCubicCurve(Graphics2D g2d) {
		g2d.setColor(Color.green);
		int arg0 = 30;
		int arg1 = 400;
		int arg2 = 150;
		int arg3 = 400;
		int arg4 = 200;
		int arg5 = 500;
		int arg6 = 350;
		int arg7 = 450;
		CubicCurve2D cc2d = new CubicCurve2D.Float(arg0, arg1, arg2, arg3,
				arg4, arg5, arg6, arg7);
		g2d.draw(cc2d);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void connectComponents(Object object, Object object2, int red2) {
		// Initialize visual style
		// Color c = new Color(red);
		// g2d.setColor(c);
		// g2d.setStroke(new BasicStroke(4.0f));

		Rectangle r1 = ((Component) object).getBounds();
		Rectangle r2 = ((Component) object2).getBounds();
		int x1 = (int) r1.getCenterX();
		int y1 = (int) r1.getCenterY();
		int x2 = (int) r2.getCenterX();
		int y2 = (int) r2.getCenterY();
		// Simple way to draw a line
		// g2d.drawLine(x1, y1, x2, y2);
		// Sophisticated way to draw a line
		Line2D l = new Line2D.Double(x1, y1, x2, y2);
		// connectors.add(l);
		// g2d.draw(l);
	}

	// public void connectComponents(JInternalFrame f1, JInternalFrame f2,
	// Graphics2D g2d) {
	// String color = "ff000";
	// connectComponents(f1, f2, g2d, color);
	// }

	public void connectAllComponents(Graphics2D g2d) {
		Component[] c = getComponents();
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c.length; j++) {
				connectComponents((JInternalFrame) c[i], (JInternalFrame) c[j],
						0x0000ff);
			}
		}
	}

	public void connectComponentsWithCubicCurves(JInternalFrame f1,
			JInternalFrame f2, Graphics2D g2d) {
		g2d.setColor(Color.green);

		// Represent internal frames as rectangles
		Rectangle r1 = f1.getBounds();
		double r1x = r1.getCenterX();
		double r1y = r1.getCenterY();
		Rectangle r2 = f2.getBounds();
		double r2x = r2.getCenterX();
		double r2y = r2.getCenterY();

		// Calculate control poins' buffers
		float ctrlx1Buff = (float) (100);
		float ctrly1Buff = (float) (-100);
		float ctrlx2Buff = (float) (-100);
		float ctrly2Buff = (float) (100);

		// Set (control) points of cubic curve
		float x1 = (float) r1x;
		float y1 = (float) r1y;
		float ctrlx1 = (float) (r1x + ctrlx1Buff);
		float ctrly1 = (float) (r1y + ctrly1Buff);
		float ctrlx2 = (float) (r2x + ctrlx2Buff);
		float ctrly2 = (float) (r2y + ctrly2Buff);
		float x2 = (float) r2x;
		float y2 = (float) r2y;
		CubicCurve2D cc2d = new CubicCurve2D.Float(x1, y1, ctrlx1, ctrly1,
				ctrlx2, ctrly2, x2, y2);
		g2d.draw(cc2d);
	}

	public JInternalFrame createFrame() {
		int frameNumber = frames.size() + 1;
		InternalFrame frame = new InternalFrame("Frame " + frameNumber, 100,
				100, this);
		frame.setVisible(true);
		// Every JInternalFrame must be added to content pane using JDesktopPane
		// Add frame to internal list of frames.
		frames.add(frame);
		// Add frame to the virtual desktop.
		this.add(frame);
		try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
		updateFrameTitles();
		updateFrameList();
		generatePopupMenu();
		return frame;
	}

	public void removeFrame() {
		// Remove all connections from and to the frame that should be removed
		for (int i = 0; i < frames.size(); i++) {
			ArrayList links = links = ((InternalFrame) frames.get(i))
					.getLinks();
			for (int j = 0; j < links.size(); j++) {
				if (links.get(j) == frames.getLast()) {
					links.remove(j);
				}
			}
		}
		ArrayList ownLinks = ((InternalFrame) frames.getLast()).getLinks();
		ownLinks.removeAll(ownLinks);

		this.remove(frames.getLast());
		if (!frames.isEmpty()) {
			frames.removeLast();
		}
		updateFrameList();
		generatePopupMenu();
		this.repaint();
	}

	// Add a menu bar
	protected JMenuBar createMenuBar() {
		// Outer menu bar
		JMenuBar outerBar = new JMenuBar();
		outerBar.setLayout(new GridLayout(0, 1));

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Frame");
		JMenu menuDiverse = new JMenu("Diverse");
		JMenu menuTests = new JMenu("Tests");
		menuDiverse.setMnemonic(KeyEvent.VK_D);
		menu.setMnemonic(KeyEvent.VK_F);
		// Create new frame
		JMenuItem createFrameItem = new JMenuItem("Create Frame");
		createFrameItem.setMnemonic(KeyEvent.VK_C);
		createFrameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
			}
		});
		JMenuItem removeFrameItem = new JMenuItem("Remove Frame");
		removeFrameItem.setMnemonic(KeyEvent.VK_R);
		removeFrameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFrame();
			}
		});
		JMenuItem menuItemInfo = new JMenuItem("Print Info");
		menuItemInfo.setMnemonic(KeyEvent.VK_P);
		menuItemInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingTest st = new SwingTest();
				st.printInfo("foo");
			}
		});
		JMenuItem menuItemLineTest = new JMenuItem("LineTest");
		menuItemLineTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DrawableDesktopPane();
			}
		});
		menu.add(createFrameItem);
		menu.add(removeFrameItem);
		menuDiverse.add(menuItemInfo);
		menuTests.add(menuItemLineTest);
		menuBar.add(menu);
		menuBar.add(menuDiverse);
		menuBar.add(menuTests);
		outerBar.add(menuBar);

		// Test menu bar
		JMenu testMenu = new JMenu("Test Menu");
		for (int i = 0; i < 10; i++) {
			testMenu.add(new JMenuItem("Test 2"));
		}
		JMenuBar testBar = new JMenuBar();
		testBar.add(testMenu);
		outerBar.add(testBar);

		return outerBar;
	}

	public LinkedList<JInternalFrame> getFrames() {
		return frames;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleCustomEvent(EventObject e) {
		// SetPositionDialog dialog = new SetPositionDialog(frame);
		// dialog.setVisible(true);
		int newX = dialog.getX();
		int newY = dialog.getY();
		frames.get(leftList.locationToIndex(pointInList)).setLocation(newX,
				newY);
	}
}