package swingtest;

import java.util.ArrayList;

import javax.swing.JInternalFrame;

public class Node {
	private ArrayList<JInternalFrame> links = new ArrayList<JInternalFrame>();
	private String name;
	private JInternalFrame frame;
	
	public Node(JInternalFrame frame) {
		this.frame = frame;
		this.name = frame.getTitle();
	}
	
	public void add(JInternalFrame node) {
		links.add(node);
	}
	
	public void remove(JInternalFrame node) {
		links.remove(node);
	}
	
	public JInternalFrame getInternalFrame() {
		return frame;
	}
	
	public ArrayList getLinks() {
		return links;
	}
	
	public String toString() {
		return name;
	}
}
