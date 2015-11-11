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

	JPanel header;

	protected FoodListPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(Component.LEFT_ALIGNMENT);

		header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_COLOUR);
		header.add(new HomeButton(manager));
		header.add(new BackButton(manager.getGroupPanel(), manager));

	}

	protected void setFoodGroup(FoodGroup group) {
		this.group = group;
		JLabel title = new JLabel();
		title.setText(group.toString()); // check if this works
		title.setFont(GUI.TITLE_FONT);
		title.setOpaque(true);
		header.add(title);
		// header needs to be added to the panel eventually
	}
}
