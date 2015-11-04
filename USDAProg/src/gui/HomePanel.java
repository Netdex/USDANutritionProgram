package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePanel extends JPanel {

	GUIFrame frame;

	public HomePanel(GUIFrame frame) {
		this.frame = frame;
		run();
	}

	private void run() {
		// Sets it up to boxLayout vertical
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create banner header and buttons on layout
		JLabel bannerLabel;
		try {
			bannerLabel = new JLabel(new ImageIcon(ImageIO.read(new File(
					"homebanner.jpg"))));
		} catch (IOException e) {
			bannerLabel = null;
		}
		JPanel buttons = new JPanel(new GridLayout(2, 2, 16, 16));

		// create five buttons
		// TODO Images need to be created
		JButton searchButton = new JButton(new ImageIcon("searchButton.png"));
		JButton groupButton = new JButton(new ImageIcon("groupButton.png"));
		JButton bookmarksButton = new JButton(new ImageIcon(
				"bookmarksButton.png"));
		JButton settingsButton = new JButton(
				new ImageIcon("settingsButton.png"));

		buttons.add(searchButton);
		buttons.add(groupButton);
		buttons.add(bookmarksButton);
		buttons.add(settingsButton);

		JButton aboutButton = new JButton(new ImageIcon("aboutButton.png"));

		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
		bookmarksButton.addActionListener(new BookmarksButtonListener());
		settingsButton.addActionListener(new SettingsButtonListener());
		aboutButton.addActionListener(new AboutButtonListener());

		this.add(bannerLabel);
		this.add(buttons);
		this.add(aboutButton);
	}

	class SearchButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.switchToSearch();
		}
	}

	class GroupButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// switch to group panel
		}
	}

	class BookmarksButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// switch to bookmarks panel
		}
	}

	class SettingsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// switch to settings panel
		}
	}

	class AboutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// show about window
		}
	}
}
