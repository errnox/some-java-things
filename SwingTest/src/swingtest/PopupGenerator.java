package swingtest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

public class PopupGenerator {
	final JFrame frame = new JFrame();
	static JLabel label;

	public PopupGenerator(Component component, Map map) {
		frame.setSize(500, 500);
		label = new JLabel("Right click, please.");

		final JPopupMenu popup = new JPopupMenu();

		// Icons can be added like so
		// popup.add(new AbstractAction("New", MetalIconFactory
		// .getTreeComputerIcon()) {
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// // Do something
		// }
		// });

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			final String value = (String) pairs.getValue();
			popup.add(new AbstractAction(key) {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						Object obj = this;
//						Method m = obj.getClass().getMethod(value, new Class[] {});
						Method m = getClass().getDeclaredMethod(value);
						m.invoke(this);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			});
		}

		label.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		frame.getContentPane().add(label, BorderLayout.CENTER);

		// frame.pack();
		centerFrame();
		frame.setVisible(true);
	}

	public void centerFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidht = frame.getWidth();
		int frameHeight = frame.getHeight();
		int x = (dimension.width - frameWidht) / 2;
		int y = (dimension.height - frameHeight) / 2;
		frame.setLocation(x, y);
	}

	public static void main(String[] args) {
		Map <String, String> map = new HashMap<String, String>();
		map.put("Hello", "System.out.println(\"Hello, World!\");");
		map.put("Nothing", "System.out.println(\"Doing nothing...\");");
		new PopupGenerator(label, map);
	}

}
