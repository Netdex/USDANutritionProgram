package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GUI extends JFrame {

	private double userNutritionMultiplier; 
	// TODO move this somewhere more appropriate. Also should be saved on the HDD

	public GUI() {
		super("USDA FOOD ORGANIZER");
		this.setSize(480, 640);
		try {
			// TODO make proper image
			this.setIconImage(ImageIO.read(new File("images/thisIcon.png")));
		} catch (IOException e) {
		}
		PanelManager manager = new PanelManager();
		this.add(manager);
		manager.switchToHome();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	protected void setDailyCal(double dailyCal) {
		this.userNutritionMultiplier = dailyCal / 2000;
	}
}
