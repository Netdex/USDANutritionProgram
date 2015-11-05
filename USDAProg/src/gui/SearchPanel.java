package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {

	GUI gui;
	JTextField searchBox;

	public SearchPanel(GUI gui) {
		this.gui = gui;

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton homeButton = new JButton(new ImageIcon("images/homeButton.png"));
		searchBox = new JTextField("Search...", 20);
		titlePanel.add(homeButton);
		titlePanel.add(searchBox);
		this.add(titlePanel);
		searchBox.addKeyListener(new SearchBoxActionListener());
	}

	private void findResults(String query) {

	}

	class SearchBoxActionListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				findResults(searchBox.getText());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// nothing
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// nothing
		}

	}
}
