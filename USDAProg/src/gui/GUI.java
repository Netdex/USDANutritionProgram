package gui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import config.ConfigurationManager;

public class GUI extends JFrame {

	protected static Color BACKGROUND_COLOR = new Color(16448250);
	protected static Color HEADER_COLOR = new Color(16448250);
	public static ConfigurationManager CONFIG = new ConfigurationManager(
			new File("config.prop"));

	public GUI() {
		super("USDA FOOD ORGANIZER");
		CONFIG.load();
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
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		new GUI();
	}
}
