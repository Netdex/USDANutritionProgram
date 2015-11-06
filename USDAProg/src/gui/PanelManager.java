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
	private HelpPanel helpPanel;

	CardLayout cardLayoutManager;

	public PanelManager() {
		cardLayoutManager = new CardLayout();
		this.setLayout(cardLayoutManager);
		homePanel = new HomePanel(this);
		groupPanel = new GroupPanel(this);
		foodListPanel = new FoodListPanel(this);
		searchPanel = new SearchPanel(this);
		settingsPanel = new SettingsPanel(this);
		aboutPanel = new AboutPanel(this);
		infoPanel = new InfoPanel();
		extraInfoPanel = new ExtraInfoPanel(this);
		helpPanel = new HelpPanel(this);

		this.add(homePanel, "home");
		this.add(groupPanel, "group");
		this.add(foodListPanel, "foodList");
		this.add(searchPanel, "search");
		this.add(settingsPanel, "settings");
		this.add(aboutPanel, "about");
		this.add(helpPanel, "help");
		this.add(extraInfoPanel, "extraInfo");
	}

	protected InfoPanel getInfoPanel() {
		return infoPanel;
	}

	protected void switchToHome() {
		cardLayoutManager.show(this, "home");
	}

	protected void switchToSearchPanel() {
		cardLayoutManager.show(this, "search");
		searchPanel.reset();
	}

	protected void switchToInfoPanel() {
		cardLayoutManager.show(this, "foodInfo");
	}

	protected void switchToGroup() {
		cardLayoutManager.show(this, "group");
	}

	protected void switchToSettings() {
		cardLayoutManager.show(this, "settings");
	}

	protected void switchToHelp() {
		cardLayoutManager.show(this, "help");
	}

	protected void switchToAbout() {
		cardLayoutManager.show(this, "about");

	}

	protected void switchToFoodList(FoodGroup group) {
		cardLayoutManager.show(this, "foodList");
	}

	protected JPanel getGroupPanel() {
		return groupPanel;
	}
}
