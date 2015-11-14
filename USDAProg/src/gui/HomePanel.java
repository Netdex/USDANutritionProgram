package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePanel extends JPanel {

	PanelManager manager;

	public HomePanel(PanelManager manager) {
		this.manager = manager;
		// Sets it up to boxLayout vertical
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(GUI.BACKGROUND_COLOUR);

		// create banner header and buttons on layout
		JLabel bannerLabel = new JLabel();
		try {
			bannerLabel.setIcon(new ImageIcon(ImageIO.read(
					new File("images/homeBanner.png")).getScaledInstance(480,
					128, Image.SCALE_SMOOTH)));
		} catch (IOException e1) {
		}
		JPanel buttonQuad = new JPanel(new GridLayout(2, 2, 8, 8));
		buttonQuad.setOpaque(false);

		// creates six buttons
		HomePanelNavButton searchButton = new HomePanelNavButton("searchButton");
		HomePanelNavButton groupButton = new HomePanelNavButton("groupButton");
		HomePanelNavButton addFoodButton = new HomePanelNavButton(
				"addFoodButton");
//		HomePanelNavButton helpButton = new HomePanelNavButton("helpButton");
//		HomePanelNavButton quitButton = new HomePanelNavButton("quitButton");
		HomePanelNavButton aboutButton = new HomePanelNavButton(
				"aboutButton");

		buttonQuad.add(searchButton);
		buttonQuad.add(groupButton);
		buttonQuad.add(addFoodButton);
//		buttonQuad.add(helpButton);
		buttonQuad.add(aboutButton);
//		buttonQuad.add(quitButton);

		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
//		helpButton.addActionListener(new HelpButtonListener());
		addFoodButton.addActionListener(new AddFoodButtonListener());
//		quitButton.addActionListener(new QuitButtonListener());
		aboutButton.addActionListener(new AboutButtonListener());

		this.add(bannerLabel);
		bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonQuad);
		aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	class HomePanelNavButton extends JButton {
		private HomePanelNavButton(String buttonName) {
			super();
			try {
				this.setIcon(new ImageIcon(ImageIO.read(
						new File("images/" + buttonName + ".png"))
						.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setFocusable(false);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setBorder(GUI.EMPTY_BORDER);
		}
	}

	class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToSearchPanel(true);
		}
	}

	class GroupButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToGroup();
		}
	}

//	class HelpButtonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			manager.switchToHelp();
//		}
//	}

	class AboutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToAbout();
		}
	}

	class AddFoodButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToAddFood();
		}
	}

//	class QuitButtonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if (manager.LOADING_PERCENTAGE == -1)
//				System.exit(0);
//		}
//	}
}
