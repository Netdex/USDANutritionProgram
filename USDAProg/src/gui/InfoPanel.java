package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
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
		BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.setBackground(GUI.BACKGROUND_COLOUR);
		contentPanel.setOpaque(false);

		this.add(contentPanel, BorderLayout.CENTER);
	}

	protected void setFoodItem(FoodItem item) {
		contentPanel.removeAll();
		this.food = item;

		// TODO make alignments work

		// Changes title in header
		String longDesc = food.getLongDescription();
		if (longDesc.indexOf(',') != -1)
			titleName.setText(longDesc.substring(0, longDesc.indexOf(',')));
		else
			titleName.setToolTipText(longDesc);

		// adds long name in actual page
		JLabel longName = new JLabel("<html>" + longDesc + "</html>");
		longName.setFont(GUI.SUBTITLE_FONT);
		longName.setForeground(GUI.ACCENT_COLOUR);
		longName.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(longName);

		// adds common name info
		if (!food.getCommonName().equals("")) {
			JLabel commonName = new JLabel("<html> Other name(s) include: "
					+ food.getCommonName().toString() + "</html>");
			commonName.setFont(GUI.CONTENT_FONT);
			commonName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(commonName);
		}

		// adds food group info
		JLabel foodGroup = new JLabel("Food Group: "
				+ food.getFoodGroup().toString());
		foodGroup.setFont(GUI.CONTENT_FONT);
		foodGroup.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(foodGroup);

		// add scientific name
		if (!food.getScientificName().equals("")) {
			JLabel scientificName = new JLabel("<html> Scientific name: "
					+ food.getScientificName().toString() + "</html>");
			scientificName.setFont(GUI.SCIENTIFIC_FONT);
			scientificName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(scientificName);
		}

		// add manufacturer name
		if (!food.getManufacturerName().equals("")) {
			JLabel manufacName = new JLabel("<html> Manufactured by: "
					+ food.getManufacturerName().toString() + "</html>");
			manufacName.setFont(GUI.CONTENT_FONT);
			manufacName.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(manufacName);
		}

		JPanel amountEntryLine = new JPanel();
		amountEntryLine.setOpaque(false);
		FlowLayout amountEntryLayout = new FlowLayout(FlowLayout.LEFT);
		amountEntryLine.setLayout(amountEntryLayout);

		JLabel amountEntryPrompt = new JLabel(
				"<html> The unit used to measure this item is "
						// + food.getWeightInfo().getDesc() TODO fix this
						+ "hi"
						+ ".<br>Please enter the amount of this food<br>you are intending to consume<html>");
		amountEntryPrompt.setFont(GUI.CONTENT_FONT);
		amountEntryPrompt.setAlignmentX(LEFT_ALIGNMENT);
		amountEntryLine.add(amountEntryPrompt);

		SpinnerNumberModel amountEntryModel = new SpinnerNumberModel(0, 0, 999,
				1);
		JSpinner amountEntry = new JSpinner(amountEntryModel);
		amountEntry.setBackground(GUI.BACKGROUND_COLOUR);
		amountEntry.setFont(GUI.CONTENT_FONT);
		amountEntry.setAlignmentX(LEFT_ALIGNMENT);
		amountEntryLine.add(amountEntry);

		amountEntryLine.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(amountEntryLine);

		contentPanel.revalidate();
		contentPanel.repaint();
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
// symbolab