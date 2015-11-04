package gui;

import java.awt.Component;
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

	JFrame frame;
	GUI gui;

	public HomePanel(JFrame frame, GUI gui) {
		this.frame = frame;
		this.gui = gui;
		run();
	}

	private void run() {
		// Sets it up to boxLayout vertical
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create banner header and buttons on layout
		JLabel bannerLabel = new JLabel("BANNER HERE");
		JPanel buttonQuad = new JPanel(new GridLayout(2, 2, 8, 8));

		// create five buttons
		// TODO Proper images need to be created

		// Why can't I display an icon in a button?
		ImageIcon searchButtonImage = null;
		try {
			searchButtonImage = new ImageIcon(ImageIO.read(new File(
					"images/searchButton.png")));
		} catch (IOException e) {
		}
		JButton searchButton = new JButton(searchButtonImage);

		// JButton searchButton = new JButton("Search");
		JButton groupButton = new JButton("Group");
		JButton bookmarksButton = new JButton("Bookmarks");
		JButton settingsButton = new JButton("Settings");

		JButton aboutButton = new JButton("About");

		buttonQuad.add(searchButton);
		buttonQuad.add(groupButton);
		buttonQuad.add(bookmarksButton);
		buttonQuad.add(settingsButton);

		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
		bookmarksButton.addActionListener(new BookmarksButtonListener());
		settingsButton.addActionListener(new SettingsButtonListener());
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
			gui.switchToSearch();
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