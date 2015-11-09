package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import parser.DataManager;
import parser.parsables.FoodItem;

public class SearchPanel extends JPanel {

	PanelManager manager;
	JTextField searchBox;

	Color searchBoxGray = new Color(2, 2, 2);

	public SearchPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_BLUE);

		JPanel bannerTitlePanel = new JPanel();
		bannerTitlePanel.setLayout(new FlowLayout());
		bannerTitlePanel.setBackground(GUI.HEADER_ORANGE);
		bannerTitlePanel.add(new HomeButton(manager));

		searchBox = new JTextField("Search...", 22);
		searchBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		searchBox.setBackground(Color.WHITE);
		searchBox.setForeground(searchBoxGray);
		searchBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				searchBox.setText("");
				searchBox.setForeground(Color.BLACK);
			}
		});
		bannerTitlePanel.add(searchBox);
		searchBox.addKeyListener(new SearchBoxActionListener());
		this.add(bannerTitlePanel, BorderLayout.NORTH);
	}

	private void findResults(String query) {
		// deal with an empty array
		FoodItem[] results = GUI.dataManager.searchForItem(query.split(" "));
		FoodItemButton[] resultsListModel = new FoodItemButton[results.length];

		for (int i = 0; i < results.length; i++) {
			resultsListModel[i] = new FoodItemButton(results[i], manager);
		}
		JList<FoodItemButton> resultsList = new JList<FoodItemButton>(
				resultsListModel);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);

		// when clicked, then switch to it...
	}

	protected void resetSearchBox() {
		searchBox.setText("Search...");
		searchBox.setForeground(searchBoxGray);
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
