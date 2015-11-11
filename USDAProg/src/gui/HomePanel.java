package gui;

import java.awt.Color;
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
		this.setBackground(Color.BLACK);

		// create banner header and buttons on layout
		JLabel bannerLabel = new JLabel();
		try {
			bannerLabel.setIcon(new ImageIcon(ImageIO.read(
					new File("images/homeBanner.png")).getScaledInstance(480,
					128, Image.SCALE_SMOOTH)));
		} catch (IOException e1) {
		}
		JPanel buttonQuad = new JPanel(new GridLayout(2, 2, 8, 8));
		buttonQuad.setBackground(Color.BLACK);

		// create five buttons
		JButton searchButton = new JButton();
		try {
			searchButton.setIcon(new ImageIcon(ImageIO.read(
					new File("images/searchButton.png")).getScaledInstance(200,
					200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchButton.setBackground(Color.GREEN);
		searchButton.setBorder(GUI.EMPTY_BORDER);

		JButton groupButton = new JButton();
		try {
			groupButton.setIcon(new ImageIcon(ImageIO.read(
					new File("images/groupButton.png")).getScaledInstance(200,
					200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		groupButton.setBackground(Color.ORANGE);
		groupButton.setBorder(GUI.EMPTY_BORDER);

//		JButton helpButton = new JButton();
//		try {
//			helpButton = new JButton(new ImageIcon(ImageIO.read(
//					new File("images/helpButton.png")).getScaledInstance(200,
//					200, Image.SCALE_SMOOTH)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		helpButton.setBackground(new Color(8174056));
//		helpButton.setBorder(GUI.EMPTY_BORDER);

		// JButton settingsButton = new JButton();
		// try {
		// settingsButton.setIcon(new ImageIcon(ImageIO.read(
		// new File("images/settingsButton.png")).getScaledInstance(
		// 200, 200, Image.SCALE_SMOOTH)));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// settingsButton.setBackground(Color.DARK_GRAY);
		// settingsButton.setBorder(GUI.EMPTY_BORDER);

		JButton aboutButton = new JButton("About");
		aboutButton.setBackground(Color.BLACK);
		aboutButton.setForeground(Color.WHITE);
		aboutButton.setBorder(GUI.EMPTY_BORDER);

		buttonQuad.add(searchButton);
		buttonQuad.add(groupButton);
//		buttonQuad.add(helpButton);
//		buttonQuad.add(settingsButton);

		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
//		helpButton.addActionListener(new HelpButtonListener());
		// settingsButton.addActionListener(new SettingsButtonListener());
		aboutButton.addActionListener(new AboutButtonListener());

		this.add(bannerLabel);
		bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonQuad);
		this.add(aboutButton);
		aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToSearchPanel();
		}
	}

	class GroupButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToGroup();
		}
	}

	// class HelpButtonListener implements ActionListener {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// manager.switchToHelp();
	// }
	// }

	// class SettingsButtonListener implements ActionListener {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// manager.switchToSettings();
	// }
	// }

	class AboutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToAbout();
		}
	}
}
