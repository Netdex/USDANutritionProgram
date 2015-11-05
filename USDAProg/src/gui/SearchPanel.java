package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import parser.parsables.FoodItem;

public class SearchPanel extends JPanel {

	PanelManager manager;
	JTextField searchBox;

	public SearchPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel bannerTitlePanel = new JPanel();
		bannerTitlePanel.setLayout(new FlowLayout());
		JButton homeButton = new JButton(new ImageIcon("images/homeButton.png"));
		homeButton.addActionListener(new HomeButtonListener());

		searchBox = new JTextField("Search...", 20);
		bannerTitlePanel.add(homeButton);
		bannerTitlePanel.add(searchBox);
		this.add(bannerTitlePanel);
		searchBox.addKeyListener(new SearchBoxActionListener());
	}

	// TODO unbreak this
	private void findResults(String query) {
		// go find top 25, then displayResults them
		displayResults(null);
	}

	// TODO unbreak this
	private void displayResults(FoodItem[] results) {
		// create a scrollable JList of JButtons that redirect to their
		// respective info pages
		JList<FoodItem> resultsList = new JList<FoodItem>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);

		this.add(resultsList);

		// end up getting the FoodItem that is required, and show its info page
		// DO NOT delete this panel in case they want to go back
	}

	class SearchBoxActionListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
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

	class HomeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			manager.switchToHome();
		}
	}
}
