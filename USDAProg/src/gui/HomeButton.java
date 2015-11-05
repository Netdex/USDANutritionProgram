package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomeButton extends JButton {

	PanelManager manager;

	public HomeButton(PanelManager manager) throws IOException {
		// TODO make better icon
		super(new ImageIcon(ImageIO.read(new File("images/homeButton.png"))
				.getScaledInstance(48, 48, Image.SCALE_DEFAULT)));
		this.manager = manager;
		addActionListener(new HomeButtonActionListener());
	}

	class HomeButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			manager.switchToHome();
		}

	}
}
