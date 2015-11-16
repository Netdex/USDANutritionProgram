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

/**
 * The JFrame of the entire program
 * 
 * @author Vince Ou
 *
 */
public class GUI extends JFrame {

	/**
	 * Background colour
	 */
	public final static Color BACKGROUND_COLOUR = Color.WHITE;
	/**
	 * Colour of headers
	 */
	public final static Color HEADER_COLOUR = new Color(7517933);
	/**
	 * Colour of accents
	 */
	public final static Color ACCENT_COLOUR = new Color(15594071);

	/**
	 * Colour of title text
	 */
	public final static Color TITLE_COLOUR = Color.WHITE;
	/**
	 * Colour of content (non-title)
	 */
	public final static Color CONTENT_COLOUR = Color.BLACK;
	/**
	 * Colour of inactive search boxes
	 */
	public final static Color SEARCH_BOX_GRAY = new Color(2, 2, 2);

	/**
	 * Title font
	 */
	protected static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	/**
	 * Subtitle font
	 */
	protected static final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD,
			20);
	/**
	 * Font of content
	 */
	protected static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN,
			16);
	/**
	 * Font for scientific text
	 */
	protected static final Font SCIENTIFIC_FONT = new Font("Calibri",
			Font.ITALIC, 14);

	/**
	 * No border
	 */
	protected static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();
	/**
	 * Border for buttons
	 */
	protected static final javax.swing.border.Border BUTTON_BORDER = BorderFactory
			.createLineBorder(BACKGROUND_COLOUR, 1, true);

	/**
	 * The interface between front and back end
	 */
	protected static DataManager dataManager;
	/**
	 * The manager switcher thing
	 */
	private PanelManager manager;

	public GUI() {
		// loads things
		super("USDA Food Database");

		// Sets size and location
		this.setSize(480, 640);
		this.setLocation(300, 100);

		// sets the icon in the task bark
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Creates a manager for the different views
		manager = new PanelManager();
		this.add(manager);

		// Loads the files from the database
		dataManager = DataManager.getInstance();
		dataManager.bindProgressDisplay(this);
		dataManager.init(new File("USDAFiles/FOOD_DES.TXT"), new File(
				"USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File(
						"USDAFiles/WEIGHT.TXT"), new File(
						"USDAFiles/LANGUAL.txt"), new File(
						"USDAFiles/LANGDESC.TXT"), new File(
						"USDAFiles/FOOTNOTE.TXT"));

		// Starts actual operation
		manager.switchToHome();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Gets the panel manager
	 * 
	 * @return the panel manager
	 */
	public PanelManager getPanelManager() {
		return manager;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		new GUI();
	}
}
