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

public class BackButton extends JButton {

	private JPanel target;
	private final PanelManager manager;

	public BackButton(JPanel target, PanelManager manager) {
		super();
		this.target = target;
		this.manager = manager;
		try {
			this.setIcon(new ImageIcon(ImageIO.read(
					new File("images/backButton.png")).getScaledInstance(48,
					48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(new Dimension(48, 48));
		this.setFocusable(false);
		this.setBackground(GUI.HEADER_COLOUR);
		this.setBorder(GUI.EMPTY_BORDER);
		this.addActionListener(new BackButtonActionListener());
	}

	protected void setTarget(JPanel panel) {
		target = panel;
	}

	class BackButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
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
