package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

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
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOR);

		JPanel bannerTitlePanel = new JPanel();
		bannerTitlePanel.setLayout(new FlowLayout());
		bannerTitlePanel.setBackground(GUI.HEADER_COLOR);
		try {
			bannerTitlePanel.add(new HomeButton(manager));
		} catch (IOException e) {
		}

		searchBox = new JTextField("Search...", 16);
		searchBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
		searchBox.setBackground(GUI.BACKGROUND_COLOR);
		searchBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				searchBox.setText("");
			}
		});
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

	protected void reset() {
		searchBox.setText("Search...");
		// clear all previous search results
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
}
