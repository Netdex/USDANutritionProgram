package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import parser.parsables.FoodItem;

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

	// TODO unbreak this
	private void findResults(String query) {
		// go find top 25, then displayResults them
		displayResults(null);
	}
	
	// TODO unbreak this
	private void displayResults(FoodItem[] results) {
		// create a scrollable JList of JButtons that redirect to their respective info pages
		JList<FoodItem> resultsList = new JList<FoodItem>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		
		// end up getting the FoodItem that is required, and show its info page
		// DO NOT delete this panel in case they want to go back
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
