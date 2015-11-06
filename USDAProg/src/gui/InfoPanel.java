package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import parser.parsables.FoodItem;

public class InfoPanel extends JPanel {

	SearchPanel searchPanel;
	FoodItem food;
	double amountOfFood;

	JList<String> nutritionDataList;

	JLabel commonDescLabel = null;
	JLabel foodGroupLabel = null;
	JLabel commonNameLabel = null;

	public InfoPanel(SearchPanel searchPanel) {
		super();
		this.searchPanel = searchPanel;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel header = new JPanel();

		JButton backButton = new JButton();
		try {
			backButton.setIcon(new ImageIcon(ImageIO.read(new File(
					"images/backButton.png"))));
		} catch (IOException e) {
		}

		JButton moreInfo = new JButton();
		try {
			moreInfo.setIcon(new ImageIcon(ImageIO.read(new File(
					"images/moreInfoButton.png"))));
		} catch (IOException e) {
		}

		backButton.addActionListener(new BackButtonListener());
		moreInfo.addActionListener(new MoreInfoButtonListener());

		header.add(backButton);
		header.add(moreInfo);

		this.add(header);
	}

	protected void setFoodItem(FoodItem food) {
		this.food = food;

		nutritionDataList = new JList<String>();

		commonDescLabel = new JLabel(food.getLongDescription());
		commonDescLabel.setFont(GUI.TITLE_FONT);
		nutritionDataList.add(commonDescLabel);

		foodGroupLabel = new JLabel(food.getFoodGroup().toString());
		foodGroupLabel.setFont(GUI.CONTENT_FONT);
		nutritionDataList.add(foodGroupLabel);

		commonNameLabel = new JLabel(food.getCommmonName());
		commonNameLabel.setFont(GUI.CONTENT_FONT);
		nutritionDataList.add(commonNameLabel);

		JLabel unitsEntryPrompt = new JLabel(
				"Units: enter how much of the food product you are intending to consume.");
		unitsEntryPrompt.setFont(GUI.CONTENT_FONT);
		nutritionDataList.add(unitsEntryPrompt);

		/*
		 * TODO unbreak this Get the number of weight measurement units for each
		 * food.
		 * 
		 * Use a for loop and generate that many text entry boxes, one for each
		 * unit of measure
		 * 
		 * Convert the most recently entered value to the universal unit
		 * 
		 * Do math with all the nutrients and the universal unit to figure out
		 * the displayed amount of nutrients
		 */

		this.add(nutritionDataList);
	}

	class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// "delete" this panel and show the searchPanel results again
		}

	}

	class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// draw the more info dialog over this
		}

	}

}
