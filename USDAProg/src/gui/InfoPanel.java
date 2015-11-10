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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import parser.parsables.FoodItem;

public class InfoPanel extends JPanel {

	private FoodItem food;
	private double amountOfFood;
	private double nutritionMultiplier;

	private SearchPanel searchPanel;
	private PanelManager manager;

	private JPanel header;
	private JPanel contentPanel;
	private JLabel titleName;
	private JSpinner amountEntry;

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

		JScrollPane scrollPanel = new JScrollPane();
		scrollPanel.createVerticalScrollBar();
		scrollPanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(GUI.SCROLL_SPEED);
		scrollPanel.setWheelScrollingEnabled(true);
		scrollPanel.setHorizontalScrollBar(null);
		this.add(contentPanel);

		nutritionMultiplier = GUI.CONFIG
				.getDouble("userNutritionMultiplier");
	}

	protected void setFoodItem(FoodItem food) {
		contentPanel.removeAll();
		this.food = food;
		String name = this.food.getLongDescription();
		titleName.setText(name.substring(0, name.indexOf(',')));

		JLabel longDescLabel = new JLabel("<html>" + name + "</html>");
		longDescLabel.setFont(GUI.SUBTITLE_FONT);
		contentPanel.add(longDescLabel);

		JLabel foodGroupLabel = new JLabel(this.food.getFoodGroup().toString());
		foodGroupLabel.setFont(GUI.CONTENT_FONT);
		contentPanel.add(foodGroupLabel);

		JLabel commonNameLabel = new JLabel(this.food.getCommonName());
		commonNameLabel.setFont(GUI.CONTENT_FONT);
		contentPanel.add(commonNameLabel);

		JLabel unitsEntryPrompt = new JLabel(
				"<html>Units: which unit of measure are you using?.</html>");
		unitsEntryPrompt.setFont(GUI.CONTENT_FONT);
		contentPanel.add(unitsEntryPrompt);

		// TODO get the units used to measure this type of food
		// use a JComboBox

		JLabel amountEntryPrompt = new JLabel(
				"<html>How much (in the units above) are you intending to consume?</html>");
		amountEntryPrompt.setFont(GUI.CONTENT_FONT);
		contentPanel.add(amountEntryPrompt);

		SpinnerNumberModel amountModel = new SpinnerNumberModel(0.0, 0.0,
				Integer.MAX_VALUE, 1.0);
		amountEntry = new JSpinner(amountModel);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.addChangeListener(new AmountEntryListener());
		contentPanel.add(amountEntry);

		/*
		 * unbreak this
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

	private void updateFields(double newAmount) {
		amountOfFood = newAmount;

	}

	protected void setNutritionMultiplier(double personalizedMultiplier) {
		this.nutritionMultiplier = personalizedMultiplier;
	}

	class AmountEntryListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			updateFields((double) amountEntry.getValue());
		}

	}

	class MoreInfoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// draw the more info dialog over this
		}

	}

}
