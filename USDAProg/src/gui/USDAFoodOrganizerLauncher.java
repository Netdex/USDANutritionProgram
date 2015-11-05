package gui;

import javax.swing.UIManager;

public class USDAFoodOrganizerLauncher {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new GUI();
	}

}
