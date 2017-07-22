package guitest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}
