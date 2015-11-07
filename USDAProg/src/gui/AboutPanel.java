package gui;

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
		// TODO make this look less gross
		super();
		this.manager = manager;
		this.setLayout(new GridLayout(3, 0));
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setBackground(GUI.BACKGROUND_COLOR);

		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_GREY);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new HomeButton(manager));

		JLabel title = new JLabel("ABOUT");
		title.setFont(GUI.TITLE_FONT);
		title.setBackground(GUI.HEADER_GREY);
		header.add(title);

		this.add(header);

		JLabel icon = new JLabel();
		try {
			icon.setIcon(new ImageIcon(ImageIO.read(
					new File("images/aboutIcon.png")).getScaledInstance(400,
					200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(icon);

		JLabel info = new JLabel(
				"<html>&copy; 2015 Gordon Guan and Vince Ou.<br>Created for ICS4UE Unit 3 Final Project.<br>Did you enjoy reading in this hideous font?</html>");
		info.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		info.setHorizontalAlignment(SwingConstants.RIGHT);
		info.setBackground(GUI.BACKGROUND_COLOR);
		this.add(info);
	}
}
