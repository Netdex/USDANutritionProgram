package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GUIFrame {

	JFrame frame;
	HomePanel homePanel;
	GroupPanel groupPanel;
	FoodListPanel foodListPanel;
	SearchPanel searchPanel;
	InfoPanel infoPanel;
	ExtraInfoPanel extraInfoPanel;
	SettingsPanel settingsPanel;

	public GUIFrame() {
		frame = new JFrame("USDA FOOD ORGANIZER");
		try {
			frame.setIconImage(ImageIO.read(new File("frameIcon.png")));
		} catch (IOException e) {
		}
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		homePanel = new HomePanel(this);
		frame.add(homePanel);
	}

	protected void switchToSearch() {
		frame.remove(homePanel);
		searchPanel = new SearchPanel(this);
		frame.add(searchPanel);
	}
}
