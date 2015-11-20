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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Home screen
 * 
 * @author Vince Ou
 *
 */
public class HomePanel extends JPanel {

	/**
	 * Manager for the panels/views/different functions
	 */
	private final PanelManager manager;

	public HomePanel(PanelManager manager) {
		// Sets up things
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
			JOptionPane.showConfirmDialog(null, "Banner image not found",
					"Image Not Found", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}

		// something to hold the four major nav buttons
		JPanel buttonQuad = new JPanel(new GridLayout(2, 2, 8, 8));
		buttonQuad.setOpaque(false);

		// adds the four main buttons
		HomePanelNavButton searchButton = new HomePanelNavButton("searchButton");
		HomePanelNavButton groupButton = new HomePanelNavButton("groupButton");
		HomePanelNavButton addFoodButton = new HomePanelNavButton(
				"addFoodButton");
		HomePanelNavButton aboutButton = new HomePanelNavButton("aboutButton");

		buttonQuad.add(searchButton);
		buttonQuad.add(groupButton);
		buttonQuad.add(addFoodButton);
		buttonQuad.add(aboutButton);

		// Makes the buttons work
		searchButton.addActionListener(new SearchButtonListener());
		groupButton.addActionListener(new GroupButtonListener());
		addFoodButton.addActionListener(new AddFoodButtonListener());
		aboutButton.addActionListener(new AboutButtonListener());

		// Adds things into the panel
		this.add(bannerLabel);
		bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonQuad);
		aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	/**
	 * Creates one of the four buttons
	 * 
	 * @author Vince Ou
	 *
	 */
	class HomePanelNavButton extends JButton {

		/**
		 * Constructor
		 * 
		 * @param buttonName
		 *            the name of the button (ALSO THE FILE NAME)
		 */
		private HomePanelNavButton(String buttonName) {
			// does things.
			super();

			// Gives the button an image
			try {
				this.setIcon(new ImageIcon(ImageIO.read(
						new File("images/" + buttonName + ".png"))
						.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(this, "Button icon not found",
						"Missing Image", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}

			// Does more things layout-wise.
			this.setFocusable(false);
			this.setBackground(GUI.BACKGROUND_COLOUR);
			this.setBorder(GUI.EMPTY_BORDER);
		}
	}

	/**
	 * What happens when you press the group button (switches to group panel)
	 * 
	 * @author Vince Ou
	 *
	 */
	private class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToSearchPanel(true);
		}
	}

	/**
	 * What happens when you press the group button (switches to group panel)
	 * 
	 * @author Vince Ou
	 *
	 */
	private class GroupButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToGroup();
		}
	}

	/**
	 * What happens when you press the about button (switches to about panel)
	 * 
	 * @author Vince Ou
	 *
	 */
	private class AboutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToAbout();
		}
	}

	/**
	 * What happens when you press the add food button (switches to add food
	 * panel)
	 * 
	 * @author Vince Ou
	 *
	 */
	private class AddFoodButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchToAddFood();
		}
	}
}
