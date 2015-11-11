package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import parser.parsables.FoodGroup;

public class GroupPanel extends JPanel implements ActionListener {

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

		// TODO needs to be a way to find a list of all food groups
		// create buttons for each group (with name), and put buttons into JList

		FoodGroupButton[] groupSelectorModel = {};
		// replace this with a list of all foods under this food group
		JList<FoodGroupButton> groupSelector = new JList<FoodGroupButton>(
				groupSelectorModel);
		groupSelector.setBackground(GUI.BACKGROUND_COLOUR);
		this.add(groupSelector);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		FoodGroupButton source = (FoodGroupButton) event.getSource();

		manager.switchToFoodList(source.getFoodGroup());
	}

	class FoodGroupButton extends JButton {
		FoodGroup foodGroup;

		public FoodGroupButton(FoodGroup foodGroup) {
			super();
			this.foodGroup = foodGroup;
		}

		private FoodGroup getFoodGroup() {
			return foodGroup;
		}
	}
}
