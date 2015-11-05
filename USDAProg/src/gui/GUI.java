package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GUI {

	JFrame frame;
	HomePanel homePanel;
	GroupPanel groupPanel;
	FoodListPanel foodListPanel;
	SearchPanel searchPanel;
	InfoPanel infoPanel;
	ExtraInfoPanel extraInfoPanel;
	SettingsPanel settingsPanel;

	public GUI() {
		frame = new JFrame("USDA FOOD ORGANIZER");
		frame.setSize(480, 640);
		try {
			frame.setIconImage(ImageIO.read(new File("images/frameIcon.png"))); //TODO make image for this
		} catch (IOException e) {
		}
		homePanel = new HomePanel(frame, this);
		frame.add(homePanel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// TODO find a way to switch panels
	protected void switchToSearch() {
		frame.remove(homePanel);
		searchPanel = new SearchPanel(this);
		frame.add(searchPanel);
	}
}
