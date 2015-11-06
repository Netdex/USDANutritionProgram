package gui;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutPanel extends JPanel {

	PanelManager manager;

	public AboutPanel(PanelManager manager) {
		// TODO make this look less gross
		super();
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(CENTER_ALIGNMENT);

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
		this.add(icon);

		JLabel info = new JLabel(
				"(C) 2015 Gordon Guan and Vince Ou.\nCreated for ICS4UE Unit 3 Final Project.");
		info.setFont(GUI.CONTENT_FONT);
		info.setBackground(GUI.BACKGROUND_COLOR);
		this.add(info);
	}
}
