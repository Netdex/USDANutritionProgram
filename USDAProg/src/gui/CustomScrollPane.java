package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A custom JScrollPane that saves a lot of typing, because all of the
 * JScrollPanes in the entire program have the same setup parameters.
 * 
 * @author Vince Ou
 *
 */
class CustomScrollPane extends JScrollPane {

	/**
	 * Speed of mouse wheel scrolling
	 */
	private static final byte SCROLL_SPEED = 20;

	CustomScrollPane(JPanel pane) {
		super(pane);
		this.createVerticalScrollBar();
		this.setForeground(GUI.CONTENT_COLOUR);
		this.setBackground(GUI.BACKGROUND_COLOUR);
		this.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
		this.getVerticalScrollBar().setBackground(GUI.BACKGROUND_COLOUR);
		this.setWheelScrollingEnabled(true);
		this.setHorizontalScrollBar(null);
	}

	void scrollToTop() {
		this.getVerticalScrollBar().setValue(0);
	}
}