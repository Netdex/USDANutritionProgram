package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(GUI.BACKGROUND_COLOR);
		this.manager = manager;

		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		JLabel title = new JLabel("FOOD GROUPS");
		title.setFont(GUI.TITLE_FONT);
		title.setBackground(GUI.HEADER_COLOR);
		header.add(title);
		header.setBackground(GUI.HEADER_COLOR);
		this.add(header);

		// TODO needs to be a way to find a list of all food groups
		// get a list of all food groups
		// create buttons for each group (with name), and put buttons into JList
		JList<FoodGroupButton> groupSelector = new JList<FoodGroupButton>();
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
