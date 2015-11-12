package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parser.parsables.FoodGroup;

public class FoodListPanel extends JPanel {

	PanelManager manager;
	FoodGroup group;

	JLabel title;
	JPanel header;

	JPanel foodsList;
	
	protected FoodListPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setAlignmentY(Component.LEFT_ALIGNMENT);

		header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_COLOUR);
		header.add(new HomeButton(manager));
		header.add(new BackButton(manager.getGroupPanel(), manager));
		title = new JLabel();
		title.setFont(GUI.SUBTITLE_FONT);
		header.add(title);

		this.add(header, BorderLayout.NORTH);
		
		foodsList = new JPanel();
		BoxLayout groupsLayout = new BoxLayout(foodsList, BoxLayout.Y_AXIS);
		foodsList.setLayout(groupsLayout);
		foodsList.setBackground(GUI.BACKGROUND_COLOUR);
		
		JScrollPane foodsScrollable = new JScrollPane(foodsList);
		foodsScrollable.createVerticalScrollBar();
		foodsScrollable.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		foodsScrollable
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		foodsScrollable.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		foodsScrollable.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		foodsScrollable.setWheelScrollingEnabled(true);

		this.add(foodsScrollable, BorderLayout.CENTER);
	}

	protected void setFoodGroup(FoodGroup group) {
		this.group = group;
		title.setText(group.getDescription());
	}
}
