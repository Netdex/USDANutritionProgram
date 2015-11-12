package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parser.ImageExtract;
import parser.parsables.FoodItem;

public class ExtraInfoPanel extends JPanel {

	private PanelManager manager;
	private FoodItem food;
	
	private JPanel contentPane;
	
	protected ExtraInfoPanel(PanelManager panelManager) {
		super();
		this.manager = panelManager;
		this.setLayout(new BorderLayout());
		JPanel header = new JPanel();
		header.setBackground(GUI.HEADER_COLOUR);
		header.add(new BackButton(manager.getInfoPanel(), this.manager));
		
		JLabel titleNameLabel = new JLabel("BONJOUR!");
		titleNameLabel.setFont(GUI.TITLE_FONT);
		header.add(titleNameLabel);
		this.add(header, BorderLayout.NORTH);
		contentPane = new JPanel();
		JScrollPane scrollPane = new JScrollPane(contentPane);
		scrollPane.createVerticalScrollBar();
		scrollPane.getViewport().setBackground(GUI.BACKGROUND_COLOUR);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(
				GUI.SCROLL_SPEED);
		scrollPane.getVerticalScrollBar().setBackground(
				GUI.BACKGROUND_COLOUR);
		scrollPane.setWheelScrollingEnabled(true);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	protected void setFood(FoodItem item) {
		contentPane.removeAll();
		this.food = item;
		
		String longDesc = food.getLongDescription();
		int firstSeparatorIndex = longDesc.indexOf(',');
		int alternateFirstSeparator = longDesc.indexOf('(');
		if ((alternateFirstSeparator > 0 && alternateFirstSeparator < firstSeparatorIndex)
				|| firstSeparatorIndex == -1)
			firstSeparatorIndex = alternateFirstSeparator;

		String titleName;
		// ridiculously long string before comma/bracket or none
		if (firstSeparatorIndex > 17 || firstSeparatorIndex == -1) {
			int firstSpaceIndex = longDesc.indexOf(' ');
			if (longDesc.length() <= 17)
				titleName = longDesc;
			else {
				if (firstSpaceIndex > 17)
					// if the first space is still too long, force cut
					titleName = longDesc.substring(0, 17);
				else
					// use space instead of the other separators then...
					titleName = longDesc.substring(0, firstSpaceIndex);
			}
		} else {
			// normal case
			titleName = longDesc.substring(0, firstSeparatorIndex);
		}

		// adds an image
		JLabel image = new JLabel();
		ImageExtract.injectImage(image, titleName);
		contentPane.add(image);
		// adds LanguaLs
		if (food.getLangualGroup() != null) {
			JLabel langualsList = new JLabel("<html>"
					+ food.getLangualGroup().getLanguaLs().toString()
					+ "</html>");
			langualsList.setFont(GUI.CONTENT_FONT);
			contentPane.add(langualsList);
			
		}
		
	}
}
