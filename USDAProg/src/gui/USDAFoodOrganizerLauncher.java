package gui;

import javax.swing.UIManager;

public class USDAFoodOrganizerLauncher {

	public static void main(String[] args) throws Exception {
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		new GUI();
	}

}
