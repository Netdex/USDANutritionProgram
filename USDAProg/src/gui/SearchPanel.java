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

public class SearchPanel extends JPanel {

	PanelManager manager;
	JTextField searchBox;

	public SearchPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_WHITE);

		JPanel bannerTitlePanel = new JPanel();
		bannerTitlePanel.setLayout(new FlowLayout());
		bannerTitlePanel.setBackground(GUI.HEADER_GREY);
		bannerTitlePanel.add(new HomeButton(manager));

		searchBox = new JTextField("Search...", 22);
		searchBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		searchBox.setBackground(GUI.BACKGROUND_WHITE);
		searchBox.setForeground(GUI.HEADER_GREY);
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

	// TODO make searching work
	private void findResults(String query) {
		// go find top 25, then displayResults them

		ArrayList<FoodItemButton> resultsListModel = new ArrayList<FoodItemButton>();
		JList<FoodItemButton> resultsList = new JList<FoodItemButton>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);

		// when clicked, then switch to it...
	}

	protected void resetSearchBox() {
		searchBox.setText("Search...");
		searchBox.setForeground(GUI.HEADER_GREY);
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
