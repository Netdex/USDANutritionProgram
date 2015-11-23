package gui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import parser.DataManager;
import parser.ImageExtract;

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
	public final static Color ACCENT_COLOUR = new Color(5242800);

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
	public final static Color SEARCH_BOX_GREY_GRAY = new Color(128, 128, 128);

	/**
	 * Title font
	 */
	static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	/**
	 * Subtitle font
	 */
	static final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD, 20);
	/**
	 * Font of content
	 */
	static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN, 16);
	/**
	 * Font for scientific text
	 */
	static final Font SCIENTIFIC_FONT = new Font("Calibri", Font.ITALIC, 14);

	/**
	 * Maximum value for JSpinners
	 */
	static final double SPINNER_MAX = (double) Integer.MAX_VALUE;

	/**
	 * No border
	 */
	static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();
	/**
	 * Border for buttons
	 */
	static final javax.swing.border.Border BUTTON_BORDER = BorderFactory
			.createLineBorder(BACKGROUND_COLOUR, 1, true);

	/**
	 * The interface between front and back end
	 */
	static DataManager dataManager;
	/**
	 * The manager switcher thing
	 */
	private final PanelManager manager;

	private GUI() {
		// loads things
		super("USDA Food Database");

		// Sets size and location
		this.setSize(480, 640);
		this.setLocation(300, 100);

		// sets the icon in the task bark
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, "Window icon not found",
					"Missing Image", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}

		// Creates a manager for the different views
		manager = new PanelManager();
		this.add(manager);

		ImageExtract.initPreload();
		// Loads the files from the database
		dataManager = DataManager.getInstance();
		dataManager.bindProgressDisplay(this);
		dataManager.init(new File("FOOD_DES.TXT"), new File("NUT_DATA.TXT"),
				new File("NUTR_DEF.TXT"), new File("FD_GROUP.TXT"), new File(
						"WEIGHT.TXT"), new File("LANGUAL.txt"), new File(
						"LANGDESC.TXT"), new File("FOOTNOTE.TXT"));

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
