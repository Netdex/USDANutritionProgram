package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import parser.ImageExtract;
import parser.parsables.FoodItem;

/**
 * The panel that manages search
 * 
 * @author Vince Ou
 *
 */
class SearchPanel extends JPanel {

	/**
	 * The card-switching, panel-managing, backend-glueing panel
	 */
	private final PanelManager manager;
	/**
	 * The search box used to enter search terms
	 */
	private final JTextField searchBox;
	/**
	 * The scrolling pane that holds the results panel
	 */
	private final CustomScrollPane resultsList;
	/**
	 * The panel holding the results
	 */
	private final JPanel resultsPanel;
	/**
	 * The last time a key was pressed (used to auto-search)
	 */
	private long prevKeyPressedTime;
	/**
	 * Whether or not the delay should search
	 */
	private boolean shouldSearch = true;

	public SearchPanel(PanelManager manager) {
		// Set up
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// The header
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_COLOUR);
		// Adds a home button
		header.add(new HomeButton(manager));

		// Creates and adds a search box to the header
		searchBox = new JTextField("Search...", 22);
		searchBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		searchBox.setBackground(Color.WHITE);
		searchBox.setForeground(GUI.SEARCH_BOX_GREY_GRAY);
		searchBox.setBorder(GUI.EMPTY_BORDER);
		searchBox.setAlignmentX(LEFT_ALIGNMENT);
		searchBox.setMinimumSize(new Dimension(400, 0));
		searchBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (searchBox.getText().equals("Search...")) {
					searchBox.setText("");
					searchBox.setForeground(GUI.CONTENT_COLOUR);
				}
			}
		});
		header.add(searchBox);
		searchBox.addKeyListener(new SearchBoxActionListener());
		this.add(header, BorderLayout.NORTH);

		// Creates a panel to hold the results
		resultsPanel = new JPanel();
		resultsPanel.setBackground(GUI.BACKGROUND_COLOUR);
		BoxLayout resultsPanelLayout = new BoxLayout(resultsPanel,
				BoxLayout.Y_AXIS);
		resultsPanel.setAlignmentX(LEFT_ALIGNMENT);
		resultsPanel.setLayout(resultsPanelLayout);

		// Makes results scrollable
		resultsList = new CustomScrollPane(resultsPanel);

		new Thread() {
			public void run() {
				while (true) {
					try {
						// framework for auto-searching after the user stops
						// typing
						if (shouldSearch
								&& System.currentTimeMillis()
										- prevKeyPressedTime >= 500) {
							String txt = searchBox.getText();
							// The user has intended to search for something
							if (!txt.equals("Search...") && !txt.equals(""))
								findResults(txt);
							else {
								// Clear the results panel
								resetResults();
							}
							// Stops search
							shouldSearch = false;
						}
						// Delay, so the the search doesn't trip over itself
						Thread.sleep(50);
					} catch (Exception e) {

					}
				}
			}
		}.start();
		// Adds the list
		this.add(resultsList);
	}

	/**
	 * Finds and displays the results of the search
	 * 
	 * @param query
	 *            the String the user searched for
	 */
	private void findResults(String query) {
		// Clears the previous results and scrolls to the top
		resultsPanel.removeAll();
		resultsList.getVerticalScrollBar().setValue(0);
		// Sends the results to be processed
		FoodItem[] results = GUI.dataManager.searchForItem(query.split(" "));
		if (results.length > 0)
			// There are results, and will create a button for every result,
			// along with padding
			for (int i = 0; i < results.length; i++) {
				FoodItemButton button = new FoodItemButton(results[i], manager);
				resultsPanel.add(button);
				resultsPanel.add(Box.createRigidArea(new Dimension(0, 7)));
				button.repaint();
			}
		else {
			// No results found, displays a message
			JLabel notFound = new JLabel("No results found");
			notFound.setPreferredSize(new Dimension(450, 100));
			notFound.setFont(GUI.SUBTITLE_FONT);
			resultsPanel.add(notFound);
		}
		// Preloads the images in the results
		System.out.println("preloading");
		ImageExtract.preloadImages(results);

		// Refresh!
		resultsPanel.revalidate();
		resultsPanel.repaint();
	}

	/**
	 * Resets the search box (typically used after switching back to this
	 * window)
	 */
	void resetSearchBox() {
		searchBox.setText("Search...");
		searchBox.setForeground(GUI.SEARCH_BOX_GREY_GRAY);
	}

	/**
	 * Resets the list of results. Used when switching back to this from, say,
	 * the home screen, or when coming here from the foodItem page
	 */
	void resetResults() {
		// Clears everything, refreshes
		resultsPanel.removeAll();
		resultsPanel.revalidate();
		resultsPanel.repaint();

		// Adds a prompt for the user to enter something into the search box
		JLabel searchPrompt = new JLabel(
				"<html>Type something in the search box to get started...</html>");
		searchPrompt.setPreferredSize(new Dimension(450, 100));
		searchPrompt.setFont(GUI.SUBTITLE_FONT);
		resultsPanel.add(searchPrompt);
	}

	/**
	 * The button for each food item.
	 * 
	 * @author Vince Ou
	 *
	 */
	class FoodItemButton extends JButton {

		/**
		 * The food being represented
		 */
		final FoodItem food;
		/**
		 * The manager for the panel switching
		 */
		final PanelManager manager;

		/**
		 * Creates a button
		 * 
		 * @param food
		 *            the FoodItem to represent
		 * @param manager
		 *            the switching panel
		 */
		public FoodItemButton(FoodItem food, PanelManager manager) {
			// Sets up things
			super();
			this.food = food;
			this.manager = manager;
			this.setBackground(GUI.ACCENT_COLOUR);
			this.addActionListener(new FoodItemButtonListener());
			this.setLayout(new BorderLayout());
			this.setMaximumSize(new Dimension(460, 100));
			this.setFocusable(false);
			this.setBorder(GUI.BUTTON_BORDER);

			// Adds the name of the food first
			JLabel foodDescription = new JLabel("<html>"
					+ food.getLongDescription() + "</html>");
			foodDescription.setFont(GUI.SUBTITLE_FONT);
			foodDescription.setForeground(Color.BLACK);
			foodDescription.setForeground(GUI.CONTENT_COLOUR);
			foodDescription.setOpaque(false);
			this.add(foodDescription, BorderLayout.CENTER);

			// Then adds the food group category it is in afterwards
			JLabel foodGroupName = new JLabel("<html>"
					+ food.getFoodGroup().toString() + "</html>");
			foodGroupName.setFont(GUI.CONTENT_FONT);
			foodGroupName.setForeground(Color.BLACK);
			foodGroupName.setForeground(GUI.CONTENT_COLOUR);
			foodGroupName.setOpaque(false);
			this.add(foodGroupName, BorderLayout.SOUTH);
		}

		/**
		 * Lets the button be clickable
		 * 
		 * @author Vince Ou
		 *
		 */
		class FoodItemButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Sets up the foodinfo panel
				manager.getInfoPanel().setFoodItem(food);
				manager.getInfoPanel().getBackButton()
						.setTarget(SearchPanel.this);
				// switches to that panel
				manager.switchToInfoPanel();
			}

		}
	}

	/**
	 * Lets the user press enter in the search box to search, in case they don't
	 * want to wait for the auto-search, or they do it out of habit
	 * 
	 * Also allows the auto-searching-when-the-user-stops-typing function to
	 * work by resetting the prevKeyPressedTime each time
	 * 
	 * @author Vince Ou
	 *
	 */
	private class SearchBoxActionListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) {
			// gets the key input, and changes the previous key pressed time as
			// necessary
			if (event.getKeyCode() == KeyEvent.VK_ENTER)
				findResults(searchBox.getText());
			prevKeyPressedTime = System.currentTimeMillis();
			shouldSearch = true;
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