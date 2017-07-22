package swingtest;

import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JInternalFrame;

public class Connector {
	private Line2D line;
	private JInternalFrame origin;
	private JInternalFrame target;

	public Connector(Line2D line) {
		this.line = line;
	}

	public void setOrigin(JInternalFrame origin) {
		this.origin = origin;
	}
	
	public JInternalFrame getOrigin() {
		return this.origin;
	}

	public void setTarget(JInternalFrame target) {
		this.target = target;
	}
	
	public JInternalFrame getTarget() {
		return this.target;
	}

	public void setLine(Line2D line) {
		this.line = line;
	}

	public Line2D getLine() {
		return line;
	}
}
