package gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import parser.parsables.FoodGroup;

public class PanelManager extends JPanel {

	private HomePanel homePanel;
	private GroupPanel groupPanel;
	private FoodListPanel foodListPanel;
	private SearchPanel searchPanel;
	private InfoPanel infoPanel;
	private ExtraInfoPanel extraInfoPanel;
	private SettingsPanel settingsPanel;
	private AboutPanel aboutPanel;

	CardLayout cardLayoutManager;

	public PanelManager() {
		cardLayoutManager = new CardLayout();
		this.setLayout(cardLayoutManager);
		homePanel = new HomePanel(this);
		groupPanel = new GroupPanel(this);
		foodListPanel = new FoodListPanel();
		searchPanel = new SearchPanel(this);
		settingsPanel = new SettingsPanel(this);
		aboutPanel = new AboutPanel(this);
		infoPanel = new InfoPanel(searchPanel);

		this.add(homePanel, "home");
		this.add(groupPanel, "group");
		this.add(foodListPanel, "foodList");
		this.add(searchPanel, "search");
		// layoutManager.addLayoutComponent(infoPanel, "info");
		// layoutManager.addLayoutComponent(extraInfoPanel, "extraInfo");
		this.add(settingsPanel, "settings");
		this.add(aboutPanel, "about");
	}

	protected void switchToHome() {
		cardLayoutManager.show(this, "home");
	}

	protected void switchToSearch() {
		cardLayoutManager.show(this, "search");
	}

	protected void switchToInfoPage(InfoPanel infoPanel) {
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

	protected void switchToAbout() {
		cardLayoutManager.show(this, "about");

	}

	protected void switchToFoodList(FoodGroup group) {
		cardLayoutManager.show(this, "foodList");
	}

	protected void switchToPanel(JPanel panel) {
		// get the equivalent panel in the list of panels that cardlayoutmanager
		// handles, then switches to it
	}
}
