package gui;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel{

	GUI frame;

	public SearchPanel(GUI frame) {
		this.frame = frame;
		go();
	}

	private void go() {
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton homeButton = new JButton(new ImageIcon("homeButton.png"));
		JTextField searchBox = new JTextField("Search...", 20);
		titlePanel.add(homeButton);
		titlePanel.add(searchBox);
		this.add(titlePanel);
		
		
	}

}
