package gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelManager extends JPanel {

	private HomePanel homePanel;
	private GroupPanel groupPanel;
	private FoodListPanel foodListPanel;
	private SearchPanel searchPanel;
	private InfoPanel infoPanel;
	private ExtraInfoPanel extraInfoPanel;
	private SettingsPanel settingsPanel;

	CardLayout cardLayoutManager;

	public PanelManager() {
		cardLayoutManager = new CardLayout();
		this.setLayout(cardLayoutManager);
		homePanel = new HomePanel(this);
		groupPanel = new GroupPanel();
		foodListPanel = new FoodListPanel();
		searchPanel = new SearchPanel(this);
		settingsPanel = new SettingsPanel(this);

		this.add(homePanel, "home");
		this.add(groupPanel, "group");
		this.add(foodListPanel, "foodList");
		this.add(searchPanel, "search");
//		layoutManager.addLayoutComponent(infoPanel, "info");
//		layoutManager.addLayoutComponent(extraInfoPanel, "extraInfo");
		this.add(settingsPanel, "settings");
	}

	protected void switchToHome() {
		cardLayoutManager.show(this, "home");
	}

	protected void switchToSearch() {
		cardLayoutManager.show(this, "search");
	}

}
