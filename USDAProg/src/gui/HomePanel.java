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
		JPanel buttonQuad = new JPanel(new GridLayout(3, 2, 8, 8));
		buttonQuad.setBackground(Color.BLACK);

		// search
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

		// group
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

		// add
		JButton addFoodButton = new JButton();
		try {
			addFoodButton = new JButton(new ImageIcon(ImageIO.read(
					new File("images/addFoodButton.png")).getScaledInstance(
					200, 200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addFoodButton.setBackground(new Color(3793803));
		addFoodButton.setBorder(GUI.EMPTY_BORDER);

		// help
		JButton helpButton = new JButton();
		try {
			helpButton = new JButton(new ImageIcon(ImageIO.read(
					new File("images/helpButton.png")).getScaledInstance(200,
					200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		helpButton.setBackground(new Color(8174056));
		helpButton.setBorder(GUI.EMPTY_BORDER);

		// quit
		JButton quitButton = new JButton();
		try {
			quitButton = new JButton(new ImageIcon(ImageIO.read(
					new File("images/quitButton.png")).getScaledInstance(200,
					200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		quitButton.setBackground(new Color(14046292));
		quitButton.setBorder(GUI.EMPTY_BORDER);

		// about
		JButton aboutButton = new JButton();
		try {
			aboutButton = new JButton(new ImageIcon(ImageIO.read(
					new File("images/moreInfoButton.png")).getScaledInstance(
					200, 200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		aboutButton.setBackground(new Color(14046411));
		aboutButton.setBorder(GUI.EMPTY_BORDER);

		buttonQuad.add(searchButton);
		buttonQuad.add(groupButton);
		buttonQuad.add(addFoodButton);
		buttonQuad.add(helpButton);
		buttonQuad.add(aboutButton);
		buttonQuad.add(quitButton);

		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
		helpButton.addActionListener(new HelpButtonListener());
		addFoodButton.addActionListener(new AddFoodButtonListener());
		quitButton.addActionListener(new QuitButtonListener());
		aboutButton.addActionListener(new AboutButtonListener());

		this.add(bannerLabel);
		bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonQuad);
		aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

	class HelpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToHelp();
		}
	}

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

	class QuitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
