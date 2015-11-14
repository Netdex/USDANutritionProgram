package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

class CustomScrollPane extends JScrollPane {
	protected CustomScrollPane(JPanel pane) {
		super(pane);
		this.createVerticalScrollBar();
		this.setForeground(GUI.CONTENT_COLOUR);
		this.setBackground(GUI.BACKGROUND_COLOUR);
		this.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getVerticalScrollBar().setUnitIncrement(GUI.SCROLL_SPEED);
		this.getVerticalScrollBar().setBackground(GUI.BACKGROUND_COLOUR);
		this.setWheelScrollingEnabled(true);
	}
}