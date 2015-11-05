package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomeButton extends JButton {

	PanelManager manager;

	public HomeButton(PanelManager manager) {
		super(new ImageIcon("images/homeButton.png"));
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
