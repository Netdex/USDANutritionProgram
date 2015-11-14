package gui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import parser.DataManager;
import config.ConfigurationManager;

public class GUI extends JFrame {

	public static final ConfigurationManager CONFIG = new ConfigurationManager(
			new File("config.prop"));

	public final static Color BACKGROUND_COLOUR = Color.WHITE;
	public final static Color HEADER_COLOUR = new Color(7517933);	
	public final static Color ACCENT_COLOUR = new Color(16120427);
	public final static Color TITLE_COLOUR = Color.WHITE;
	public final static Color CONTENT_COLOUR = Color.BLACK;

	protected static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	protected static final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD,
			20);
	protected static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN,
			16);
	protected static final Font SCIENTIFIC_FONT = new Font("Calibri",
			Font.ITALIC, 14);

	protected static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();
	protected static final javax.swing.border.Border BUTTON_BORDER = BorderFactory
			.createLineBorder(BACKGROUND_COLOUR, 1, true);

	protected static final byte SCROLL_SPEED = 20;

	protected static DataManager dataManager;
	private PanelManager manager;

	public GUI() {
		super("USDA Food Database");
		CONFIG.load();
		// this doesn't actually set the correct height, but whatever.
		this.setSize(480, 640);
		this.setLocation(300, 100);
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		manager = new PanelManager();
		this.add(manager);

		dataManager = DataManager.getInstance();
		dataManager.bindProgressDisplay(this);
		dataManager.init(new File("USDAFiles/FOOD_DES.TXT"), new File(
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
		// UIManager
		// .setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		new GUI();
	}
}
