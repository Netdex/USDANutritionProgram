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

import parser.DataManager;
import config.ConfigurationManager;

public class GUI extends JFrame {

	public static final ConfigurationManager CONFIG = new ConfigurationManager(
			new File("config.prop"));

	public final static Color BACKGROUND_COLOUR = new Color(255, 255, 255);
	public final static Color HEADER_COLOUR = new Color(192, 192, 192);
	public final static Color ACCENT_COLOUR = new Color(15718468);

	protected static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	protected static final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD,
			20);
	protected static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN,
			17);
	protected static final Font SCIENTIFIC_FONT = new Font("Calibri",
			Font.ITALIC, 14);

	protected static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();
	protected static byte SCROLL_SPEED = 20;

	protected static DataManager dataManager;
	private PanelManager manager;

	public GUI() {
		super("USDA Food Database");
		CONFIG.load();
		this.setSize(480, 640);
		this.setLocation(100, 100);
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		manager = new PanelManager();
		this.add(manager);

		dataManager = DataManager.getInstance();
		dataManager.bindProgressDisplay(this);
		dataManager.initAsync(new File("USDAFiles/FOOD_DES.TXT"), new File(
				"USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File(
						"USDAFiles/WEIGHT.TXT"), new File(
						"USDAFiles/LANGUAL.txt"), new File(
						"USDAFiles/LANGDESC.TXT"), new File(
						"USDAFiles/FOOTNOTE.TXT"));

		manager.switchToHome();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public PanelManager getPanelManager() {
		return manager;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		new GUI();
	}
}
