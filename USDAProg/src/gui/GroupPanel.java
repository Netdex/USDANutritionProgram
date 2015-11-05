package gui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GroupPanel extends JPanel {

	PanelManager manager;

	public GroupPanel(PanelManager manager) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.manager = manager;

		JPanel header = new JPanel();
		header.add(new HomeButton(manager));
		JLabel title = new JLabel("FOOD GROUPS");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		header.add(title);
		this.add(header);
	}
}
