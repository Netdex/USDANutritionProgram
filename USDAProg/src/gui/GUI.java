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

public class GUI extends JFrame {

	public static ConfigurationManager CONFIG = new ConfigurationManager(
			new File("config.prop"));

	protected final static Color BACKGROUND_COLOR = new Color(16448250);
	protected final static Color HEADER_GREY = new Color(14606046);

	protected static final Font TITLE_FONT = new Font("Futura", Font.BOLD, 32);
	protected static final Font SUBTITLE_FONT = new Font("Calibri", Font.PLAIN,
			18);
	protected static final Font CONTENT_FONT = new Font("Calibri", Font.PLAIN,
			16);

	protected static final javax.swing.border.Border EMPTY_BORDER = BorderFactory
			.createEmptyBorder();

	public GUI() {
		super("USDA Food Database");
		CONFIG.load();
		this.setSize(480, 640);
		try {
			this.setIconImage(ImageIO.read(new File("images/windowIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
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
