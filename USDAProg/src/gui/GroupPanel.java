package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GroupPanel extends JPanel {

	PanelManager manager;

	public GroupPanel(PanelManager manager) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.manager = manager;

		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		HomeButton homeButton = null;
		try {
			homeButton = new HomeButton(this.manager);
		} catch (IOException e) {
		}
		header.add(homeButton);
		JLabel title = new JLabel("FOOD GROUPS");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		header.add(title);
		this.add(header);
	}
}
