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
		groupPanel = new GroupPanel(this);
		foodListPanel = new FoodListPanel();
		searchPanel = new SearchPanel(this);
		settingsPanel = new SettingsPanel(this);

		this.add(homePanel, "home");
		this.add(groupPanel, "group");
		this.add(foodListPanel, "foodList");
		this.add(searchPanel, "search");
		// layoutManager.addLayoutComponent(infoPanel, "info");
		// layoutManager.addLayoutComponent(extraInfoPanel, "extraInfo");
		this.add(settingsPanel, "settings");
	}

	protected void switchToHome() {
		cardLayoutManager.show(this, "home");
	}

	protected void switchToSearch() {
		searchPanel.reset();
		cardLayoutManager.show(this, "search");
	}

	protected void switchToInfoPage(InfoPanel infoPanel) {
		this.add(infoPanel, "foodInfo");
		cardLayoutManager.show(this, "foodInfo");
	}

	protected void switchToGroup() {
		cardLayoutManager.show(this, "group");
	}

	protected void switchToSettings() {
		cardLayoutManager.show(this, "settings");
	}

	protected void switchToBookmarks() {
		cardLayoutManager.show(this, "bookmarks");
	}
}
