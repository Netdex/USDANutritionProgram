package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * "About creators" information panel for the users
 * @author Vince Ou
 *
 */
class AboutPanel extends JPanel {

	/**
	 * The manager for all the panels that are switched around to display different views.
	 */
	private final PanelManager manager;

	public AboutPanel(PanelManager man) {
		super();
		// Sets up the layout
		this.manager = man;
		this.setLayout(new BorderLayout());
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// Creates a header
		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		// Adds a button that redirects to the home screen
		header.add(new HomeButton(manager));

		// Title text
		JLabel title = new JLabel("About");
		title.setForeground(GUI.TITLE_COLOUR);
		title.setFont(GUI.TITLE_FONT);
		title.setBackground(GUI.HEADER_COLOUR);
		header.add(title);

		// Adds the header panel to the 'about' panel
		this.add(header, BorderLayout.NORTH);

		// Creates an image with "logos" on it and adds it to the 'about' panel
		JLabel icon = new JLabel();
		try {
			icon.setIcon(new ImageIcon(ImageIO.read(
					new File("images/aboutIcon.png")).getScaledInstance(460,
					230, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(icon);

		// Adds text to the panel
		JLabel info = new JLabel(
				"<html>&copy; 2015 Gordon Guan and Vince Ou.<br>Created for ICS4UE Unit 3 Final Project.<br>Did you enjoy reading in this hideous font?</html>");
		info.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		info.setForeground(GUI.CONTENT_COLOUR);
		info.setHorizontalAlignment(SwingConstants.RIGHT);
		info.setBackground(GUI.BACKGROUND_COLOUR);
		this.add(info, BorderLayout.SOUTH);
	}
}
