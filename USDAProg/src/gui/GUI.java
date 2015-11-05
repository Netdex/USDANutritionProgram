package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GUI extends JFrame {

	private HomePanel homePanel;
	private GroupPanel groupPanel;
	private FoodListPanel foodListPanel;
	private SearchPanel searchPanel;
	private InfoPanel infoPanel;
	private ExtraInfoPanel extraInfoPanel;
	private SettingsPanel settingsPanel;

	private double dailyCal;

	public GUI() {
		super("USDA FOOD ORGANIZER");
		this.setSize(480, 640);
		try {
			// TODO make proper image
			this.setIconImage(ImageIO.read(new File("images/thisIcon.png")));
		} catch (IOException e) {
		}
		homePanel = new HomePanel(this);
		this.add(homePanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	protected void showSearch() {
		searchPanel = new SearchPanel(this);
		this.add(searchPanel);
	}

	protected void showSettings() {
		settingsPanel = new SettingsPanel(this);
		this.add(settingsPanel);
	}

	public double getDailyCal() {
		return dailyCal;
	}

	protected void setDailyCal(double dailyCal) {
		this.dailyCal = dailyCal;
	}
}
