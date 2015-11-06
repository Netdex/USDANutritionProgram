package gui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.parsables.FoodGroup;

public class FoodListPanel extends JPanel {

	PanelManager manager;
	FoodGroup group;

	protected FoodListPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(Component.LEFT_ALIGNMENT);

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_GREY);
		header.add(new HomeButton(manager));
		header.add(new BackButton(manager.getGroupPanel(), manager));

		JLabel title = new JLabel();
		title.setText(group.toString()); // check if this works
		title.setFont(GUI.TITLE_FONT);
		title.setOpaque(true);
	}

	protected void setFoodGroup(FoodGroup group) {
		this.group = group;
	}
}
