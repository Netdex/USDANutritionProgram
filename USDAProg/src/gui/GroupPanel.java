package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parser.DataManager;
import parser.parsables.FoodGroup;

public class GroupPanel extends JPanel {

	PanelManager manager;
	ArrayList<FoodGroupButton> foodGroupButtons;

	public GroupPanel(PanelManager manager) {
		super();
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);
		this.manager = manager;

		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		JLabel title = new JLabel("FOOD GROUPS");
		title.setFont(GUI.TITLE_FONT);
		title.setBackground(GUI.HEADER_COLOUR);
		header.add(title);
		header.setBackground(GUI.HEADER_COLOUR);
		this.add(header, BorderLayout.NORTH);

		// TODO create buttons for each group (with name), and put buttons into
		// scrollable list
		JPanel groupsList = new JPanel();
		BoxLayout groupsLayout = new BoxLayout(groupsList, BoxLayout.Y_AXIS);
		groupsList.setLayout(groupsLayout);
		groupsList.setBackground(GUI.BACKGROUND_COLOUR);
		
		Runnable r = new Runnable(){
			public void run(){
				FoodGroup[] foodGroups = DataManager.getInstance().getFoodGroups();
				for (int i = 0; i < foodGroups.length; i++) {
					FoodGroupButton button = new FoodGroupButton(foodGroups[i]);
					button.setAlignmentX(LEFT_ALIGNMENT);
					groupsList.add(button);
					System.out.println(foodGroups[i].getDescription());
				}
			}
		};
		DataManager.getInstance().registerSyncEvent(r);

		JScrollPane groupsScrollable = new JScrollPane(groupsList);
		groupsScrollable.createVerticalScrollBar();
		groupsScrollable.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		groupsScrollable
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		groupsScrollable.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		groupsScrollable.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		groupsScrollable.setWheelScrollingEnabled(true);

		this.add(groupsScrollable, BorderLayout.CENTER);
	}

	class FoodGroupButton extends JButton {
		FoodGroup group;

		public FoodGroupButton(FoodGroup foodGroup) {
			super();
			this.group = foodGroup;
			this.addActionListener(new FoodGroupButtonListener());
			this.setMaximumSize(new Dimension(460, 128));
			JLabel name = new JLabel(group.getDescription());
			name.setFont(GUI.CONTENT_FONT);
			this.setBackground(GUI.ACCENT_COLOUR);
			name.setAlignmentX(LEFT_ALIGNMENT);
			name.setFocusable(false);
			name.setOpaque(false);
			this.add(name);
		}

		private FoodGroup getFoodGroup() {
			return group;
		}

		class FoodGroupButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.getFoodListPanel().setFoodGroup(getFoodGroup());
				manager.switchToFoodList();
			}

		}
	}
}
