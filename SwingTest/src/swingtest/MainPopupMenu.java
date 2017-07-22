package swingtest;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MainPopupMenu implements ActionListener {
	protected Component selectedComponent;
	private final String CREATE_FRAME = "Create Frame";
	private final String REMOVE_FRAME = "Remove Frame";
	private DrawableDesktopPane drawableDesktopPane;

	public MainPopupMenu(final DrawableDesktopPane drawableDesktopPane) {
		this.drawableDesktopPane = drawableDesktopPane;
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(makeMenuItem(CREATE_FRAME));
		popupMenu.add(makeMenuItem("Remove Frame"));

		MouseListener mouseListener = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				checkPopup(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				checkPopup(e);
			}

			public void checkPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					selectedComponent = e.getComponent();
//					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					 popupMenu.show(drawableDesktopPane, e.getX(), e.getY());
				}
			}
		};
		
	    drawableDesktopPane.addMouseListener(mouseListener);
	}

	private JMenuItem makeMenuItem(String label) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(this);
		return item;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String choice = e.getActionCommand();
		if (choice.equals(CREATE_FRAME)) {
			drawableDesktopPane.createFrame();
		} else if (choice.equals(REMOVE_FRAME)) {
			drawableDesktopPane.removeFrame();
		}
	}

}
