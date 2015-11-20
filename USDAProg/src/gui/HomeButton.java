package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Universal home button that switches whatever the user is looking at to the
 * home screen
 * 
 * @author Vince Ou
 *
 */
class HomeButton extends JButton {

	/**
	 * The manager for the different panels/views
	 */
	private final PanelManager manager;

	public HomeButton(PanelManager manager) {
		// sets up things
		super();

		// Sets an image for the home button
		try {
			this.setIcon(new ImageIcon(ImageIO.read(
					new File("images/homeButton.png")).getScaledInstance(48,
					48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Sets up more things
		this.manager = manager;
		this.setBackground(GUI.HEADER_COLOUR);
		this.setFocusable(false);
		this.setBorder(GUI.EMPTY_BORDER);
		addActionListener(new HomeButtonActionListener());
	}

	/**
	 * What happens when you click the home button
	 * 
	 * @author Vince Ou
	 *
	 */
	private class HomeButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Switches the view to the home view.
			manager.switchToHome();
		}

	}
}
