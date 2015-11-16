package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.DataManager;
import parser.parsables.FoodGroup;

/**
 * List of all food groups
 * 
 * @author Vince Ou
 *
 */
public class GroupPanel extends JPanel {

	/**
	 * Something that handles all the panels1
	 */
	private PanelManager manager;
	/**
	 * Makes scrolling possible
	 */
	private CustomScrollPane groupsScrollable;

	public GroupPanel(PanelManager manager) {
		// Set up
		super();
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);
		this.manager = manager;

		// Creates a header and home button
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		// makes a title text label and adds it
		JLabel title = new JLabel("Food Groups");
		title.setFont(GUI.TITLE_FONT);
		title.setForeground(GUI.TITLE_COLOUR);
		title.setOpaque(false);
		title.setAlignmentX(LEFT_ALIGNMENT);
		header.add(title);

		// Sets backgrounds and such.
		header.setBackground(GUI.HEADER_COLOUR);
		this.add(header, BorderLayout.NORTH);

		// Creates a panel to hold the list of buttons that point to each food
		// group
		JPanel groupsList = new JPanel();
		BoxLayout groupsLayout = new BoxLayout(groupsList, BoxLayout.Y_AXIS);
		groupsList.setLayout(groupsLayout);
		groupsList.setBackground(GUI.BACKGROUND_COLOUR);

		// Gets the buttons only after the list of food groups have been loaded
		// from file.
		Runnable r = new Runnable() {
			public void run() {
				// Gets the food groups, then sorts by alphabetical order
				FoodGroup[] foodGroups = DataManager.getInstance()
						.getFoodGroups();
				Arrays.sort(foodGroups, new Comparator<FoodGroup>() {
					@Override
					public int compare(FoodGroup a, FoodGroup b) {
						return a.getDescription().compareTo(b.getDescription());
					}
				});

				// Creates a button for each group.
				for (int i = 0; i < foodGroups.length; i++) {
					FoodGroupButton button = new FoodGroupButton(foodGroups[i]);
					button.setAlignmentX(LEFT_ALIGNMENT);
					groupsList.add(button);
					groupsList.add(Box.createRigidArea(new Dimension(0, 7)));
				}
			}
		};
		// Does stuff.
		DataManager.getInstance().registerSyncEvent(r);

		// Makes it scrollable!
		groupsScrollable = new CustomScrollPane(groupsList);
		this.add(groupsScrollable, BorderLayout.CENTER);
	}

	/**
	 * Resets the scrolling.
	 * 
	 * @author Vince Ou
	 */
	protected void resetScroll() {
		groupsScrollable.scrollToTop();
	}

	/**
	 * The button that points to each individual food group
	 * 
	 * @author Vince Ou
	 *
	 */
	class FoodGroupButton extends JButton {

		/**
		 * The target for the food group
		 */
		FoodGroup group;

		public FoodGroupButton(FoodGroup foodGroup) {
			// Set up
			super();
			this.group = foodGroup;
			this.addActionListener(new FoodGroupButtonListener());
			this.setMaximumSize(new Dimension(460, 128));
			this.setFocusable(false);
			this.setBorder(GUI.BUTTON_BORDER);

			// Creates a name for each food group and labels it
			JLabel name = new JLabel(group.getDescription());
			name.setFont(GUI.SUBTITLE_FONT);
			this.setBackground(GUI.ACCENT_COLOUR);
			name.setAlignmentX(LEFT_ALIGNMENT);
			name.setFocusable(false);
			name.setOpaque(false);
			name.setForeground(GUI.CONTENT_COLOUR);
			this.add(name);
		}

		/**
		 * Gets the food group
		 * 
		 * @return the food group
		 */
		private FoodGroup getFoodGroup() {
			return group;
		}

		/**
		 * What happens when you click the button for the food group
		 * 
		 * @author Vince Ou
		 *
		 */
		class FoodGroupButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Gets the food group, and then switches to it.
				manager.getFoodListPanel().setFoodGroup(getFoodGroup());
				manager.switchToFoodListPanel();
			}

		}
	}
}
