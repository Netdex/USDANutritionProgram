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

public class HelpPanel extends JPanel {

	private PanelManager manager;

	public HelpPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(GUI.BACKGROUND_COLOR);

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(GUI.HEADER_GREY);
		header.add(new HomeButton(this.manager));

		JLabel title = new JLabel("HELP");
		title.setFont(GUI.TITLE_FONT);
		title.setOpaque(false);
		header.add(title);
		this.add(header);

		JLabel helpPicture = new JLabel();
		//TODO make a proper help image
		try {
			helpPicture.setIcon(new ImageIcon(ImageIO.read(
					new File("images/helpPicture.png")).getScaledInstance(400,
					400, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(helpPicture);
	}
}
