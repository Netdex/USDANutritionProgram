package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GUI extends JFrame {

	HomePanel homePanel;
	GroupPanel groupPanel;
	FoodListPanel foodListPanel;
	SearchPanel searchPanel;
	InfoPanel infoPanel;
	ExtraInfoPanel extraInfoPanel;
	SettingsPanel settingsPanel;

	public GUI() {
		super("USDA FOOD ORGANIZER");
		this.setSize(480, 640);
		try {
			// TODO make icon image
			this.setIconImage(ImageIO.read(new File("images/thisIcon.png"))); 
		} catch (IOException e) {
		}
		homePanel = new HomePanel(this);
		this.add(homePanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	protected void switchToSearch() {
		this.remove(this.getContentPane());
		searchPanel = new SearchPanel(this);
		this.add(searchPanel);
	}
}
