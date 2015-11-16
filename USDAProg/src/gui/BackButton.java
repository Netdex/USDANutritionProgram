package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Universal back button for any screen to add to return their previous screen
 * 
 * @author Vince Ou
 *
 */
public class BackButton extends JButton {

	/**
	 * The JPanel to return to
	 */
	private JPanel target;
	/**
	 * The manager of all panels
	 */
	private final PanelManager manager;

	public BackButton(JPanel target, PanelManager manager) {
		// Sets up its information
		super();
		this.target = target;
		this.manager = manager;

		// Creates an icon for the back button
		try {
			this.setIcon(new ImageIcon(ImageIO.read(
					new File("images/backButton.png")).getScaledInstance(48,
					48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Alignment
		this.setSize(new Dimension(48, 48));
		this.setFocusable(false);
		this.setBackground(GUI.HEADER_COLOUR);
		this.setBorder(GUI.EMPTY_BORDER);
		this.addActionListener(new BackButtonActionListener());
	}

	/**
	 * Changes where the back button should point
	 * 
	 * @param panel
	 *            the target
	 */
	protected void setTarget(JPanel panel) {
		target = panel;
	}

	/**
	 * What happens when the button is clicked
	 * 
	 * @author Vince Ou
	 *
	 */
	class BackButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// There is no better way to do this.
			// Switches to the appropriate panel as desired.
			// These are the only panels that need to be using the back button.
			if (target instanceof GroupPanel)
				manager.switchToGroup();
			else if (target instanceof InfoPanel)
				manager.switchToInfoPanel();
			else if (target instanceof SearchPanel)
				manager.switchToSearchPanel(false);
			else if (target instanceof FoodListPanel)
				manager.switchToFoodListPanel();
		}
	}
}
