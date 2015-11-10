package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import parser.parsables.FoodItem;

public class SearchPanel extends JPanel {

	private PanelManager manager;
	private JTextField searchBox;
	private JScrollPane resultsList;
	private JPanel resultsPanel;
	private long prevKeyPressedTime;

	Color searchBoxGray = new Color(2, 2, 2);

	public SearchPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		JPanel bannerTitlePanel = new JPanel();
		bannerTitlePanel.setLayout(new FlowLayout());
		bannerTitlePanel.setBackground(GUI.HEADER_COLOUR);
		bannerTitlePanel.add(new HomeButton(manager));

		searchBox = new JTextField("Search...", 22);
		searchBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		searchBox.setBackground(Color.WHITE);
		searchBox.setForeground(searchBoxGray);
		searchBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (searchBox.getText().equals("Search...")) {
					searchBox.setText("");
					searchBox.setForeground(Color.BLACK);
				}
			}
		});
		bannerTitlePanel.add(searchBox);
		searchBox.addKeyListener(new SearchBoxActionListener());
		this.add(bannerTitlePanel, BorderLayout.NORTH);

		resultsPanel = new JPanel();
		resultsPanel.setBackground(GUI.BACKGROUND_COLOUR);
		BoxLayout resultsPanelLayout = new BoxLayout(resultsPanel,
				BoxLayout.Y_AXIS);
		resultsPanel.setAlignmentY(LEFT_ALIGNMENT);
		resultsPanel.setLayout(resultsPanelLayout);

		resultsList = new JScrollPane(resultsPanel);
		resultsList.createVerticalScrollBar();
		resultsList
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		resultsList.getVerticalScrollBar().setUnitIncrement(GUI.SCROLL_SPEED);
		resultsList.setWheelScrollingEnabled(true);
		resultsList.setHorizontalScrollBar(null);
		this.add(resultsList);
	}

	private void findResults(String query) {
		resultsPanel.removeAll();
		// TODO deal with an empty array
		FoodItem[] results = GUI.dataManager.searchForItem(query.split(" "));
		if (results.length > 0)
			for (int i = 0; i < results.length; i++) {
				FoodItemButton button = new FoodItemButton(results[i], manager);
				resultsPanel.add(button);
				button.repaint();
			}
		else {
			JLabel notFound = new JLabel("No results found");
			notFound.setFont(GUI.CONTENT_FONT);
			resultsPanel.add(notFound);
		}

		resultsPanel.revalidate();
		resultsPanel.repaint();
	}

	protected void resetSearchBox() {
		searchBox.setText("Search...");
		searchBox.setForeground(searchBoxGray);
	}

	class FoodItemButton extends JButton {

		FoodItem food;
		PanelManager manager;

		public FoodItemButton(FoodItem food, PanelManager manager) {
			super();
			this.food = food;
			this.manager = manager;
			this.setBackground(GUI.ACCENT_COLOUR);
			this.addActionListener(new FoodItemButtonListener());
			this.setLayout(new BorderLayout());
			this.setMaximumSize(new Dimension(460, 128));

			JLabel foodDescription = new JLabel("<html>"
					+ food.getLongDescription() + "</html>");
			foodDescription.setFont(GUI.SUBTITLE_FONT);
			foodDescription.setForeground(Color.BLACK);
			foodDescription.setOpaque(false);
			this.add(foodDescription);
		}

		class FoodItemButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.getInfoPanel().setFoodItem(food);
				manager.switchToInfoPanel();
			}

		}
	}

	class SearchBoxActionListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER
					&& !searchBox.getText().equals("Search..."))
				findResults(searchBox.getText());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() != KeyEvent.VK_ENTER) {
				// Find out a way to search when the "user stops typing"

				// ie. after the last press, wait 800ms, then search.
				long currentTime = System.currentTimeMillis();
				String query = searchBox.getText();
				if (currentTime - prevKeyPressedTime >= 800
						&& query.length() >= 3) {
					findResults(query);
					prevKeyPressedTime = currentTime;
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// nothing
		}
	}

}
