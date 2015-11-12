package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;

public class FoodListPanel extends JPanel {

	private PanelManager manager;
	private FoodGroup group;

	private JLabel title;
	private JPanel header;

	private JPanel foodsList;
	private JScrollPane foodsScrollable;

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

		foodsScrollable = new JScrollPane(foodsList);
		foodsScrollable.createVerticalScrollBar();
		foodsScrollable.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		foodsScrollable
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		foodsScrollable.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		foodsScrollable.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		foodsScrollable.setWheelScrollingEnabled(true);
		foodsScrollable.setHorizontalScrollBar(null);

		this.add(foodsScrollable, BorderLayout.CENTER);
	}

	protected void setFoodGroup(FoodGroup foodGroup) {
		this.group = foodGroup;
		title.setText(group.getDescription());
		foodsScrollable.getVerticalScrollBar().setValue(0);

		FoodItem[] foods = group.getFoods().toArray(new FoodItem());

		for (int i = 0; i < foods.length; i++) {
			FoodButton button = new FoodButton(foods[i], manager);
			foodsList.add(button);
		}

		foodsList.revalidate();
		foodsList.repaint();
	}

	class FoodButton extends JButton {
		FoodItem food;
		PanelManager manager;

		public FoodButton(FoodItem food, PanelManager manager) {
			super();
			this.food = food;
			this.manager = manager;
			this.setBackground(GUI.ACCENT_COLOUR);
			this.addActionListener(new FoodButtonListener());
			this.setLayout(new BorderLayout());
			this.setMaximumSize(new Dimension(460, Short.MAX_VALUE));

			JLabel foodDescription = new JLabel("<html>"
					+ food.getLongDescription() + "</html>");
			foodDescription.setFont(GUI.SUBTITLE_FONT);
			foodDescription.setForeground(Color.BLACK);
			foodDescription.setOpaque(false);
			this.add(foodDescription, BorderLayout.CENTER);
		}

		class FoodButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.getInfoPanel().setFoodItem(food);
				manager.getInfoPanel().getBackButton()
						.setTarget(FoodListPanel.this);
				manager.switchToInfoPanel();
			}

		}
	}
}
