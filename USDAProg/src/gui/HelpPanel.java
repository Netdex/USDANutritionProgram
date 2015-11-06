package gui;

import javax.swing.JPanel;

public class HelpPanel extends JPanel {

	private PanelManager manager;

	public HelpPanel(PanelManager manager) {
		super();
		this.manager = manager;
		this.add(new HomeButton(manager));
		// TODO intended for a flowchart of screens to see, as well as general
		// notes on program operation
	}
}
