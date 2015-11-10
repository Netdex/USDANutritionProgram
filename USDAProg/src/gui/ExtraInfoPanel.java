package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import parser.parsables.FoodItem;

public class ExtraInfoPanel extends JPanel {

	private PanelManager manager;
	private FoodItem food;

	protected ExtraInfoPanel(PanelManager panelManager) {
		super();
		this.manager = panelManager;

	}

	protected void setFood(FoodItem item) {
		this.food = item;
		// adds LanguaLs
		if (food.getLangualGroup() != null) {
			JLabel langualsList = new JLabel("<html>"
					+ food.getLangualGroup().getLanguaLs().toString()
					+ "</html>");
			langualsList.setFont(GUI.CONTENT_FONT);
			this.add(langualsList);
		}
	}
}
