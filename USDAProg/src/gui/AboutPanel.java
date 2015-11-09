package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutPanel extends JPanel {

	PanelManager manager;

	public AboutPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setBackground(GUI.BACKGROUND_BLUE);

		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_ORANGE);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		JLabel title = new JLabel("ABOUT");
		title.setFont(GUI.TITLE_FONT);
		title.setBackground(GUI.HEADER_ORANGE);
		header.add(title);

		this.add(header, BorderLayout.NORTH);

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

		JLabel info = new JLabel(
				"<html>&copy; 2015 Gordon Guan and Vince Ou.<br>Created for ICS4UE Unit 3 Final Project.<br>Did you enjoy reading in this hideous font?</html>");
		info.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		info.setHorizontalAlignment(SwingConstants.RIGHT);
		info.setBackground(GUI.BACKGROUND_BLUE);
		this.add(info, BorderLayout.SOUTH);
	}
}
