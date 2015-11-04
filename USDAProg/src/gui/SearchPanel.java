package gui;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel {

	GUIFrame frame;

	public SearchPanel(GUIFrame frame) {
		this.frame = frame;
		go();
	}

	private void go() {
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton homeButton = new JButton(new ImageIcon("homeButton.png"));
		JTextField searchBox = new JTextField("Search...");
		title.add(homeButton);
		title.add(searchBox);
	}

}
