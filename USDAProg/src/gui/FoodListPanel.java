package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;

/**
 * A list of all the foods in a food group.
 * 
 * @author Vince Ou
 *
 */
class FoodListPanel extends JPanel {

	/**
	 * Card layout manager switcher thing
	 */
	private final PanelManager manager;
	/**
	 * The food group being shown
	 */
	private FoodGroup group;

	/**
	 * The title label
	 */
	private final JLabel title;
	/**
	 * The header
	 */
	private final JPanel header;

	/**
	 * The panel displaying the list of foods
	 */
	private final JPanel foodsList;
	/**
	 * The thing making the food scrollable
	 */
	private final CustomScrollPane foodsScrollable;

	FoodListPanel(PanelManager manager) {
		// Set up
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());

		// Creates a header
		header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_COLOUR);
		// Adds home and back buttons (to return to the list of food groups)
		header.add(new HomeButton(manager));
		header.add(new BackButton(manager.getGroupPanel(), manager));
		// Sets the title, but empty
		title = new JLabel();
		title.setForeground(GUI.TITLE_COLOUR);
		title.setFont(GUI.SUBTITLE_FONT);
		header.add(title);
		this.add(header, BorderLayout.NORTH);

		// Creates a panel to hold the list of food item buttons
		foodsList = new JPanel();
		BoxLayout groupsLayout = new BoxLayout(foodsList, BoxLayout.Y_AXIS);
		foodsList.setLayout(groupsLayout);
		foodsList.setBackground(GUI.BACKGROUND_COLOUR);

		// Makes it scrollable
		foodsScrollable = new CustomScrollPane(foodsList);
		this.add(foodsScrollable, BorderLayout.CENTER);
	}

	/**
	 * Sets the food group only when it is selected, during run time.
	 * 
	 * @param foodGroup
	 *            the food group to display
	 * @author Vince Ou
	 */
	void setFoodGroup(FoodGroup foodGroup) {
		// Sets things and removes previous items
		this.group = foodGroup;
		title.setText(group.getDescription());
		foodsScrollable.getVerticalScrollBar().setValue(0);
		foodsList.removeAll();

		// Gets the list of foods
		FoodItem[] foods = group.getFoods().toArray(new FoodItem());

		// Makes buttons and adds them for each food in the group
		for (int i = 0; i < foods.length; i++) {
			FoodButton button = new FoodButton(foods[i], manager);
			foodsList.add(button);
			foodsList.add(Box.createRigidArea(new Dimension(0, 7)));
		}

		// Refresh
		foodsList.revalidate();
		foodsList.repaint();
	}

	/**
	 * Each button represents one food, and redirects to the food's individual
	 * info page when clicked.
	 * 
	 * @author Vince Ou
	 *
	 */
	class FoodButton extends JButton {
		/**
		 * The food that the item redirects to
		 */
		final FoodItem food;
		/**
		 * Manager thingamabob.
		 */
		final PanelManager manager;

		public FoodButton(FoodItem food, PanelManager manager) {
			// Sets up things.
			super();
			this.food = food;
			this.manager = manager;
			this.setBackground(GUI.ACCENT_COLOUR);
			this.addActionListener(new FoodButtonListener());
			this.setLayout(new BorderLayout());
			this.setMaximumSize(new Dimension(460, Short.MAX_VALUE));
			this.setFocusable(false);
			this.setBorder(GUI.BUTTON_BORDER);

			// Creates the text for the button.
			// HTML formatting means auto-text
			// wrapping. JTextAreas are used elsewhere because they have better
			// text wrapping capabilities, but take longer to load.
			JLabel foodDescription = new JLabel("<html>"
					+ food.getLongDescription() + "</html>");
			foodDescription.setFont(GUI.SUBTITLE_FONT);
			foodDescription.setForeground(GUI.CONTENT_COLOUR);
			foodDescription.setOpaque(false);
			this.add(foodDescription, BorderLayout.CENTER);
		}

		/**
		 * What happens when you click the button to go to a food item page
		 * 
		 * @author Vince Ou
		 *
		 */
		class FoodButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Sets the food item for the info panel, and
				// then changes the target for the back button in the info
				// panel,
				// then switches to the info panel
				manager.getInfoPanel().setFoodItem(food);
				manager.getInfoPanel().getBackButton()
						.setTarget(FoodListPanel.this);
				manager.switchToInfoPanel();
			}

		}
	}
}
