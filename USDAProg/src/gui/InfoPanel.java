package gui;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;

import parser.parsables.FoodItem;

public class InfoPanel extends JPanel {

	FoodItem food;
	double amountOfFood;

	private SearchPanel searchPanel;
	private PanelManager manager;

	private JPanel header;
	private JPanel contentPanel;
	private JLabel titleName;

	public InfoPanel(SearchPanel searchPanel, PanelManager manager) {
		super();
		this.searchPanel = searchPanel;
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.BACKGROUND_COLOUR);

		header = new JPanel();
		header.add(new BackButton(this.searchPanel, this.manager));
		titleName = new JLabel();
		titleName.setFont(GUI.TITLE_FONT);
		header.add(titleName);
		JButton moreInfo = new JButton();
		try {
			moreInfo.setIcon(new ImageIcon(ImageIO.read(
					new File("images/moreInfoButton.png")).getScaledInstance(
					48, 48, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
		}
		moreInfo.addActionListener(new MoreInfoButtonListener());
		moreInfo.setBackground(GUI.BACKGROUND_COLOUR);
		header.add(moreInfo);
		header.setBackground(GUI.HEADER_COLOUR);

		this.add(header, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setAlignmentY(LEFT_ALIGNMENT);
		this.add(contentPanel);
	}

	protected void setFoodItem(FoodItem food) {
		contentPanel.removeAll();
		this.food = food;
		String name = food.getLongDescription();
		titleName.setText(name.substring(0, name.indexOf(',')));

		JLabel longDescLabel = new JLabel("<html>" + name + "</html>");
		longDescLabel.setFont(GUI.SUBTITLE_FONT);
		contentPanel.add(longDescLabel);

		JLabel foodGroupLabel = new JLabel(food.getFoodGroup().toString());
		foodGroupLabel.setFont(GUI.CONTENT_FONT);
		contentPanel.add(foodGroupLabel);

		JLabel commonNameLabel = new JLabel(food.getCommonName());
		commonNameLabel.setFont(GUI.CONTENT_FONT);
		contentPanel.add(commonNameLabel);

		JLabel unitsEntryPrompt = new JLabel(
				"<html>Units: enter how much of the food product you are intending to consume.</html>");
		unitsEntryPrompt.setFont(GUI.CONTENT_FONT);
		contentPanel.add(unitsEntryPrompt);

		/*
		 * TODO unbreak this
		 * 
		 * Get the number of weight measurement units for each food.
		 * 
		 * Use a for loop and generate that many text entry boxes, one for each
		 * unit of measure
		 * 
		 * Convert the most recently entered value to the universal unit
		 * 
		 * Do math with all the nutrients and the universal unit to figure out
		 * the displayed amount of nutrients
		 */

	}

	class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// draw the more info dialog over this
		}

	}

}
