package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.parsables.FoodItem;

public class FoodItemButton extends JButton {

	FoodItem food;
	PanelManager manager;

	public FoodItemButton(FoodItem food, PanelManager manager) {
		super();
		this.food = food;
		this.manager = manager;

		JPanel contents = new JPanel();
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		contents.setAlignmentY(Component.LEFT_ALIGNMENT);
		contents.setLayout(layout);

		this.addActionListener(new FoodItemButtonListener());

		JLabel foodDescription = new JLabel(food.getLongDescription());
		foodDescription.setFont(GUI.SUBTITLE_FONT);
		foodDescription.setForeground(Color.BLACK);
		foodDescription.setOpaque(false);
		contents.add(foodDescription);

		JLabel extraInfo = new JLabel(food.getLangualGroup().getLanguaLs()
				.toString()); // does this return all the LanguaLs in a nice
								// string separated by semicolons?
		extraInfo.setFont(GUI.CONTENT_FONT);
		extraInfo.setForeground(GUI.HEADER_GREY);
		extraInfo.setOpaque(false);
		contents.add(extraInfo);
	}

	class FoodItemButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			manager.getInfoPanel().setFoodItem(food);
			manager.switchToInfoPage();
		}

	}
}
