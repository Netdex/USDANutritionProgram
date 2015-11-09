package gui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import config.ConfigurationManager;
import parser.DataManager;

public class GUI extends JFrame {

	public static ConfigurationManager CONFIG = new ConfigurationManager(
			new File("config.prop"));

	protected final static Color BACKGROUND_COLOUR = new Color(255, 255, 255);
	protected final static Color HEADER_COLOUR = new Color(192, 192, 192);
	protected final static Color ACCENT_COLOUR = new Color(15718468);

	protected static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	protected static final Font SUBTITLE_FONT = new Font("Calibri", Font.PLAIN,
			18);
	protected static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN,
			16);

	protected static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();

	protected static DataManager dataManager;

	public GUI() {
		super("USDA Food Database");
		CONFIG.load();
		this.setSize(480, 640);
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		dataManager = DataManager.getInstance();

		dataManager.initAsync(new File("USDAFiles/FOOD_DES.TXT"), new File(
				"USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File(
						"USDAFiles/WEIGHT.TXT"), new File(
						"USDAFiles/LANGUAL.txt"), new File(
						"USDAFiles/LANGDESC.TXT"), new File(
						"USDAFiles/FOOTNOTE.TXT"));

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
