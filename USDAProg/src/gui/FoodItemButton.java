package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.ImageExtract;
import parser.parsables.FoodItem;

public class FoodItemButton extends JButton {

	FoodItem food;
	PanelManager manager;

	public FoodItemButton(FoodItem food, PanelManager manager) {
		super();
		this.food = food;
		this.manager = manager;
		this.setBackground(GUI.ACCENT_COLOUR);

		JPanel contents = new JPanel();
		FlowLayout layout = new FlowLayout();
		contents.setAlignmentY(Component.LEFT_ALIGNMENT);
		contents.setLayout(layout);

		this.addActionListener(new FoodItemButtonListener());

		JLabel foodDescription = new JLabel(food.getLongDescription());
		foodDescription.setFont(GUI.SUBTITLE_FONT);
		foodDescription.setForeground(Color.BLACK);
		foodDescription.setOpaque(false);
		
		JLabel uhm = new JLabel("TEST");
		uhm.setIcon(new ImageIcon(ImageExtract.getSearchImage(food.getLongDescription()
				.substring(0, food.getLongDescription().indexOf(",")))));
		contents.add(foodDescription);
		contents.add(uhm);
	}

	class FoodItemButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			manager.getInfoPanel().setFoodItem(food);
			manager.switchToInfoPanel();
		}

	}
}
