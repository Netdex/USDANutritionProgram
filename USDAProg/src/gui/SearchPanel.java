package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import parser.parsables.FoodItem;

public class SearchPanel extends JPanel {

	private PanelManager manager;
	private JPanel contents;
	private JTextField searchBox;
	private JList<FoodItemButton> resultsList;
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

		contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		contents.setBackground(GUI.BACKGROUND_COLOUR);
	}

	private void findResults(String query) {
		// TODO deal with an empty array
		FoodItem[] results = GUI.dataManager.searchForItem(query.split(" "));
		FoodItemButton[] resultsListModel = new FoodItemButton[results.length];
		
		for (int i = 0; i < results.length; i++) {
			resultsListModel[i] = new FoodItemButton(results[i], manager);
		}

		resultsList = new JList<FoodItemButton>(resultsListModel);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		contents.add(resultsList);

		// when button clicked, then switch to it...
	}

	protected void resetSearchBox() {
		searchBox.setText("Search...");
		searchBox.setForeground(searchBoxGray);
		resultsList = null;
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

			JPanel contents = new JPanel();
			BoxLayout layout = new BoxLayout(contents, BoxLayout.Y_AXIS);
			contents.setAlignmentY(Component.LEFT_ALIGNMENT);
			contents.setLayout(layout);

			//TODO make this shorter!
			JLabel foodDescription = new JLabel(food.getLongDescription());
			foodDescription.setFont(GUI.SUBTITLE_FONT);
			foodDescription.setForeground(Color.BLACK);
			foodDescription.setOpaque(false);
			contents.add(foodDescription);

			JLabel extraInfo = new JLabel(this.food.getFoodGroup()
					.getDescription());
			extraInfo.setFont(GUI.CONTENT_FONT);
			extraInfo.setForeground(GUI.HEADER_COLOUR);
			extraInfo.setOpaque(false);
			contents.add(extraInfo);
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
			// long currentTime = System.currentTimeMillis();
			// if (currentTime - prevKeyPressedTime >= 800) {
			// findResults(searchBox.getText());
			// prevKeyPressedTime = currentTime;
			// }
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// nothing
		}
	}

}
